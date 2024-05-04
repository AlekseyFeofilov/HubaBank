namespace BFF_client.Api.Models.Credit
{
    public class CreateCreditDto
    {
        public Guid? AccountId { get; set; } = null;

        public Guid BillId { get; set; }

        public long Principal { get; set; }

        public DateOnly CompletionDate { get; set; }

        public Guid CreditTermsId { get; set; }
    }
}
