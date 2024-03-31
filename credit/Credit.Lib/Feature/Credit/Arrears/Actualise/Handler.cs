using Credit.Dal.Specifications;
using MediatR;
using Utils.DateTime;

namespace Credit.Lib.Feature.Credit.Arrears.Actualise;

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
        var lastArrearsUpdated = credit.LastArrearsUpdate;
        var overdidPayments = await _mediator.Send(new Payment.Fetch.All.Request(new OverdidPaymentSpecification()),
            cancellationToken);
        
        foreach (var payment in overdidPayments)
        {
            var lastArrearsUpdatedForPayment = payment.PaymentDay < lastArrearsUpdated
                ? payment.PaymentDay.GetDifferenceInDays(lastArrearsUpdated)
                : payment.PaymentDay.GetDifferenceInDays(DateTime.Today);
            
            credit.Fine += payment.Arrears * credit.ArrearsInterest * lastArrearsUpdatedForPayment;
        }
    }
}