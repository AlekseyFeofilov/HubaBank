using Credit.Dal.Specifications;
using Credit.Data.Responses;

namespace Credit.Lib.Feature.Credit.FetchById;

public class Request : Base.Fetch.Request<Dal.Models.Credit, CreditResponse>
{
    public Guid Id { get; }

    public Request(Guid id) : base(new CreditIdentitySpecification(id) &&
                                   new ActiveOnlySpecification<Dal.Models.Credit>())
    {
        Id = id;
    }
}