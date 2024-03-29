namespace BFF_client.Api.model.bill
{
    public class TransactionDto
    {
        public string Id { get; set; }

        public string BillId { get; set; }

        public long BalanceChange { get; set; }

        public ReasonDto Reason { get; set; }

        public string Instant { get; set; }
    }
}
