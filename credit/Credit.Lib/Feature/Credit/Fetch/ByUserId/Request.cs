using Credit.Dal.Specifications;
using Credit.Data;
using Credit.Data.Responses;

namespace Credit.Lib.Feature.Credit.Fetch.ByUserId;

public class Request : Base.FetchAll.Request<Dal.Models.Credit, CreditResponse>
{
    public Request(Guid userId, PageFilter? pageFilter) : base(new CreditUserSpecification(userId) &&
        new ActiveOnlySpecification<Dal.Models.Credit>(), pageFilter)
    {
    }
}