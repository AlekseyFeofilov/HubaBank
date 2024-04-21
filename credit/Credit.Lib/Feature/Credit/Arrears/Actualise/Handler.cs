using Credit.Dal.Specifications;
using Credit.Data.Requests.Credit;
using MediatR;
using Microsoft.Extensions.Logging;
using Utils.DateTime;

namespace Credit.Lib.Feature.Credit.Arrears.Actualise;

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
        var credit = await _mediator.Send(new Fetch.ById.Request(request.CreditId), cancellationToken);
        var lastArrearsUpdated = credit.LastArrearsUpdate;
        var overdidPayments = await _mediator.Send(new Payment.Fetch.All.Request(new OverdidPaymentSpecification()),
            cancellationToken);
        
        foreach (var payment in overdidPayments)
        {
            var lastArrearsUpdatedForPayment = payment.PaymentDay < lastArrearsUpdated
                ? payment.PaymentDay.GetDifferenceInDays(lastArrearsUpdated)
                : payment.PaymentDay.GetDifferenceInDays(DateTime.Today);
            
            var updateRequest = new UpdateRequest
            { 
                Fine = payment.Arrears * credit.ArrearsInterest * lastArrearsUpdatedForPayment
            };
            
            _logger.LogWarning("Recalculate fine for credit with id {creditId}. Old fine: {oldFine}, updatedFine: {updatedFine}", 
                credit.Id, credit.Fine, updateRequest.Fine);
            await _mediator.Send(new Credit.Update.Request(credit.Id, updateRequest), cancellationToken);
            
            
        }
    }
}