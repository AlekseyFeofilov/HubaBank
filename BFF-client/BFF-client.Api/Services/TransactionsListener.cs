
using BFF_client.Api.Controllers;
using BFF_client.Api.HubaWebSocket;
using BFF_client.Api.model.bill;
using BFF_client.Api.Models.bill;
using Microsoft.AspNetCore.SignalR;
using RabbitMQ.Client;
using RabbitMQ.Client.Events;
using System.Text;
using System.Text.Json;
using System.Threading.Channels;

namespace BFF_client.Api.Services
{
    public class TransactionsListener : BackgroundService
    {
        private readonly IWebSocketUserDb _userDb;
        private readonly ILogger<TransactionsListener> _logger;
        private IConnection _connection;
        private IModel _channel;

        public TransactionsListener(IConfiguration configuration, IWebSocketUserDb hubUserDb, ILogger<TransactionsListener> logger)
        {
            _userDb = hubUserDb;
            _logger = logger;

            _connection = CommonUtils.CreateConnection(configuration);
            _channel = _connection.CreateModel();
            _channel.QueueDeclare("client_transfer_response_queue", true, false, false, new Dictionary<string, object>() { { "x-queue-type", "quorum" } });
        }

        protected override Task ExecuteAsync(CancellationToken stoppingToken)
        {
            var consumer = new EventingBasicConsumer(_channel);
            consumer.Received += async (model, eventArgs) =>
            {
                var body = eventArgs.Body.ToArray();
                var message = Encoding.UTF8.GetString(body);
                var transactionInfo = JsonSerializer.Deserialize<TransactionInfoDto>(message, ControllersUtils.jsonOptions);

                SocketUserModel? MinusSocketUser; 
                if (transactionInfo.Source.userId == null || transactionInfo.Source.billId == null)
                {
                    MinusSocketUser = null;
                }
                else
                {
                    MinusSocketUser = _userDb.IsExists((Guid)transactionInfo.Source.userId, (Guid)transactionInfo.Source.billId);
                }
                if (MinusSocketUser != null)
                {
                    var transactionDto = new TransactionDto
                    {
                        Id = transactionInfo.Id,
                        BillId = transactionInfo.Source.billId.ToString(),
                        BalanceChange = transactionInfo.Amount,
                        Reason = transactionInfo.Target.type,
                        Instant = transactionInfo.Instant
                    };
                    var transactionJson = JsonSerializer.Serialize(transactionDto, ControllersUtils.jsonOptions);
                    var transactionBody = Encoding.UTF8.GetBytes(transactionJson);
                    await MinusSocketUser.WebSocket.SendAsync(transactionBody, System.Net.WebSockets.WebSocketMessageType.Text, true, CancellationToken.None);
                }

                SocketUserModel? PlusSocketUser;
                if (transactionInfo.Target.userId == null || transactionInfo.Target.billId == null)
                {
                    PlusSocketUser = null;
                }
                else
                {
                    PlusSocketUser = _userDb.IsExists((Guid)transactionInfo.Target.userId, (Guid)transactionInfo.Target.billId);
                }
                if (PlusSocketUser != null)
                {
                    var transactionDto = new TransactionDto
                    {
                        Id = transactionInfo.Id,
                        BillId = transactionInfo.Target.billId.ToString(),
                        BalanceChange = transactionInfo.Amount,
                        Reason = transactionInfo.Source.type,
                        Instant = transactionInfo.Instant
                    };
                    var transactionJson = JsonSerializer.Serialize(transactionDto, ControllersUtils.jsonOptions);
                    var transactionBody = Encoding.UTF8.GetBytes(transactionJson);
                    await PlusSocketUser.WebSocket.SendAsync(transactionBody, System.Net.WebSockets.WebSocketMessageType.Text, true, CancellationToken.None);
                }

                _logger.LogInformation(message);

                _channel.BasicAck(eventArgs.DeliveryTag, false);
            };

            _channel.BasicConsume("client_transfer_response_queue", true, consumer);

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
