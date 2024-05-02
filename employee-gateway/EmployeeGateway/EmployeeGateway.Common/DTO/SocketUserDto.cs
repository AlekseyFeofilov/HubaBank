using System.Net.WebSockets;

namespace EmployeeGateway.Common.DTO;

public class SocketUserDto
{
    public Guid BillId { get; set; }

    public WebSocket WebSocket { get; set; }

}