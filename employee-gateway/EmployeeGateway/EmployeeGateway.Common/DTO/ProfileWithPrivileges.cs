namespace EmployeeGateway.Common.DTO;

public class ProfileWithPrivileges
{
    public string Id { get; set; }

    public FullNameDto FullNameDto { get; set; }

    public string Phone { get; set; }

    public List<string> Privileges {  get; set; }

}