using Credit.Lib.Feature.Credit.Create;
using Credit.Lib.Strategies.CalculatePaymentAmount;
using MediatR;
using Microsoft.Extensions.Logging;

namespace Credit.Lib.Feature.Credit.Activate;

public class OnCreditCreateEvent : INotificationHandler<Notification>
{
    private readonly IMediator _mediator;
    private readonly ILogger<OnCreditCreateEvent> _logger;

    public OnCreditCreateEvent(IMediator mediator, ILogger<OnCreditCreateEvent> logger)
    {
        _mediator = mediator;
        _logger = logger;
    }

    public Task Handle(Notification notification, CancellationToken cancellationToken)
    {
        //todo make with hangfire retry
        var request = new Request
        {
            CreditId = notification.CreditId,
            CalculatePaymentAmountStrategy =
                new DefaultCalculatePaymentAmountStrategy(notification.Principal, notification.CompletionDate)
        };

        _logger.LogWarning("Trying to activate credit with id {creditId}", request.CreditId);
        return _mediator.Send(request, cancellationToken);
    }
}