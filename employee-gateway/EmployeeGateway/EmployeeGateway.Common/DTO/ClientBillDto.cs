namespace EmployeeGateway.Models;

public class ClientBillDto
{
    public string Id { get; set; }

    public long Balance { get; set; }

    public string Currency { get; set; }

    public bool isHidden { get; set; }

}