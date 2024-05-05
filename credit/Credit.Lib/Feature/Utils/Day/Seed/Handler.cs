using MediatR;

namespace Credit.Lib.Feature.Utils.Day.Seed;

public class Handler : IRequestHandler<Request>
{
    private readonly IMediator _mediator;

    public Handler(IMediator mediator)
    {
        _mediator = mediator;
    }

    public async Task Handle(Request request, CancellationToken cancellationToken)
    {
        var settings = new Data.Requests.Day.CreateRequest[]
        {
            new()
            {
                Id = Guid.Parse("3fa85f64-5717-4562-b3fc-2c963f66afa6"),
                Now = DateTime.Now,
            }
        };
        
        foreach (var setting in settings)
        {
            var existingDay = await _mediator.Send(new Day.Fetch.Request(), cancellationToken);

            // ReSharper disable once ConditionIsAlwaysTrueOrFalseAccordingToNullableAPIContract
            if (existingDay != null)
            {
                continue;
            }
            
            await _mediator.Send(new Day.Create.Request(setting), cancellationToken);
        }
    }
}