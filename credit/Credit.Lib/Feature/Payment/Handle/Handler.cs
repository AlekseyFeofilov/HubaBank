using Core.Provider.v1;
using Credit.Data.Requests.Payment;
using Credit.Primitives;
using MediatR;

namespace Credit.Lib.Feature.Payment.Handle;

public class Handler : IRequestHandler<Request>
{
    private readonly IMediator _mediator;
    private readonly ICoreProviderV1 _coreProviderV1;

    public Handler(IMediator mediator, ICoreProviderV1 coreProviderV1)
    {
        _mediator = mediator;
        _coreProviderV1 = coreProviderV1;
    }

    public async Task Handle(Request request, CancellationToken cancellationToken)
    {
        var payment = await _mediator.Send(new Fetch.ById.Request(request.Id), cancellationToken);
        var haveArrears = await _mediator.Send(new Credit.HaveArrears.Request(request.Id), cancellationToken);
        
        if (haveArrears)
        {
            await _mediator.Send(new Credit.Arrears.Handle.Request(payment.CreditId), cancellationToken);
        }
        
        var bill = await _mediator.Send(new Payment.FetchBill.Request(request.Id), cancellationToken);
        var paymentAmount = Math.Min(payment.PaymentAmount, bill.Balance);
        
        await _mediator.Send(new MasterBill.Deposit.Request(bill.Id, paymentAmount), cancellationToken);
        await _mediator.Send(new Payment.Update.Request(payment.Id, new UpdateRequest
        {
            PaymentStatus = bill.Balance < payment.PaymentAmount
                ? PaymentStatus.Overdue
                : PaymentStatus.Paid,
            Arrears = payment.PaymentAmount - paymentAmount
        }), cancellationToken);

        Console.WriteLine($"Payment {request.Id} was handled");
    }
}