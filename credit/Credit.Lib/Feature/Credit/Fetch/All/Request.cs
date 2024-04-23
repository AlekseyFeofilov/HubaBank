using Credit.Data;
using Credit.Data.Responses;
using EntityFrameworkCore.CommonTools;

namespace Credit.Lib.Feature.Credit.Fetch.All;

public class Request : Base.FetchAll.Request<Dal.Models.Credit, CreditResponse>
{
    public Request(Specification<Dal.Models.Credit>? specification, PageFilter? pageFilter) : base(specification, pageFilter)
    {
    }
}