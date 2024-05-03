namespace BFF_client.Api.Models.bill
{
    public class TransactionGiveMoneyDto
    {
        public Guid BillId { get; set; }

        public long Amount { get; set; }
    }
}
