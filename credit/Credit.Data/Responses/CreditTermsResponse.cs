namespace Credit.Data.Responses;

public class CreditTermsResponse
{
    public Guid Id { get; set; }
    public float InterestRate { get; set; }
    public string Title { get; set; }
}