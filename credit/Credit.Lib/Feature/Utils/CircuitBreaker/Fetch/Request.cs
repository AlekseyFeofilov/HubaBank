using Credit.Dal.Specifications;

namespace Credit.Lib.Feature.Utils.CircuitBreaker.Fetch;

public class Request : Base.Fetch.Request<Dal.Models.CircuitBreaker, Data.Responses.CircuitBreakerResponse>
{
    public Request(Guid id) : base(new IdentitySpecification<Dal.Models.CircuitBreaker, Guid>(id))
    {
    }
}