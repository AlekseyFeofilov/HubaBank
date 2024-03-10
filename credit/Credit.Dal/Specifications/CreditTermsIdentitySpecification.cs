using Credit.Dal.Models;

namespace Credit.Dal.Specifications;

public class CreditTermsIdentitySpecification : IdentitySpecification<CreditTerms, Guid>
{
    public CreditTermsIdentitySpecification(Guid key) : base(key)
    {
    }

    public CreditTermsIdentitySpecification(IEnumerable<Guid>? keys) : base(keys)
    {
    }
}