using EmployeeGateway.Common.Enum;

namespace EmployeeGateway.DAL.Entity;

public class CircuitBreakerEntity
{
    public Guid Id { get; set; }
    public MicroserviceName MicroserviceName { get; set; }
    public CircuitBreakerStatus CircuitBreakerStatus { get; set; }
    public int ErrorCount { get; set; }
    public int RequestCount { get; set; }
    public DateTime? OpenTime { get; set; }
}