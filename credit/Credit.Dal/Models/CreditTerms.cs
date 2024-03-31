namespace Credit.Dal.Models;

public class CreditTerms : IIdentity<Guid>, IDeletable
{
    public Guid Id { get; set; }
    public string Title { get; set; }
    public float InterestRate { get; set; }
    public bool IsDeleted { get; set; }
}