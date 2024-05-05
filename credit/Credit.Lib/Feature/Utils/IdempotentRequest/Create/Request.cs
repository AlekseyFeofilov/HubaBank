namespace Credit.Lib.Feature.Utils.IdempotentRequest.Create;

public class Request : Base.Add.Request<Dal.Models.IdempotentRequest, Data.Requests.IdempotentRequest.CreateRequest>
{
    public Request(Data.Requests.IdempotentRequest.CreateRequest entity) : base(entity)
    {
    }
}