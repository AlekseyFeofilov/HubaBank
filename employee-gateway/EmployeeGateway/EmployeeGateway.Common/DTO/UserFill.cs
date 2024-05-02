namespace EmployeeGateway.Models;

public class UserFill
{
    public string Id { get; set; }
    public FullName FullNameDto { get; set; }
    public string Phone { get; set; }
    public bool employee { get; set; }
    public List<string> AdditionPrivileges { get; set; }
    public List<string> Roles { get; set; }
    public List<string> Privileges { get; set; }
    public bool blocked { get; set; }
}