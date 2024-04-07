using Credit.Lib.Feature.Credit.Create;
using Credit.Lib.Jobs;
using MediatR;

namespace Credit.Lib.Feature.Credit.Activate;

public class OnCreditCreateEvent : INotificationHandler<Notification>
{
    private readonly IJobClient _jobClient;

    public OnCreditCreateEvent(IJobClient jobClient)
    {
        _jobClient = jobClient;
    }

    public Task Handle(Notification notification, CancellationToken cancellationToken)
    {
        _jobClient.EnqueueCreditActivation(notification.CreditId);
        return Task.CompletedTask;
    }
}