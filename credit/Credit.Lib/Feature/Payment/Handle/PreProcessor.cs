using MediatR;
using MediatR.Pipeline;
using Microsoft.Extensions.Logging;

namespace Credit.Lib.Feature.Payment.Handle;

public class PreProcessor : IRequestPreProcessor<Request>
{
    private readonly ILogger<PreProcessor> _logger;
    private readonly IMediator _mediator;

    public PreProcessor(ILogger<PreProcessor> logger, IMediator mediator)
    {
        _logger = logger;
        _mediator = mediator;
    }

    public async Task Process(Request request, CancellationToken cancellationToken)
    {
        _logger.LogWarning("Trying to handle payment with id {paymentId}", request.PaymentId);
        var payment = await _mediator.Send(new Fetch.ById.Request(request.PaymentId), cancellationToken);
        var haveArrears = await _mediator.Send(new Credit.HaveArrears.Request(payment.CreditId), cancellationToken);
        
        if (haveArrears)
        {
            await _mediator.Send(new Credit.Arrears.Handle.Request(payment.CreditId)
            {
                RequestId = request.RequestId
            }, cancellationToken);
        }
        
        await _mediator.Send(new Credit.Interest.Handle.Request(payment.CreditId)
        {
            RequestId = request.RequestId
        }, cancellationToken);
    }
}