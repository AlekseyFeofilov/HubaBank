using Microsoft.AspNetCore.SignalR;

namespace EmployeeGateway.BL.WebSocket;

public class TransactionsHub: Hub
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