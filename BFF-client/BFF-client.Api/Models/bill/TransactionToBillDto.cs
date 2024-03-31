namespace BFF_client.Api.model.bill
{
    public class TransactionToBillDto
    {
        public Guid SourceBillId { get; set; }

        public Guid TargetBillId { get; set; }

        public long Amount { get; set; }
    }
}
