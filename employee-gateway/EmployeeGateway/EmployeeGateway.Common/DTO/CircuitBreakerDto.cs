using EmployeeGateway.Common.Enum;

namespace EmployeeGateway.Common.DTO;

public class CircuitBreakerDto
{
    public Guid Id { get; set; }
    public MicroserviceName MicroserviceName { get; set; }
    public CircuitBreakerStatus CircuitBreakerStatus { get; set; }
    public int ErrorCount { get; set; }
    public int RequestCount { get; set; }
    
    public DateTime? OpenTime { get; set; }
}