using MediatR;

namespace Credit.Lib.Feature.Credit.Create;

public class Notification : INotification
{
    public Guid Id { get; init; }
    public long AccountsPayable { get; init; }
    public DateOnly CompletionDate { get; init; }
}