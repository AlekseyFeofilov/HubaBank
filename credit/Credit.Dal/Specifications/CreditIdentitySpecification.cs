namespace Credit.Dal.Specifications;

public class CreditIdentitySpecification : IdentitySpecification<Models.Credit, Guid>
{
    public CreditIdentitySpecification(Guid key) : base(key)
    {
    }

    public CreditIdentitySpecification(IEnumerable<Guid>? keys) : base(keys)
    {
    }
}