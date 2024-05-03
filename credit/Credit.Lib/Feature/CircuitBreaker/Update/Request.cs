using Credit.Dal.Specifications;
using Credit.Primitives;

namespace Credit.Lib.Feature.CircuitBreaker.Update;

public class Request : Base.Update.Request<Dal.Models.CircuitBreaker, Data.Responses.CircuitBreakerResponse>
{
    public Request(Guid id, CircuitBreakerStatus status)
        : base(new IdentitySpecification<Dal.Models.CircuitBreaker, Guid>(id))
    {
        Expression = circuitBreaker =>
        {
            circuitBreaker.CircuitBreakerStatus = status;

            switch (status)
            {
                case CircuitBreakerStatus.Closed:
                    circuitBreaker.ErrorCount = 0;
                    break;
                case CircuitBreakerStatus.Open:
                    circuitBreaker.OpenTime = DateTime.Now;
                    break;
                case CircuitBreakerStatus.HalfOpen:
                    break;
                default:
                    throw new ArgumentOutOfRangeException(nameof(status), status, null);
            }
        };
    }
}