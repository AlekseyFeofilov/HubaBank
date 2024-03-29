using Credit.Lib.Feature.Credit.Create;
using Credit.Lib.Strategies.CalculatePaymentAmount;
using MediatR;

namespace Credit.Lib.Feature.Credit.Activate;

public class OnCreditCreateEvent : INotificationHandler<Notification>
{
    private readonly IMediator _mediator;

    public OnCreditCreateEvent(IMediator mediator)
    {
        _mediator = mediator;
    }

    public Task Handle(Notification notification, CancellationToken cancellationToken)
    {
        var request = new Request
        {
            Id = notification.Id,
            CalculatePaymentAmountStrategy =
                new DefaultCalculatePaymentAmountStrategy(notification.AccountsPayable, notification.CompletionDate)
        };

        return _mediator.Send(request, cancellationToken);
    }
}