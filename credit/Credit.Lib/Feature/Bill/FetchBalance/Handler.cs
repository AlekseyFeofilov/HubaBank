using MediatR;

namespace Credit.Lib.Feature.Bill.FetchBalance;

public class Handler : IRequestHandler<Request, long>
{
    private readonly IMediator _mediator;

    public Handler(IMediator mediator)
    {
        _mediator = mediator;
    }

    public async Task<long> Handle(Request request, CancellationToken cancellationToken)
    {
        var bill = await _mediator.Send(new Bill.Fetch.Request(request.BillId), cancellationToken);
        return bill.Balance;
    }
}