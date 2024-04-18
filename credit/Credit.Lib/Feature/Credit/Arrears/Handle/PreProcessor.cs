using MediatR;
using MediatR.Pipeline;
using Microsoft.Extensions.Logging;
using Utils.DateTime;

namespace Credit.Lib.Feature.Credit.Arrears.Handle;

public class PreProcessor : IRequestPreProcessor<Request>
{
    private readonly IMediator _mediator;
    private readonly ILogger<Handler> _logger;

    public PreProcessor(IMediator mediator, ILogger<Handler> logger)
    {
        _mediator = mediator;
        _logger = logger;
    }

    public async Task Process(Request request, CancellationToken cancellationToken)
    {
        var credit = await _mediator.Send(new Credit.Fetch.ById.Request(request.CreditId), cancellationToken);

        if (credit.LastArrearsUpdate < DateTime.Today.ToDateOnly())
        {
            _logger.LogWarning("Trying to actualise credit with id {creditId} arrears", credit.Id);
            await _mediator.Send(new Credit.Arrears.Actualise.Request(credit.Id), cancellationToken);
        }
    }
}