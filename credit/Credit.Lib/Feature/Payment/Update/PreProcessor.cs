using Credit.Data.Requests.Credit;
using MediatR;
using MediatR.Pipeline;

namespace Credit.Lib.Feature.Payment.Update;

public class PreProcessor : IRequestPreProcessor<Request>
{
    private readonly IMediator _mediator;

    public PreProcessor(IMediator mediator)
    {
        _mediator = mediator;
    }

    public async Task Process(Request request, CancellationToken cancellationToken)
    {
        var payment = await _mediator.Send(new Payment.Fetch.ById.Request(request.PaymentId), cancellationToken);
        await _mediator.Send(new Credit.Update.Request(payment.CreditId, new UpdateRequest
        {
            PayOffAccountsPayable = payment.PaymentAmount - request.UpdateRequest.PaymentAmount
        }), cancellationToken);
    }
}