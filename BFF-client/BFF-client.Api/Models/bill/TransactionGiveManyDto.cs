namespace BFF_client.Api.Models.bill
{
    public class TransactionGiveManyDto
    {
        public Guid BillId { get; set; }

        public long Amount { get; set; }
    }
}
