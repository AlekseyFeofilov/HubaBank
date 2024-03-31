using BFF_client.Api.Controllers;
using BFF_client.Api.model;
using BFF_client.Api.Models;
using Microsoft.AspNetCore.SignalR;
using System.Net.Http;

namespace BFF_client.Api.HubaWebSocket
{
    public class TransactionsHub : Hub
    {
        public override async Task OnConnectedAsync()
        {
            await Clients.Caller.SendAsync("", "asdfa");
            await base.OnConnectedAsync();
        }

        public override async Task OnDisconnectedAsync(Exception? exception)
        {
            await base.OnDisconnectedAsync(exception);
        }
    }
}
