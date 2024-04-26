using Credit.Dal.Specifications;
using Credit.Data.Responses;

namespace Credit.Lib.Feature.IdempotentRequest.Fetch;

public class Request : Base.Fetch.Request<Dal.Models.IdempotentRequest, IdempotenceRequestResponse?>
{
    public Guid Id { get; }

    public Request(Guid id) : base(new IdentitySpecification<Dal.Models.IdempotentRequest, Guid>(id))
    {
        Id = id;
    }
}