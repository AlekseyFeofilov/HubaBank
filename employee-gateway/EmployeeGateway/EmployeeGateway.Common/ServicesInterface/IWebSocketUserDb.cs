using EmployeeGateway.Common.DTO;

namespace EmployeeGateway.Common.ServicesInterface;

public interface IWebSocketUserDb
{
    void AddConnection(Guid userId, SocketUserDto socketUser);

    SocketUserDto? IsExists(Guid userId, Guid billId);

    void RemoveConnection(Guid userId);

}