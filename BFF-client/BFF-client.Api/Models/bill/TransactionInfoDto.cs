using BFF_client.Api.model.bill;

namespace BFF_client.Api.Models.bill
{
    public class TransactionInfoDto
    {
        public string Id { get; set; }

        public TransactionBill Source { get; set; }

        public TransactionBill Target { get; set; }

        public string Instant { get; set; }
    }
}
