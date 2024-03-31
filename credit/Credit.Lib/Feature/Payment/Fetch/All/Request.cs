using Credit.Data;
using Credit.Data.Responses;
using EntityFrameworkCore.CommonTools;

namespace Credit.Lib.Feature.Payment.Fetch.All;

public class Request : Base.FetchAll.Request<Dal.Models.Payment, PaymentResponse>
{
    public Request(Specification<Dal.Models.Payment>? specification, PageFilter? pageFilter = null) : base(specification, pageFilter)
    {
    }
}