using MediatR;

namespace Credit.Lib.Feature.Utils.Setting.Seed;

public class Handler : IRequestHandler<Request>
{
    private readonly IMediator _mediator;

    public Handler(IMediator mediator)
    {
        _mediator = mediator;
    }

    public async Task Handle(Request request, CancellationToken cancellationToken)
    {
        var settings = new Data.Requests.Setting.CreateRequest[]
        {
            new()
            {
                Id = Guid.Parse("3fa85f64-5717-4562-b3fc-2c963f66afa6"),
                SettingName = "RandomFaultEnable",
                Value = "false"
            }
        };
        
        foreach (var setting in settings)
        {
            var existingSetting = await _mediator.Send(new Fetch.Request(setting.Id), cancellationToken);

            // ReSharper disable once ConditionIsAlwaysTrueOrFalseAccordingToNullableAPIContract
            if (existingSetting != null)
            {
                continue;
            }
            
            await _mediator.Send(new Create.Request(setting), cancellationToken);
        }
    }
}