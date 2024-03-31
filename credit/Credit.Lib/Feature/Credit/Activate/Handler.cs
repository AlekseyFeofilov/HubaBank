using Credit.Primitives;
using MediatR;
using Microsoft.Extensions.Logging;
using Utils.DateTime;

namespace Credit.Lib.Feature.Credit.Activate;

public class Handler : IRequestHandler<Request>
{
    private readonly IMediator _mediator;
    private readonly ILogger<Handler> _logger;

    public Handler(IMediator mediator, ILogger<Handler> logger)
    {
        _mediator = mediator;
        _logger = logger;
    }

    public async Task Handle(Request request, CancellationToken cancellationToken)
    {
        var credit = await _mediator.Send(new Fetch.ById.Request(request.CreditId), cancellationToken);
        var monthsToComplete = credit.CompletionDate.GetDifferenceInMonths(DateTime.Now);
        var payments = Array.Empty<Data.Requests.Payment.CreateRequest>().ToList();
        
        for (var monthsAfterToday = 1; monthsAfterToday <= monthsToComplete; monthsAfterToday++)
        {
            var payment = new Data.Requests.Payment.CreateRequest
            {
                PaymentStatus = PaymentStatus.Scheduled,
                PaymentDay = DateOnly.FromDateTime(DateTime.Now.AddMonths(monthsAfterToday)),
                PaymentAmount = request.CalculatePaymentAmountStrategy.Calculate(monthsAfterToday),
                CreditId = credit.Id,
                Arrears = 0,
            };
            
            payments.Add(payment);
        }
        
        _logger.LogWarning("Trying to create payments for credit with id {creditId}", credit.Id);
        await _mediator.Send(new Payment.Create.Request(payments), cancellationToken);
        _logger.LogWarning("Credit with id {creditId} was activated", credit.Id);
    }
}