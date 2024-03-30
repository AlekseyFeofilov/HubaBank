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

    public Task EnqueueNextDayPayments()
    {
        return _mediator.Send(new Feature.Payment.EnqueueForNextDay.Request());
    }

    public Task EnqueuePayment(Guid id)
    {
        return _mediator.Send(new Feature.Payment.Handle.Request(id));
    }
}