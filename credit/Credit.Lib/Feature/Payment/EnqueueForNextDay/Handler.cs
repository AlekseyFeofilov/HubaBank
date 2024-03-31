using Credit.Dal.Specifications;
using MediatR;

namespace Credit.Lib.Feature.Payment.EnqueueForNextDay;

public class Handler : IRequestHandler<Request>
{
    private readonly IMediator _mediator;

    public Handler(IMediator mediator)
    {
        _mediator = mediator;
    }

    public async Task Handle(Request request, CancellationToken cancellationToken)
    {
        Console.WriteLine("Start enqueueing payments for next day");
        var payments = await _mediator.Send(new Fetch.All.Request(
            new TomorrowPaymentSpecification()), cancellationToken);

        foreach (var payment in payments)
        {
            await _mediator.Send(new Enqueue.Request(payment.Id), cancellationToken);
        }
    }
}