using Credit.Dal.Specifications;
using Credit.Data.Responses;

namespace Credit.Lib.Feature.CreditTerms.FetchById;

public class Request : Base.Fetch.Request<Dal.Models.CreditTerms, CreditTermsResponse>
{
    public Request(Guid id) : base(new CreditTermsIdentitySpecification(id))
    {
    }
}