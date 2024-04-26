using System.Net;

namespace Credit.Data.Requests.IdempotentRequest;

public class CreateRequest
{
    public required Guid Id { get; init; }
    public required string ConfirmationKeyHash { get; init; }
    public string Response { get; set; }
    public HttpStatusCode? HttpStatusCode { get; set; }
    public bool Completed { get; set; }
}