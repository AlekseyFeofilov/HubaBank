using BFF_client.Api.Controllers;
using BFF_client.Api.model;
using BFF_client.Api.Models;
using Microsoft.AspNetCore.SignalR;
using System.Net.Http;

namespace BFF_client.Api.WebSocket
{
    public class TransactionsHub : Hub
    {
        /*public override async Task OnConnectedAsync()
        {
            var authHeader = Context.GetHttpContext().Request.Headers.Authorization.FirstOrDefault();
            if (authHeader == null)
            {
                return Unauthorized();
            }
            var userId = ControllersUtils.GetUserIdByHeader(authHeader);
            if (userId == null)
            {
                return Unauthorized();
            }
            var profileWithPrivileges = await this.GetProfileWithPrivileges(authHeader, _configUrls, _httpClientFactory.CreateClient());
            if (profileWithPrivileges == null)
            {
                return Unauthorized();
            }
            if (profileWithPrivileges.Privileges.Contains(Privileges.TRANSACTION_READ) == false)
            {
                return Forbid();
            }
            await base.OnConnectedAsync();
        }*/
    }
}
