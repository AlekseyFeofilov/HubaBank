using Credit.Primitives;

namespace Credit.Data.Requests.CircuitBreaker;

public class CreateRequest
{
    public Guid Id { get; set; }
    public string Name { get; set; }
    public CircuitBreakerStatus CircuitBreakerStatus { get; set; } = CircuitBreakerStatus.Closed;
    public DateTime OpenTime { get; set; } = DateTime.Now.AddDays(-1); 
}