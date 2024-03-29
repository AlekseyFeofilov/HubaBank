namespace Credit.Lib.Feature.Payment.Create;

public class Request : Base.Add.Request<Dal.Models.Payment, Data.Requests.Payment.CreateRequest>
{
    public Request(Data.Requests.Payment.CreateRequest entity) : base(entity)
    {
    }

    public Request(IReadOnlyCollection<Data.Requests.Payment.CreateRequest> entities) : base(entities)
    {
    }
}