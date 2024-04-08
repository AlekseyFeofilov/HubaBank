using Credit.Dal.Specifications;
using Credit.Data.Responses;
using MediatR;

namespace Credit.Lib.Feature.Payment.FetchOverdid;

public class Handler : IRequestHandler<Request, IReadOnlyCollection<PaymentResponse>>
{
    private readonly IMediator _mediator;

    public Handler(IMediator mediator)
    {
        _mediator = mediator;
    }

    public Task<IReadOnlyCollection<PaymentResponse>> Handle(Request request, CancellationToken cancellationToken)
    {
        var specification = request.CreditId.HasValue 
            ? new PaymentCreditSpecification(request.CreditId.Value) && new OverdidPaymentSpecification()
            : new OverdidPaymentSpecification();

        return _mediator.Send(new Fetch.All.Request(specification), cancellationToken);
    }
}