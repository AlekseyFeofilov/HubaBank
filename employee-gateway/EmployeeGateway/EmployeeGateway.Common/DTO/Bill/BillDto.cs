namespace EmployeeGateway.Common.DTO.Bill;

public class BillDto
{
    public string Id { get; set; }

    public string UserId { get; set; }

    public long Balance { get; set; }
        
    public string Currency {  get; set; }

    public bool isHidden { get; set; }
}
