namespace Credit.Lib.Feature.CircuitBreaker.Create;

public class Request : Base.Add.Request<Dal.Models.CircuitBreaker, Data.Requests.CircuitBreaker.CreateRequest>
{
    public Request(Data.Requests.CircuitBreaker.CreateRequest entity) : base(entity)
    {
    }
}