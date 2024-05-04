using MediatR;

namespace Credit.Lib.Feature.Credit.Interest.Handle;

public class Handler : IRequestHandler<Request>
{
    private readonly IMediator _mediator;

    public Handler(IMediator mediator)
    {
        _mediator = mediator;
    }

    public async Task Handle(Request request, CancellationToken cancellationToken)
    {
        var credit = await _mediator.Send(new Fetch.ById.Request(request.CreditId), cancellationToken);
        var billBalance = await _mediator.Send(new Bill.FetchBalance.Request(credit.BillId)
        {
            RequestId = request.RequestId
        }, cancellationToken);
        
        //todo нормально считать проценты
        var interest = credit.Principal * credit.InterestRate / (DateTime.IsLeapYear(DateTime.Today.Year) ? 366 : 365);
        var paymentAmount = Math.Min((long)interest, billBalance);
        
        if (paymentAmount == 0) return;
        
        await _mediator.Send(new MasterBill.Deposit.Request(credit.BillId, paymentAmount), cancellationToken);
        await _mediator.Send(new Credit.Update.Request(credit.Id, new Data.Requests.Credit.UpdateRequest
        {
            ArrearsInterest = Math.Max((long)interest - paymentAmount, 0)
        }), cancellationToken);
    }
}