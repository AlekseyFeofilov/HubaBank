using MediatR;

namespace Credit.Lib.Jobs;

public class JobAgent : IJobAgent
{
    private readonly IMediator _mediator;

    public JobAgent(IMediator mediator)
    {
        _mediator = mediator;
    }

    public Task Ping()
    {
        return _mediator.Send(new Feature.Ping.Request());
    }
}