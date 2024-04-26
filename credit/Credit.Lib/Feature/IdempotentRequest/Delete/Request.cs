using Credit.Dal.Specifications;
using MediatR;

namespace Credit.Lib.Feature.IdempotentRequest.Delete;

public class Request : Base.Delete.Request<Dal.Models.IdempotentRequest, Unit>
{
    public Request(Guid id) : base(new IdentitySpecification<Dal.Models.IdempotentRequest, Guid>(id))
    {
    }
}