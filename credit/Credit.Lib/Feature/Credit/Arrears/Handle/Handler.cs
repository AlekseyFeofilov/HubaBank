using MediatR;
using Utils.DateTime;

namespace Credit.Lib.Feature.Credit.Arrears.Handle;

public class Handler : IRequestHandler<Request>
{
    private readonly IMediator _mediator;

    public Handler(IMediator mediator)
    {
        _mediator = mediator;
    }

    public async Task Handle(Request request, CancellationToken cancellationToken)
    {
        var credit = await _mediator.Send(new Credit.FetchById.Request(request.CreditId), cancellationToken);
        var bill = await _mediator.Send(new Bill.Fetch.Request(credit.BillId), cancellationToken);
        var balance = bill.Balance;
        
        if (credit.LastArrearsUpdate < DateTime.Today.ToDateOnly())
        {
            await _mediator.Send(new Credit.Arrears.Actualise.Request(credit.Id), cancellationToken);
        }
        
        if (balance > 0)
        {
            await PayOff(credit.ArrearsInterest);
        }

        if (balance > 0)
        {
            await PayOff(credit.Arrears);
        }
        
        if (balance > 0)
        {
            await PayOff(credit.Fine);
        }

        return;

        async Task PayOff(long wantedPayment)
        {
            var payment = Math.Min(wantedPayment, balance);
            await _mediator.Send(new MasterBill.Deposit.Request(bill.Id, payment), cancellationToken);
            balance -= payment;
        }
    }
}