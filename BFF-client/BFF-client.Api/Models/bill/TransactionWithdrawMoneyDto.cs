namespace BFF_client.Api.Models.bill
{
    public class TransactionWithdrawMoneyDto
    {
        public Guid BillId { get; set; }

        public long Amount { get; set; }
    }
}
