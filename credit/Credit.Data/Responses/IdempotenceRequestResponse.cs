using System.Net;

namespace Credit.Data.Responses;

public class IdempotenceRequestResponse
{
    public Guid Id { get; set; }
    public string ConfirmationKeyHash { get; set; }
    public string Response { get; set; }
    public HttpStatusCode? HttpStatusCode { get; set; }
    public bool Completed { get; set; }
}