using Credit.Dal.Specifications;
using Credit.Data.Responses;

namespace Credit.Lib.Feature.CreditTerms.Delete;

public class Request : Base.Update.Request<Dal.Models.CreditTerms, CreditTermsResponse>
{
    public Request(Guid id) : base(new CreditTermsIdentitySpecification(id))
    {
        Expression = credit => credit.IsDeleted = true;
    }
}