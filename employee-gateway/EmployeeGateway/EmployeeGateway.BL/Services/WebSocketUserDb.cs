using System.Collections.Concurrent;
using EmployeeGateway.Common.DTO;
using EmployeeGateway.Common.ServicesInterface;

namespace EmployeeGateway.BL.Services;

public class WebSocketUserDb : IWebSocketUserDb
{
    private ConcurrentDictionary<Guid, SocketUserDto> userBillDictionary = new ConcurrentDictionary<Guid, SocketUserDto>();

    public void AddConnection(Guid userId, SocketUserDto socketUser)
    {
        userBillDictionary.AddOrUpdate(userId, socketUser, (key, oldValue) => socketUser);
    }

    public SocketUserDto? IsExists(Guid userId, Guid billId)
    {
        userBillDictionary.TryGetValue(userId, out SocketUserDto? socketUser);
        if (socketUser != null && socketUser.BillId == billId)
        {
            return socketUser;
        }
        return null;
    }

    public void RemoveConnection(Guid userId)
    {
        userBillDictionary.Remove(userId, out _);
    }
}
