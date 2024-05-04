namespace EmployeeGateway.Common.DTO.Transaction;

public class TransactionInfoDto
{
    public string Id { get; set; }

    public TransactionBill Source { get; set; }

    public TransactionBill Target { get; set; }

    public string Instant { get; set; }


}