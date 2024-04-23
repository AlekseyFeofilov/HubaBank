using MediatR;

namespace Credit.Lib.Feature.MasterBill.Deposit;

public class Handler : IRequestHandler<Request>
{
    private readonly IMediator _mediator;

    public Handler(IMediator mediator)
    {
        _mediator = mediator;
    }
    
    public Task Handle(Request request, CancellationToken cancellationToken)
    {
        return _mediator.Send(new MakeTransaction.Request(request.SourceBillId, request.AmountOfMoney), cancellationToken);
    }
}