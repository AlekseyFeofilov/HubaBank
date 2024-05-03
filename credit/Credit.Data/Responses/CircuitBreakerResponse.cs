using Credit.Primitives;

namespace Credit.Data.Responses;

public class CircuitBreakerResponse
{
    public Guid Id { get; set; }
    public string Name { get; set; }
    public CircuitBreakerStatus CircuitBreakerStatus { get; set; } = CircuitBreakerStatus.Closed; 
    public int ErrorCount { get; set; }
    public DateTime OpenTime { get; set; }
}