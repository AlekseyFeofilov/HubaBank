namespace EmployeeGateway.Common.DTO.Transaction;

public class TransactionWithdrawMoneyDto
{
    public Guid BillId { get; set; }

    public long Amount { get; set; }
}