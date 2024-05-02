namespace EmployeeGateway.Common.ServicesInterface;

public interface IFirebaseNotificationService
{
    Task SendNotificationAsync(string deviceToken, string title, string body);
}