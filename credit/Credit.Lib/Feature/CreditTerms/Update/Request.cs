using Credit.Data.Responses;
using EntityFrameworkCore.CommonTools;

namespace Credit.Lib.Feature.CreditTerms.Update;

public class Request : Base.Update.Request<Dal.Models.CreditTerms, CreditTermsResponse>
{
    public Request(CreditResponse entity, Specification<Dal.Models.CreditTerms>? specification) : base(specification)
    {
        Expression = credit =>
        {
            // if (entity.AccountId != null)
            // {
            //     credit.AccountId = entity.AccountId;
            // }
        };
    }
}