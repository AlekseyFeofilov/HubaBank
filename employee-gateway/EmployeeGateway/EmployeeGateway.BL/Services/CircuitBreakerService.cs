using EmployeeGateway.Common.DTO;
using EmployeeGateway.Common.Enum;
using EmployeeGateway.Common.ServicesInterface;
using EmployeeGateway.DAL;
using EmployeeGateway.DAL.Entity;
using Microsoft.EntityFrameworkCore;

namespace EmployeeGateway.BL.Services;

public class CircuitBreakerService: ICircuitBreakerService
{
    private readonly AppDbContext _appDbContext;

    public CircuitBreakerService(AppDbContext appDbContext)
    {
        _appDbContext = appDbContext;
    }

    public async Task<CircuitBreakerDto?> GetCircuitBreaker(MicroserviceName microserviceName)
    {
        var circuitBreaker = await _appDbContext.CircuitBreakers
            .Where(e => e.MicroserviceName == microserviceName)
            .Select(e => new CircuitBreakerDto
            {
                Id = e.Id,
                MicroserviceName = e.MicroserviceName,
                CircuitBreakerStatus = e.CircuitBreakerStatus,
                ErrorCount = e.ErrorCount,
                RequestCount = e.RequestCount
            })
            .FirstOrDefaultAsync();

        return circuitBreaker;
    }

    public async Task CheckStatus(MicroserviceName name)
    {
        var circuitBreaker = await _appDbContext.CircuitBreakers
            .Where(e => e.MicroserviceName == name)
            .FirstOrDefaultAsync();
        if (circuitBreaker.OpenTime == null)
            return;
        var changeTime = DateTime.UtcNow - circuitBreaker.OpenTime.Value.ToUniversalTime();
        
        if (circuitBreaker.CircuitBreakerStatus == CircuitBreakerStatus.Open && 
            changeTime.Seconds > 10)
        {
            await HalfOpenCircuitBreaker(MicroserviceName.User);
        }
            
        if (circuitBreaker.CircuitBreakerStatus == CircuitBreakerStatus.HalfOpen && 
            changeTime.Seconds > 30)
        {
            await CloseCircuitBreaker(MicroserviceName.User);
        }
    }

    public async Task OpenCircuitBreaker(MicroserviceName microserviceName)
    {
        var circuitBreaker = await _appDbContext.CircuitBreakers
            .Where(e => e.MicroserviceName == microserviceName)
            .FirstOrDefaultAsync();

        if (circuitBreaker != null)
        {
            circuitBreaker.CircuitBreakerStatus = CircuitBreakerStatus.Open;
            circuitBreaker.ErrorCount = 0;
            circuitBreaker.RequestCount = 0;
            circuitBreaker.OpenTime = DateTime.UtcNow;
        }

        await _appDbContext.SaveChangesAsync();
    }
    
    public async Task CloseCircuitBreaker(MicroserviceName microserviceName)
    {
        var circuitBreaker = await _appDbContext.CircuitBreakers
            .Where(e => e.MicroserviceName == microserviceName)
            .FirstOrDefaultAsync();

        if (circuitBreaker != null)
        {
            circuitBreaker.CircuitBreakerStatus = CircuitBreakerStatus.Closed;
            circuitBreaker.ErrorCount = 0;
            circuitBreaker.RequestCount = 0;
            circuitBreaker.OpenTime = null;
        }
        
        await _appDbContext.SaveChangesAsync();
    }
    
    public async Task HalfOpenCircuitBreaker(MicroserviceName microserviceName)
    {
        var circuitBreaker = await _appDbContext.CircuitBreakers
            .Where(e => e.MicroserviceName == microserviceName)
            .FirstOrDefaultAsync();

        if (circuitBreaker != null)
        {
            circuitBreaker.CircuitBreakerStatus = CircuitBreakerStatus.HalfOpen;
        }
        
        await _appDbContext.SaveChangesAsync();
    }

    public async Task ChangeCircuitBreakerModel(CircuitBreakerDto model)
    {
        var circuitBreaker = await _appDbContext.CircuitBreakers
            .Where(e => e.MicroserviceName == model.MicroserviceName && model.Id == e.Id)
            .FirstOrDefaultAsync();

        if (circuitBreaker != null)
        {
            circuitBreaker.CircuitBreakerStatus = model.CircuitBreakerStatus;
            circuitBreaker.ErrorCount = model.ErrorCount;
            circuitBreaker.RequestCount = model.RequestCount;
        }
        
        await _appDbContext.SaveChangesAsync();
    }

    public async Task CreateCircuitBreaker(MicroserviceName microserviceName)
    {
        var circuitBreaker = await _appDbContext.CircuitBreakers
            .Where(e => e.MicroserviceName == microserviceName)
            .FirstOrDefaultAsync();

        if (circuitBreaker != null)
            return;
        
        var entity = new CircuitBreakerEntity
        {
            MicroserviceName = microserviceName,
            CircuitBreakerStatus = CircuitBreakerStatus.Closed,
            ErrorCount = 0,
            RequestCount = 0
        };

        await _appDbContext.CircuitBreakers.AddAsync(entity);
        await _appDbContext.SaveChangesAsync();
    }
}