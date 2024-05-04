using EmployeeGateway.Common.DTO;
using EmployeeGateway.Common.Enum;

namespace EmployeeGateway.Common.ServicesInterface;

public interface ICircuitBreakerService
{
    Task<CircuitBreakerDto?> GetCircuitBreaker(MicroserviceName microserviceName);
    Task CreateCircuitBreaker(MicroserviceName microserviceName);
    Task ChangeCircuitBreakerModel(CircuitBreakerDto model);
    Task OpenCircuitBreaker(MicroserviceName microserviceName);
    Task CloseCircuitBreaker(MicroserviceName microserviceName);
    Task HalfOpenCircuitBreaker(MicroserviceName microserviceName);
    Task CheckStatus(MicroserviceName microserviceName);
}