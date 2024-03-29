using Credit.Primitives;
using MediatR;
using Utils.DateTime;

namespace Credit.Lib.Feature.Credit.Activate;

public class Handler : IRequestHandler<Request>
{
    private readonly IMediator _mediator;

    public Handler(IMediator mediator)
    {
        _mediator = mediator;
    }

    public async Task Handle(Request request, CancellationToken cancellationToken)
    {
        var credit = await _mediator.Send(new Credit.FetchById.Request(request.Id), cancellationToken);
        var monthsToComplete = credit.CompletionDate.GetDifferenceInMonths(DateTime.Now);
        var payments = Array.Empty<Data.Requests.Payment.CreateRequest>().ToList();
        
        for (var monthsAfterToday = 1; monthsAfterToday <= monthsToComplete; monthsAfterToday++)
        {
            var payment = new Data.Requests.Payment.CreateRequest
            {
                PaymentStatus = PaymentStatus.Scheduled,
                PaymentDay = DateOnly.FromDateTime(DateTime.Now.AddMonths(monthsAfterToday)),
                PaymentAmount = request.CalculatePaymentAmountStrategy.Calculate(monthsAfterToday),
                Arrears = 0,
            };
            
            payments.Add(payment);
        }

        await _mediator.Send(new Payment.Create.Request(payments), cancellationToken);
    }
}