namespace BFF_client.Api.Models.Credit
{
    public class CreditTermsDto
    {
        public Guid Id { get; set; }

        public float InterestRate { get; set; }

        public string title { get; set; }

        public bool IsDeleted { get; set; }
    }
}
