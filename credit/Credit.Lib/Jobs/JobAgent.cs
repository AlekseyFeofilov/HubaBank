using Credit.Lib.Feature.Payment.EnqueueTodayPayments;
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

    public Task EnqueueTodayPayments()
    {
        return _mediator.Send(new Request());
    }

    public Task EnqueuePayment(Guid paymentId)
    {
        return _mediator.Send(new Feature.Payment.Handle.Request(paymentId));
    }

    public Task EnqueueCreditActivation(Guid creditId)
    {
        return _mediator.Send(new Feature.Credit.Activate.Request(creditId));
    }

    public Task EnqueueCreditArrearsActualisation(Guid creditId)
    {
        return _mediator.Send(new Feature.Credit.Arrears.Actualise.Request(creditId));
    }
}