using System.Text;
using System.Text.Json;
using EmployeeGateway.Common.DTO;
using EmployeeGateway.Common.DTO.Transaction;
using EmployeeGateway.Common.ServicesInterface;
using EmployeeGateway.Common.System;
using FirebaseAdmin.Messaging;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Hosting;
using Microsoft.Extensions.Logging;
using RabbitMQ.Client;
using RabbitMQ.Client.Events;

namespace EmployeeGateway.BL.Services;

public class TransactionsListener : BackgroundService
    {
        private readonly IWebSocketUserDb _userDb;
        private readonly IServiceProvider _services;
        private readonly ILogger<TransactionsListener> _logger;
        private readonly IFirebaseNotificationService _firebaseNotificationService;
        private IConnection _connection;
        private IModel _channel;

        public TransactionsListener(IConfiguration configuration, IServiceProvider service, IWebSocketUserDb hubUserDb, ILogger<TransactionsListener> logger, IFirebaseNotificationService firebaseNotificationService)
        {
            _userDb = hubUserDb;
            _services = service;
            _logger = logger;
            _firebaseNotificationService = firebaseNotificationService;

            _connection = CommonUtils.CreateConnection(configuration);
            _channel = _connection.CreateModel();
            _channel.QueueDeclare("employer_transfer_response_queue", true, false, false, new Dictionary<string, object>() { { "x-queue-type", "quorum" } });
        }

        protected override Task ExecuteAsync(CancellationToken stoppingToken)
        {
            stoppingToken.ThrowIfCancellationRequested();
            
            using var scope = _services.CreateScope();
            var userService = scope.ServiceProvider.GetRequiredService<IUserService>();
            
            var consumer = new AsyncEventingBasicConsumer(_channel);
            consumer.Received += async (model, eventArgs) =>
            {
                var body = eventArgs.Body.ToArray();
                var message = Encoding.UTF8.GetString(body);
                var transactionInfo = JsonSerializer.Deserialize<TransactionInfoDto>(message, UtilsService.jsonOptions);

                SocketUserDto? MinusSocketUser; 
                if (transactionInfo.Source.UserId == null || transactionInfo.Source.BillId == null)
                {
                    MinusSocketUser = null;
                }
                else
                {
                    MinusSocketUser = _userDb.IsExists((Guid)transactionInfo.Source.UserId, (Guid)transactionInfo.Source.BillId);
                }
                
                if (MinusSocketUser != null)
                {
                    var transactionDto = new TransactionDto
                    {
                        Id = transactionInfo.Id,
                        BillId = transactionInfo.Source.BillId.ToString(),
                        BalanceChange = -transactionInfo.Target.Amount,
                        Reason = transactionInfo.Target.Type,
                        Instant = transactionInfo.Instant
                    };
                    var transactionJson = JsonSerializer.Serialize(transactionDto, UtilsService.jsonOptions);
                    var transactionBody = Encoding.UTF8.GetBytes(transactionJson);
                    await MinusSocketUser.WebSocket.SendAsync(transactionBody, System.Net.WebSockets.WebSocketMessageType.Text, true, CancellationToken.None);
                }
                else if (transactionInfo.Source.UserId != null || transactionInfo.Source.BillId != null)
                {
                    var deviceToken = await userService.GetMessagingToken((Guid)transactionInfo.Source.UserId);
                    if (deviceToken != null)
                    {
                        await _firebaseNotificationService.SendNotificationAsync(deviceToken, "Списание",
                            $"{-transactionInfo.Source.Amount} {transactionInfo.Source.Currency} со счёта {transactionInfo.Source.BillId}");
                    }
                }

                SocketUserDto? PlusSocketUser;
                if (transactionInfo.Target.UserId == null || transactionInfo.Target.BillId == null)
                {
                    PlusSocketUser = null;
                }
                else
                {
                    PlusSocketUser = _userDb.IsExists((Guid)transactionInfo.Target.UserId, (Guid)transactionInfo.Target.BillId);
                }
                
                if (PlusSocketUser != null)
                {
                    var transactionDto = new TransactionDto
                    {
                        Id = transactionInfo.Id,
                        BillId = transactionInfo.Target.BillId.ToString(),
                        BalanceChange = transactionInfo.Target.Amount,
                        Reason = transactionInfo.Source.Type,
                        Instant = transactionInfo.Instant
                    };
                    var transactionJson = JsonSerializer.Serialize(transactionDto, UtilsService.jsonOptions);
                    var transactionBody = Encoding.UTF8.GetBytes(transactionJson);
                    await PlusSocketUser.WebSocket.SendAsync(transactionBody, System.Net.WebSockets.WebSocketMessageType.Text, true, CancellationToken.None);
                }
                else if (transactionInfo.Target.UserId != null && transactionInfo.Target.BillId != null)
                {
                    var deviceToken = await userService.GetMessagingToken((Guid)transactionInfo.Target.UserId);
                    if (deviceToken != null)
                    {
                        await _firebaseNotificationService.SendNotificationAsync(deviceToken, "Пополнение",
                        $"{transactionInfo.Source.Amount} {transactionInfo.Target.Currency} на счёт {transactionInfo.Target.BillId}");
                    }
                }

                _logger.LogInformation(message);

                _channel.BasicAck(eventArgs.DeliveryTag, false);
                await Task.Yield();
            };

            _channel.BasicConsume("employer_transfer_response_queue", false, consumer);

            return Task.CompletedTask;
        }

        public override Task StopAsync(CancellationToken cancellationToken)
        {
            _logger.LogInformation("MessageListener is stopping.");

            return Task.CompletedTask;
        }

        public override void Dispose()
        {
            _channel.Close();
            _connection.Close();
            base.Dispose();
        }
    }
