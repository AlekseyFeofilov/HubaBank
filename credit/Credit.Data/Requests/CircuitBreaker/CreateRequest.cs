using Credit.Primitives;

namespace Credit.Data.Requests.CircuitBreaker;

public class CreateRequest
{
    public Guid Id { get; set; }
    public string Name { get; set; }
    public CircuitBreakerStatus CircuitBreakerStatus { get; set; }
    public int ErrorCount { get; set; }
}