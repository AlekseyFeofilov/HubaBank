using Credit.Lib.Models;
using MediatR;

namespace Credit.Lib.Feature.MasterBill.Balance.Fetch;

public class Handler : IRequestHandler<Request, long>
{
    private readonly IMediator _mediator;
    private readonly MasterBillSettings _masterBillSettings;

    public Handler(IMediator mediator, MasterBillSettings masterBillSettings)
    {
        _mediator = mediator;
        _masterBillSettings = masterBillSettings;
    }

    public Task<long> Handle(Request request, CancellationToken cancellationToken)
    {
        return _mediator.Send(new Bill.FetchBalance.Request(_masterBillSettings.MasterBillId), cancellationToken);
    }
}