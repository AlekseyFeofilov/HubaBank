namespace Credit.Dal.Models;

public class CreditTerms : IIdentity<Guid>, IDeletable
{
    public Guid Id { get; }
    public bool IsDeleted { get; set; }
}