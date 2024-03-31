using MediatR;

namespace Credit.Lib.Feature.Credit.Create;

public class Notification : INotification
{
    public Guid CreditId { get; init; }
    public long Principal { get; init; }
    public DateOnly CompletionDate { get; init; }
}