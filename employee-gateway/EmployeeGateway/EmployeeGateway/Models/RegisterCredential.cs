namespace EmployeeGateway.Models;

public class RegisterCredential
{
    public FullName FullName { get; set; }
    public string Password { get; set; }
    public string Phone { get; set; }
}

public class FullName
{
    public string FirstName { get; set; }
    public string SecondName { get; set; }
    public string ThirdName { get; set; }
}