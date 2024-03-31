using BFF_client.Api.model.bill;

namespace BFF_client.Api.Models.bill
{
    public class TransactionBill
    {
        public Guid? userId { get; set; }

        public Guid? billId { get; set; }

        public BillTypeDto type { get; set; }

        public string currency { get; set; }
    }
}
