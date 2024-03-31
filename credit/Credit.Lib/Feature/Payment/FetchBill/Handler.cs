using Core.Provider.v2;
using MediatR;

namespace Credit.Lib.Feature.Payment.FetchBill;

public class Handler : IRequestHandler<Request, BillDtoV2>
{
    private readonly IMediator _mediator;
    private readonly ICoreProviderV2 _coreProviderV2;

    public Handler(IMediator mediator, ICoreProviderV2 coreProviderV1)
    {
        _mediator = mediator;
        _coreProviderV2 = coreProviderV1;
    }

    public async Task<BillDtoV2> Handle(Request request, CancellationToken cancellationToken)
    {
        var payment = await _mediator.Send(new Fetch.ById.Request(request.PaymentId), cancellationToken);
        var credit = await _mediator.Send(new Credit.Fetch.ById.Request(payment.CreditId), cancellationToken);
        return await _coreProviderV2.GetBillV2Async(credit.BillId, cancellationToken);
    }
}