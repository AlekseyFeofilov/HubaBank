using Credit.Data.Responses;

namespace Credit.Lib.Feature.CreditTerms.Create;

public class Request : Base.Add.Request<Dal.Models.CreditTerms, CreditTermsResponse>
{
    public Request(CreditTermsResponse entity) : base(entity)
    {
    }

    public Request(IReadOnlyCollection<CreditTermsResponse> entities) : base(entities)
    {
    }
}