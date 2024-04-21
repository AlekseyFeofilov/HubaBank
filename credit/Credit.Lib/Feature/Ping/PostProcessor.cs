using MediatR;
using MediatR.Pipeline;

namespace Credit.Lib.Feature.Ping;

public class PostProcessor : IRequestPostProcessor<Request, Unit>
{
    private readonly IMediator _mediator;

    public PostProcessor(IMediator mediator)
    {
        _mediator = mediator;
    }

    public async Task Process(Request request, Unit response, CancellationToken cancellationToken)
    {
        Console.WriteLine("PostProcessor executed");
        await _mediator.Publish(new Notification(), cancellationToken);
        Console.WriteLine("Notification published");
    }
}