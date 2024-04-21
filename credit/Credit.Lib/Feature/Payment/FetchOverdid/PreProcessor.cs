using MediatR;
using MediatR.Pipeline;

namespace Credit.Lib.Feature.Payment.FetchOverdid;

public class PreProcessor : IRequestPreProcessor<Request>
{
    private readonly IMediator _mediator;

    public PreProcessor(IMediator mediator)
    {
        _mediator = mediator;
    }

    public async Task Process(Request request, CancellationToken cancellationToken)
    {
        if (!request.CreditId.HasValue) return;
        await _mediator.Send(new Credit.Fetch.ById.Request(request.CreditId.Value), cancellationToken);
    }
}