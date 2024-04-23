namespace Credit.Data.Requests.Core.Transfer;

public class Request
{
    public required Guid SourceBillId { get; init; }

    public required Guid TargetBillId { get; init; }

    public required long Amount { get; init; }
}