using EmployeeGateway.Common.DTO.Bill;

namespace EmployeeGateway.Common.DTO.Transaction;

public class TransactionBill
{
    public Guid? UserId { get; set; }

    public Guid? BillId { get; set; }
    
    public long Amount { get; set; }

    public BillTypeDto Type { get; set; }

    public string Currency { get; set; }

}