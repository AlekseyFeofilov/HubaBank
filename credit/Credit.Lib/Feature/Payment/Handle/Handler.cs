using Core.Provider.v1;
using Credit.Data.Requests.Payment;
using Credit.Primitives;
using MediatR;
using Microsoft.Extensions.Logging;

namespace Credit.Lib.Feature.Payment.Handle;

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
        var payment = await _mediator.Send(new Fetch.ById.Request(request.PaymentId), cancellationToken);
        var bill = await _mediator.Send(new Payment.FetchBill.Request(request.PaymentId), cancellationToken);
        var paymentAmount = Math.Min(payment.PaymentAmount, bill.Balance);
        
        if (paymentAmount != 0)
        {
            await _mediator.Send(new MasterBill.Deposit.Request(bill.Id, paymentAmount), cancellationToken);
        }
        
        await _mediator.Send(new Payment.Update.Request(payment.Id, new UpdateRequest
        {
            PaymentStatus = bill.Balance < payment.PaymentAmount
                ? PaymentStatus.Overdue
                : PaymentStatus.Paid,
            Arrears = payment.PaymentAmount - paymentAmount
        }), cancellationToken);
        
    }
}