namespace EmployeeGateway.Models;

public class LoginCredentials
{
    public string PhoneNumber { get; set; }

    public string Password { get; set; }
    public Guid MessagingToken { get; set; }

}