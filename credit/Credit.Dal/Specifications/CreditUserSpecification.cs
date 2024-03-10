using EntityFrameworkCore.CommonTools;

namespace Credit.Dal.Specifications;

public class CreditUserSpecification : Specification<Models.Credit>
{
    public CreditUserSpecification(Guid userId) : base(x => Equals(x.AccountId, userId))
    {
    }
}