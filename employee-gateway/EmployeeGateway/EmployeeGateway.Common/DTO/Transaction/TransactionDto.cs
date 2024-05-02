using EmployeeGateway.Common.DTO.Bill;

namespace EmployeeGateway.Common.DTO.Transaction;

public class TransactionDto
{
    public string Id { get; set; }

    public string BillId { get; set; }

    public long BalanceChange { get; set; }

    public BillTypeDto Reason { get; set; }

    public string Instant { get; set; }

}