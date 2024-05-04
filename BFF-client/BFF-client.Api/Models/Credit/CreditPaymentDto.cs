namespace BFF_client.Api.Models.Credit
{
    public class CreditPaymentDto
    {
        public Guid Id { get; set; }

        public Guid CreditId { get; set; }

        public string PaymentStatus { get; set; }

        public DateOnly PaymentDay { get; set; }

        public long PaymentAmount { get; set; }

        public long Interest { get; set; }

        public long Arrears { get; set; }

        public long ArrearsInterest { get; set; }
    }
}
