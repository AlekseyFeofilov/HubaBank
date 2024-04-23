using Credit.Data;
using Credit.Data.Responses;
using EntityFrameworkCore.CommonTools;

namespace Credit.Lib.Feature.CreditTerms.FetchAll;

public class Request : Base.FetchAll.Request<Dal.Models.CreditTerms, CreditTermsResponse>
{
    public Request(Specification<Dal.Models.CreditTerms>? specification = null, PageFilter? pageFilter = null) : base(specification, pageFilter)
    {
    }
}