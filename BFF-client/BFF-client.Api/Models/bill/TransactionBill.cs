using BFF_client.Api.model.bill;

namespace BFF_client.Api.Models.bill
{
    public class TransactionBill
    {
        public Guid? UserId { get; set; }

        public Guid? BillId { get; set; }

        public BillTypeDto Type { get; set; }

        public string Currency { get; set; }

        public long Amount { get; set; }
    }
}
