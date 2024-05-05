using System.Net.WebSockets;
using System.Text;
using System.Text.Json;
using EmployeeGateway.BL;
using EmployeeGateway.Common.DTO;
using EmployeeGateway.Common.DTO.Transaction;
using EmployeeGateway.Common.ServicesInterface;
using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Options;

namespace EmployeeGateway.Common.System;

public class WebSocketMiddleware
    {
        private readonly RequestDelegate _next;
        private readonly ILogger<WebSocketMiddleware> _logger;
        private readonly IWebSocketUserDb _socketUserDb;
        private readonly UrlsMicroserviceOptions _configUrls;
        private readonly IHttpClientFactory _httpClientFactory;

        public WebSocketMiddleware(
            RequestDelegate next, 
            ILogger<WebSocketMiddleware> logger, 
            IWebSocketUserDb socketUserDb, 
            IOptions<UrlsMicroserviceOptions> options, 
            IHttpClientFactory clientFactory
            )
        {
            _next = next;
            _logger = logger;
            _socketUserDb = socketUserDb;
            _configUrls = options.Value;
            _httpClientFactory = clientFactory;
        }

        public async Task InvokeAsync(HttpContext context)
        {
            if (context.WebSockets.IsWebSocketRequest)
            {
                if (await IsRequstValid(context) == false)
                {
                    return;
                }

                WebSocket webSocket = await context.WebSockets.AcceptWebSocketAsync();

                _logger.LogInformation("Connect");

                var authHeader = context.Request.Headers.Authorization.FirstOrDefault();
                var userIdString = UtilsService.GetUserIdByHeader(authHeader);
                var userId = Guid.Parse(userIdString);
                var pathSections = context.Request.Path.Value.Split('/');
                var billIdString = pathSections.ElementAtOrDefault(pathSections.Length - 1);

                _socketUserDb.AddConnection(
                    userId,
                    new SocketUserDto { BillId = Guid.Parse(billIdString), WebSocket = webSocket }
                    );

                await GetOldTransactions(billIdString, webSocket, context.Request.Headers);

                await ReceiveMessage(webSocket, async (result, buffer) =>
                {
                    if (result.MessageType == WebSocketMessageType.Close)
                    {
                        _socketUserDb.RemoveConnection(userId);

                        _logger.LogInformation("Close");

                        await webSocket.CloseAsync(result.CloseStatus.Value, result.CloseStatusDescription, CancellationToken.None);

                        return;
                    }
                });
            }
            else
            {
                await _next(context);
            }
        }

        private async Task ReceiveMessage(WebSocket socket, Action<WebSocketReceiveResult, byte[]> handleMessage)
        {
            var buffer = new byte[1024 * 4];

            while (socket.State == WebSocketState.Open)
            {
                var result = await socket.ReceiveAsync(buffer: new ArraySegment<byte>(buffer),
                                                                   cancellationToken: CancellationToken.None);
                handleMessage(result, buffer);
            }
        }

        private async Task<bool> IsRequstValid(HttpContext httpContext)
        {
            var authHeader = httpContext.Request.Headers.Authorization.FirstOrDefault();
            if (authHeader == null)
            {
                httpContext.Response.StatusCode = StatusCodes.Status401Unauthorized;
                return false;
            }
            var userId = UtilsService.GetUserIdByHeader(authHeader);
            if (userId == null)
            {
                httpContext.Response.StatusCode = StatusCodes.Status401Unauthorized;
                return false;
            }
            httpContext.Request.Headers.TryGetValue("requestId", out var requestId);
            var profileWithPrivileges = await UtilsService.GetProfileWithPrivileges(authHeader, _configUrls, _httpClientFactory.CreateClient(), requestId);
            if (profileWithPrivileges == null)
            {
                httpContext.Response.StatusCode = StatusCodes.Status401Unauthorized;
                return false;
            }
            if (profileWithPrivileges.Privileges.Contains(Privileges.BILL_READ) == false)
            {
                httpContext.Response.StatusCode = StatusCodes.Status403Forbidden;
                return false;
            }
            var pathSections = httpContext.Request.Path.Value.Split('/');
            var billIdString = pathSections.ElementAtOrDefault(pathSections.Length - 1);
            Guid billId;
            if (billIdString == null || !Guid.TryParse(billIdString, out billId))
            {
                httpContext.Response.StatusCode = StatusCodes.Status400BadRequest;
                return false;
            }

            var IsBillBelongToUser = await UtilsService.IsBillBelongToUser(userId, billId, _configUrls, _httpClientFactory.CreateClient());
            if (IsBillBelongToUser == false)
            {
                httpContext.Response.StatusCode = StatusCodes.Status403Forbidden;
                return false;
            }

            return true;
        }

        private async Task GetOldTransactions(string billId, WebSocket webSocket, IHeaderDictionary headers)
        {
            var oldHistoryUrl = _configUrls.CoreUrl + "bills/" + billId + "/transactions";
            var message = new HttpRequestMessage(HttpMethod.Get, oldHistoryUrl);
            headers.TryGetValue("requestId", out var requestId);
            message.Headers.Add("requestId", requestId.SingleOrDefault());
            var response = await _httpClientFactory.CreateClient().SendAsync(message);

            if (response.IsSuccessStatusCode)
            {
                var downstreamResponse = await response.Content.ReadAsStringAsync();
                var jsonList = JsonSerializer.Deserialize<List<TransactionDto>>(downstreamResponse, UtilsService.jsonOptions);
                foreach (var transaction in jsonList)
                {
                    var transactionJson = JsonSerializer.Serialize(transaction, UtilsService.jsonOptions);
                    var transactionBody = Encoding.UTF8.GetBytes(transactionJson);
                    await webSocket.SendAsync(transactionBody, WebSocketMessageType.Text, true, CancellationToken.None);
                }
            }
            else
            {
                var errorBody = Encoding.UTF8.GetBytes("Error on get old transactions");
                await webSocket.SendAsync(errorBody, WebSocketMessageType.Text, true, CancellationToken.None);
            }
        }
    }
