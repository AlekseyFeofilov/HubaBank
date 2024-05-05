using Credit.Dal.Specifications;
using MediatR;
using Microsoft.Extensions.Logging;

namespace Credit.Lib.Feature.Payment.EnqueueTodayPayments;

public class Handler : IRequestHandler<Request>
{
    private readonly IMediator _mediator;
    private readonly ILogger<Handler> _logger;

    public Handler(IMediator mediator, ILogger<Handler> logger)
    {
        _mediator = mediator;
        _logger = logger;
    }

    public async Task Handle(Request request, CancellationToken cancellationToken)
    {
        _logger.LogWarning("Start enqueueing payments for today");
        var now = (await _mediator.Send(new Utils.Day.Fetch.Request(), cancellationToken)).Now;
        
        var payments = await _mediator.Send(new Fetch.All.Request(
            new PaymentDayPaymentSpecification(now)), cancellationToken);

        foreach (var payment in payments)
        {
            _logger.LogWarning("Payment with id {paymentId} of credit with id {creditId} was enqueued", 
                payment.Id, payment.CreditId);
            await _mediator.Send(new Enqueue.Request(payment.Id), cancellationToken);
        }
    }
}