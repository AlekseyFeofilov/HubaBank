using Credit.Primitives;

namespace Credit.Dal.Models;

public class CircuitBreaker : IIdentity<Guid>
{
    public Guid Id { get; set; }
    public string Name { get; set; }
    public CircuitBreakerStatus CircuitBreakerStatus { get; set; }
    public DateTime OpenTime { get; set; }
}