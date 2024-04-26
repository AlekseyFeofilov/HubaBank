using System.Net;
using Credit.Dal.Specifications;
using Credit.Data.Responses;

namespace Credit.Lib.Feature.IdempotentRequest.Complete;

public class Request : Base.Update.Request<Dal.Models.IdempotentRequest, IdempotenceRequestResponse>
{
    public Request(Guid id, string response, HttpStatusCode httpStatusCode)
        : base(new IdentitySpecification<Dal.Models.IdempotentRequest, Guid>(id))
    {
        Expression = idempotentRequest =>
        {
            idempotentRequest.Completed = true;
            idempotentRequest.Response = response;
            idempotentRequest.HttpStatusCode = httpStatusCode;
        };
    }
}