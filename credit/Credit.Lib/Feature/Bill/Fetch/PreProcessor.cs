using Credit.Lib.Exceptions;
using Credit.Primitives;
using MediatR;
using MediatR.Pipeline;
using Utils.DateTime;

namespace Credit.Lib.Feature.Bill.Fetch;

public class PreProcessor : IRequestPreProcessor<Request>
{
    private readonly IMediator _mediator;

    //todo cделать по-человечески
    private readonly Guid _providerId = Guid.Parse("3fa85f64-5717-4562-b3fc-2c963f66afa6");

    public PreProcessor(IMediator mediator)
    {
        _mediator = mediator;
    }

    public async Task Process(Request request, CancellationToken cancellationToken)
    {
        var circuitBreaker = await _mediator.Send(new CircuitBreaker.Fetch.Request(_providerId), cancellationToken);

        switch (circuitBreaker.CircuitBreakerStatus)
        {
            case CircuitBreakerStatus.Open:
                await ProcessOpenCircuitBreaker(circuitBreaker, cancellationToken);
                break;
            case CircuitBreakerStatus.Closed:
                break;
            case CircuitBreakerStatus.HalfOpen:
                await ProcessHalfOpenCircuitBreaker(circuitBreaker, cancellationToken);
                return;
            default:
                throw new ArgumentOutOfRangeException();
        }
    }

    private async Task ProcessOpenCircuitBreaker(Data.Responses.CircuitBreakerResponse circuitBreaker,
        CancellationToken cancellationToken)
    {
        if (circuitBreaker.OpenTime.GetDifferenceInSeconds(DateTime.Now) > 10)
        {
            await _mediator.Send(new CircuitBreaker.Update.Request(circuitBreaker.Id, CircuitBreakerStatus.HalfOpen),
                cancellationToken);
        }

        throw new ServerUnavailableException("Core");
    }

    private async Task ProcessHalfOpenCircuitBreaker(Data.Responses.CircuitBreakerResponse circuitBreaker,
        CancellationToken cancellationToken)
    {
        if (circuitBreaker.OpenTime.GetDifferenceInSeconds(DateTime.Now) > 30)
        {
            await _mediator.Send(new CircuitBreaker.Update.Request(circuitBreaker.Id, CircuitBreakerStatus.Closed),
                cancellationToken);
            return;
        }

        if (new Random().Next(100) > 90)
        {
            return;
        }

        throw new ServerUnavailableException("Core");
    }
}