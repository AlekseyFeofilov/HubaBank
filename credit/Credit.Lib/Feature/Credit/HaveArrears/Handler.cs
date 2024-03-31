using MediatR;

namespace Credit.Lib.Feature.Credit.HaveArrears;

public class Handler : IRequestHandler<Request, bool>
{
    private readonly IMediator _mediator;

    public Handler(IMediator mediator)
    {
        _mediator = mediator;
    }

    public async Task<bool> Handle(Request request, CancellationToken cancellationToken)
    {
        var credit = await _mediator.Send(new FetchById.Request(request.CreditId), cancellationToken);
        return credit.Arrears > 0 || credit.Fine > 0 || credit.ArrearsInterest > 0;
    }
}