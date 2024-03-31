using Core.Provider.v2;
using MediatR;

namespace Credit.Lib.Feature.Bill.Fetch;

public class Handler : IRequestHandler<Request, BillDtoV2>
{
    private readonly ICoreProviderV2 _coreProviderV2;

    public Handler(ICoreProviderV2 coreProviderV2)
    {
        _coreProviderV2 = coreProviderV2;
    }

    public Task<BillDtoV2> Handle(Request request, CancellationToken cancellationToken)
    {
        return _coreProviderV2.GetBillV2Async(request.BillId, cancellationToken);
    }
}