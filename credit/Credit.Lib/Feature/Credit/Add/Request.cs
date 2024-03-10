using Credit.Data.Responses;

namespace Credit.Lib.Feature.Credit.Add;

public class Request : Base.Add.Request<Dal.Models.Credit, CreditResponse>
{
    public Request(CreditResponse entity) : base(entity)
    {
    }

    public Request(IReadOnlyCollection<CreditResponse> entities) : base(entities)
    {
    }
}