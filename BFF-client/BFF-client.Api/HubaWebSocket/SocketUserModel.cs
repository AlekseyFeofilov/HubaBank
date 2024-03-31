using System.Net.WebSockets;

namespace BFF_client.Api.HubaWebSocket
{
    public class SocketUserModel
    {
        public Guid BillId { get; set; }

        public WebSocket WebSocket { get; set; }
    }
}
