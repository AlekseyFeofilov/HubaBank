using Credit.Dal.Specifications;
using Credit.Primitives;

namespace Credit.Lib.Feature.Utils.CircuitBreaker.Update;

public class Request : Base.Update.Request<Dal.Models.CircuitBreaker, Data.Responses.CircuitBreakerResponse>
{
    public Request(Guid id, CircuitBreakerStatus status)
        : base(new IdentitySpecification<Dal.Models.CircuitBreaker, Guid>(id))
    {
        var now = DateTime.Now;
        
        Expression = circuitBreaker =>
        {
            circuitBreaker.CircuitBreakerStatus = status;

            switch (status)
            {
                case CircuitBreakerStatus.Closed:
                    break;
                case CircuitBreakerStatus.Open:
                    circuitBreaker.OpenTime = now;
                    break;
                case CircuitBreakerStatus.HalfOpen:
                    break;
                default:
                    throw new ArgumentOutOfRangeException(nameof(status), status, null);
            }
        };
    }
}