using Credit.Data.Responses;
using EntityFrameworkCore.CommonTools;

namespace Credit.Lib.Feature.Credit.Update;

public class Request : Base.Update.Request<Dal.Models.Credit, CreditResponse>
{
    public Request(CreditResponse entity, Specification<Dal.Models.Credit>? specification) : base(specification)
    {
        Expression = credit =>
        {
            if (entity.AccountId != null)
            {
                credit.AccountId = entity.AccountId;
            }
        };
    }
}