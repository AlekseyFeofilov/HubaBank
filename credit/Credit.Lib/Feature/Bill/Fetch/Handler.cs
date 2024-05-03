using Core.Provider.v2;
using Credit.Primitives;
using MediatR;

namespace Credit.Lib.Feature.Bill.Fetch;

public class Handler : IRequestHandler<Request, BillDtoV2>
{
    private readonly ICoreProviderV2 _coreProviderV2;
    private readonly IMediator _mediator;
    private readonly Guid _providerId = Guid.Parse("3fa85f64-5717-4562-b3fc-2c963f66afa6");

    public Handler(ICoreProviderV2 coreProviderV2, IMediator mediator)
    {
        _coreProviderV2 = coreProviderV2;
        _mediator = mediator;
    }

    public async Task<BillDtoV2> Handle(Request request, CancellationToken cancellationToken)
    {
        var circuitBreaker = await _mediator.Send(new CircuitBreaker.Fetch.Request(_providerId), cancellationToken);
        
        for (var retry = 0; ; retry++)
        {
            try
            {
                var bill = await _coreProviderV2.GetBillV2Async(request.BillId, cancellationToken);

                if (circuitBreaker.CircuitBreakerStatus is CircuitBreakerStatus.HalfOpen)
                {
                    await _mediator.Send(new CircuitBreaker.Update.Request(_providerId, CircuitBreakerStatus.Closed), cancellationToken);
                }
                
                await _mediator.Send(new CircuitBreaker.IncreaseErrorCount.Request(_providerId, retry), cancellationToken);
                return bill;
            }
            catch (Exception)
            {
                if (circuitBreaker.ErrorCount + retry > 50 && circuitBreaker.CircuitBreakerStatus is not CircuitBreakerStatus.HalfOpen)
                {
                    await _mediator.Send(new CircuitBreaker.Update.Request(_providerId, CircuitBreakerStatus.Open), cancellationToken);
                    throw;
                }

                if (retry == 5)
                {
                    await _mediator.Send(new CircuitBreaker.IncreaseErrorCount.Request(_providerId, retry), cancellationToken);
                    throw;
                }

                await Task.Delay(100 * (int)Math.Pow(2, retry), cancellationToken);
            }
        }
    }
}