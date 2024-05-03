
using BFF_client.Api.Controllers;
using BFF_client.Api.HubaWebSocket;
using BFF_client.Api.model.bill;
using BFF_client.Api.Models.bill;
using BFF_client.Api.Services.User;
using FirebaseAdmin.Messaging;
using Microsoft.AspNetCore.SignalR;
using RabbitMQ.Client;
using RabbitMQ.Client.Events;
using System.Net.WebSockets;
using System.Text;
using System.Text.Json;
using System.Threading.Channels;

namespace BFF_client.Api.Services
{
    public class TransactionsListener : BackgroundService
    {
        private readonly IWebSocketUserDb _userDb;
        private readonly IServiceProvider _services;
        private readonly ILogger<TransactionsListener> _logger;
        private IConnection _connection;
        private IModel _channel;

        public TransactionsListener(
            IConfiguration configuration, 
            IWebSocketUserDb hubUserDb,
            IServiceProvider service,
            ILogger<TransactionsListener> logger)
        {
            _userDb = hubUserDb;
            _services = service;
            _logger = logger;

            _connection = CommonUtils.CreateConnection(configuration);
            _channel = _connection.CreateModel();
            _channel.QueueDeclare("client_transfer_response_queue", true, false, false, new Dictionary<string, object>() { { "x-queue-type", "quorum" } });
        }

        protected override Task ExecuteAsync(CancellationToken stoppingToken)
        {
            stoppingToken.ThrowIfCancellationRequested();

            var consumer = new AsyncEventingBasicConsumer(_channel);
            consumer.Received += async (model, eventArgs) =>
            {
                try
                {
                    using var scope = _services.CreateScope();
                    var userService = scope.ServiceProvider.GetRequiredService<IUserService>();

                    var body = eventArgs.Body.ToArray();
                    var message = Encoding.UTF8.GetString(body);
                    var transactionInfo = JsonSerializer.Deserialize<TransactionInfoDto>(message, ControllersUtils.jsonOptions);

                    SocketUserModel? MinusSocketUser;
                    if (transactionInfo.Source.UserId == null || transactionInfo.Source.BillId == null)
                    {
                        MinusSocketUser = null;
                    }
                    else
                    {
                        MinusSocketUser = _userDb.IsExists((Guid)transactionInfo.Source.UserId, (Guid)transactionInfo.Source.BillId);
                    }
                    if (MinusSocketUser != null && MinusSocketUser.WebSocket.State != WebSocketState.Open && MinusSocketUser.WebSocket.State != WebSocketState.CloseReceived)
                    {
                        _userDb.RemoveConnection((Guid)transactionInfo.Source.UserId);
                        MinusSocketUser = null;
                    }
                    if (MinusSocketUser != null)
                    {
                        var transactionDto = new TransactionDto
                        {
                            Id = transactionInfo.Id,
                            BillId = transactionInfo.Source.BillId.ToString(),
                            BalanceChange = transactionInfo.Source.Amount,
                            Currency = transactionInfo.Source.Currency,
                            Reason = transactionInfo.Target.Type,
                            Instant = transactionInfo.Instant
                        };
                        var transactionJson = JsonSerializer.Serialize(transactionDto, ControllersUtils.jsonOptions);
                        var transactionBody = Encoding.UTF8.GetBytes(transactionJson);
                        await MinusSocketUser.WebSocket.SendAsync(transactionBody, WebSocketMessageType.Text, true, CancellationToken.None);
                    }
                    else if (transactionInfo.Source.UserId != null && transactionInfo.Source.BillId != null)
                    {
                        var deviceToken = await userService.GetMessagingToken((Guid)transactionInfo.Source.UserId);
                        if (deviceToken != null)
                        {
                            var firebaseMessage = new Message
                            {
                                Token = deviceToken,
                                Notification = new Notification
                                {
                                    Title = $"Списание",
                                    Body = $"{transactionInfo.Source.Amount} {transactionInfo.Source.Currency} со счёта {transactionInfo.Source.BillId}"
                                }
                            };
                            await FirebaseMessaging.DefaultInstance.SendAsync(firebaseMessage);
                        }
                    }

                    SocketUserModel? PlusSocketUser;
                    if (transactionInfo.Target.UserId == null || transactionInfo.Target.BillId == null)
                    {
                        PlusSocketUser = null;
                    }
                    else
                    {
                        PlusSocketUser = _userDb.IsExists((Guid)transactionInfo.Target.UserId, (Guid)transactionInfo.Target.BillId);
                    }
                    if (PlusSocketUser != null && PlusSocketUser.WebSocket.State != WebSocketState.Open && PlusSocketUser.WebSocket.State != WebSocketState.CloseReceived)
                    {
                        _userDb.RemoveConnection((Guid)transactionInfo.Target.UserId);
                        PlusSocketUser = null;
                    }
                    if (PlusSocketUser != null)
                    {
                        var transactionDto = new TransactionDto
                        {
                            Id = transactionInfo.Id,
                            BillId = transactionInfo.Target.BillId.ToString(),
                            BalanceChange = transactionInfo.Target.Amount,
                            Currency = transactionInfo.Target.Currency,
                            Reason = transactionInfo.Source.Type,
                            Instant = transactionInfo.Instant
                        };
                        var transactionJson = JsonSerializer.Serialize(transactionDto, ControllersUtils.jsonOptions);
                        var transactionBody = Encoding.UTF8.GetBytes(transactionJson);
                        await PlusSocketUser.WebSocket.SendAsync(transactionBody, System.Net.WebSockets.WebSocketMessageType.Text, true, CancellationToken.None);
                    }
                    else if (transactionInfo.Target.UserId != null && transactionInfo.Target.BillId != null)
                    {
                        var deviceToken = await userService.GetMessagingToken((Guid)transactionInfo.Target.UserId);
                        if (deviceToken != null)
                        {
                            var firebaseMessage = new Message
                            {
                                Token = deviceToken,
                                Notification = new Notification
                                {
                                    Title = $"Пополнение",
                                    Body = $"{transactionInfo.Target.Amount} {transactionInfo.Target.Currency} на счёт {transactionInfo.Target.BillId}"
                                }
                            };
                            await FirebaseMessaging.DefaultInstance.SendAsync(firebaseMessage);
                        }
                    }

                    _logger.LogInformation(message);

                    _channel.BasicAck(eventArgs.DeliveryTag, false);
                }
                catch (Exception ex)
                {
                    var body = eventArgs.Body.ToArray();
                    var message = Encoding.UTF8.GetString(body);
                    _logger.LogError("Ошибка при обработке ответа тразакции" + ex.Message + "\n" + message);
                }
                await Task.Yield();
            };

            _channel.BasicConsume("client_transfer_response_queue", false, consumer);

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
}
