using Core.Provider.v3;
using Credit.Primitives;
using LogService.Provider.v2;
using MediatR;

namespace Credit.Lib.Feature.Bill.Fetch;

public class Handler : IRequestHandler<Request, BillDtoV2>
{
    private readonly ICoreProviderV3 _coreProvider;
    private readonly ILogServiceProviderV2 _logServiceProvider;
    private readonly IMediator _mediator;
    private readonly Guid _providerId = Guid.Parse("3fa85f64-5717-4562-b3fc-2c963f66afa6");

    public Handler(ICoreProviderV3 coreProvider, IMediator mediator, ILogServiceProviderV2 logServiceProvider)
    {
        _coreProvider = coreProvider;
        _mediator = mediator;
        _logServiceProvider = logServiceProvider;
    }

    public async Task<BillDtoV2> Handle(Request request, CancellationToken cancellationToken)
    {
        var circuitBreaker = await _mediator.Send(new CircuitBreaker.Fetch.Request(_providerId), cancellationToken);
        
        for (var retry = 0; ; retry++)
        {
            try
            {
                var bill = await _coreProvider.GetBillV3Async(request.BillId, request.RequestId.ToString(), cancellationToken);

                if (circuitBreaker.CircuitBreakerStatus is CircuitBreakerStatus.HalfOpen)
                {
                    await _mediator.Send(new CircuitBreaker.Update.Request(_providerId, CircuitBreakerStatus.Closed), cancellationToken);
                }
                
                return bill;
            }
            catch (Exception)
            {
                var faultPercent = await _logServiceProvider.GetPercentInSecondsAsync(10, "core", cancellationToken);
                
                if (circuitBreaker.CircuitBreakerStatus is not CircuitBreakerStatus.HalfOpen && faultPercent > 0.7)
                    // && circuitBreaker.ErrorCount + retry > 10
                    // && 1.0 * circuitBreaker.ErrorCount / (circuitBreaker.SuccessCount + circuitBreaker.ErrorCount) > 0.7)
                {
                    await _mediator.Send(new CircuitBreaker.Update.Request(_providerId, CircuitBreakerStatus.Open), cancellationToken);
                    throw;
                }

                if (retry == 5)
                {
                    // await _mediator.Send(new CircuitBreaker.IncreaseErrorCount.Request(_providerId, retry), cancellationToken);
                    throw;
                }

                await Task.Delay(100 * (int)Math.Pow(2, retry), cancellationToken);
            }
        }
    }
}