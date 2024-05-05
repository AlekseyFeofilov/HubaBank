namespace Credit.Data.Requests.Day;

public class CreateRequest
{
    public required Guid Id { get; init; }
    public required DateTime Now { get; init; }
}