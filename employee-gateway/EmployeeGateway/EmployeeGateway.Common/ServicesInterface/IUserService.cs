namespace EmployeeGateway.Common.ServicesInterface;

public interface IUserService
{
    Task SetMessagingToken(Guid userId, string token);

    Task<string?> GetMessagingToken(Guid userId);

}