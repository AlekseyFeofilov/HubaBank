using EmployeeGateway.BL.Services;
using EmployeeGateway.Common.DTO;
using EmployeeGateway.Common.DTO.Transaction;
using EmployeeGateway.Common.ServicesInterface;
using EmployeeGateway.Common.System;
using Microsoft.AspNetCore.SignalR;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.Options;
using JsonSerializer = System.Text.Json.JsonSerializer;

namespace EmployeeGateway.BL.WebSocket;

public class AuthHubFilter : IHubFilter
    {
        public ValueTask<object?> InvokeMethodAsync(HubInvocationContext invocationContext, Func<HubInvocationContext, ValueTask<object?>> next)
        {
            return next(invocationContext);
        }

        public async Task OnConnectedAsync(HubLifetimeContext context, Func<HubLifetimeContext, Task> next)
        {
            var _configUrls = context.ServiceProvider.GetRequiredService<IOptions<UrlsMicroserviceOptions>>().Value;
            var _httpClientFactory = context.ServiceProvider.GetRequiredService<IHttpClientFactory>();

            var httpContext = context.Context.GetHttpContext();
            if (httpContext == null)
            {
                context.Context.Abort();
                return;
            }
            var authHeader = httpContext.Request.Headers.Authorization.FirstOrDefault();
            if (authHeader == null)
            {
                httpContext.Response.StatusCode = 401;
                context.Context.Abort();
                return;
            }
            var userId = UtilsService.GetUserIdByHeader(authHeader);
            if (userId == null)
            {
                httpContext.Response.StatusCode = 401;
                context.Context.Abort();
                return;
            }
            var profileWithPrivileges = await UtilsService.GetProfileWithPrivileges(authHeader, _configUrls, _httpClientFactory.CreateClient());
            if (profileWithPrivileges == null)
            {
                httpContext.Response.StatusCode = 401;
                context.Context.Abort();
                return;
            }
            if (profileWithPrivileges.Privileges.Contains(Privileges.BILL_READ) == false)
            {
                httpContext.Response.StatusCode = 403;
                context.Context.Abort();
                return;
            }
            var pathSections = httpContext.Request.Path.Value.Split('/');
            var billIdString = pathSections.ElementAtOrDefault(pathSections.Length - 3);
            Guid billId;
            if (billIdString == null || Guid.TryParse(billIdString, out billId))
            {
                httpContext.Response.StatusCode = 400;
                context.Context.Abort();
                return;
            }

            var IsBillBelongToUser = await UtilsService.IsBillBelongToUser(userId, billId, _configUrls, _httpClientFactory.CreateClient());
            if (IsBillBelongToUser == false)
            {
                httpContext.Response.StatusCode = 403;
                context.Context.Abort();
                return;
            }

            /*var hubUserDb = context.ServiceProvider.GetRequiredService<IWebSocketUserDb>();
            hubUserDb.AddConnection(Guid.Parse(userId), billId);*/

            await next(context);

            var oldHistoryUrl = _configUrls.CoreUrl + "bills/" + billId.ToString() + "/transactions";
            var message = new HttpRequestMessage(HttpMethod.Get, oldHistoryUrl);
            var response = await _httpClientFactory.CreateClient().SendAsync(message);

            if (response.IsSuccessStatusCode)
            {
                var downstreamResponse = await response.Content.ReadAsStringAsync();
                var body = JsonSerializer.Deserialize<List<TransactionDto>>(downstreamResponse, UtilsService.jsonOptions);
                foreach (var transaction in body)
                {
                    await context.Hub.Clients.Caller.SendAsync("", transaction);
                }
            }
            else
            {
                throw new HubException("Error on get old transactions");
            }
        }

        public Task OnDisconnectedAsync(HubLifetimeContext context, Exception? exception, Func<HubLifetimeContext, Exception?, Task> next)
        {
            var userId = context.Context.UserIdentifier;
            if (userId != null)
            {
                var hubUserDb = context.ServiceProvider.GetRequiredService<IWebSocketUserDb>();
                hubUserDb.RemoveConnection(Guid.Parse(userId));
            }
            return next(context, exception);
        }
    }
