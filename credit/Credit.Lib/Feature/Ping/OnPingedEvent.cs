using MediatR;

namespace Credit.Lib.Feature.Ping;

public class OnPingedEvent : INotificationHandler<Notification>
{
    public Task Handle(Notification notification, CancellationToken cancellationToken)
    {
        Console.WriteLine("Notification handled");
        return Task.CompletedTask;
    }
}