using MediatR;
using MediatR.Pipeline;
using Microsoft.Extensions.Logging;

namespace Credit.Lib.Feature.Credit.Activate;

public class PreProcessor : IRequestPreProcessor<Request>
{
    private readonly IMediator _mediator;
    private readonly ILogger<PreProcessor> _logger;

    public PreProcessor(IMediator mediator, ILogger<PreProcessor> logger)
    {
        _mediator = mediator;
        _logger = logger;
    }

    public async Task Process(Request request, CancellationToken cancellationToken)
    {
        var credit = await _mediator.Send(new Credit.Fetch.ById.Request(request.CreditId), cancellationToken);
        await _mediator.Send(new MasterBill.Withdraw.Request(credit.BillId, credit.Principal), cancellationToken);
        _logger.LogWarning("Deposit credit principal {principal} into bill with id {billId}", 
            credit.Principal, credit.BillId);
    }
}