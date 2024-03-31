using BFF_client.Api.Controllers;
using Microsoft.AspNetCore.SignalR;

namespace BFF_client.Api.HubaWebSocket
{
    public class UserIdProvider : IUserIdProvider
    {
        public string? GetUserId(HubConnectionContext connection)
        {
            var authHeader = connection.GetHttpContext()?.Request.Headers.Authorization.FirstOrDefault();
            if (authHeader == null) { return null; }
            return ControllersUtils.GetUserIdByHeader(authHeader);
        }
    }
}
