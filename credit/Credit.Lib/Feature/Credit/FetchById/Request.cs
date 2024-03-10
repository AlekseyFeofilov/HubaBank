using Credit.Dal.Specifications;
using Credit.Data.Responses;

namespace Credit.Lib.Feature.Credit.FetchById;

public class Request : Base.Fetch.Request<Dal.Models.Credit, CreditResponse>
{
    public Request(Guid id) : base(new CreditIdentitySpecification(id))
    {
    }
}