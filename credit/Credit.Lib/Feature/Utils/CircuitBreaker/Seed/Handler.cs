using MediatR;

namespace Credit.Lib.Feature.Utils.CircuitBreaker.Seed;

public class Handler : IRequestHandler<Request>
{
    private readonly IMediator _mediator;

    public Handler(IMediator mediator)
    {
        _mediator = mediator;
    }

    public async Task Handle(Request request, CancellationToken cancellationToken)
    {
        var settings = new Data.Requests.CircuitBreaker.CreateRequest[]
        {
            new()
            {
                Id = Guid.Parse("3fa85f64-5717-4562-b3fc-2c963f66afa6"),
                Name = "CoreProvider",
            }
        };
        
        foreach (var setting in settings)
        {
            var existingCircuitBreaker = await _mediator.Send(new Fetch.Request(setting.Id), cancellationToken);

            // ReSharper disable once ConditionIsAlwaysTrueOrFalseAccordingToNullableAPIContract
            if (existingCircuitBreaker != null)
            {
                continue;
            }
            
            await _mediator.Send(new Create.Request(setting), cancellationToken);
        }
    }
}