using EmployeeGateway.Common.DTO.Bill;

namespace EmployeeGateway.Common.DTO.Transaction;

public class TransactionBill
{
    public Guid? userId { get; set; }

    public Guid? billId { get; set; }
    
    public long Amount { get; set; }

    public BillTypeDto type { get; set; }

    public string currency { get; set; }

}