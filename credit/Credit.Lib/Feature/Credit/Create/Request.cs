using Credit.Data.Requests.Credit;

namespace Credit.Lib.Feature.Credit.Create;

public class Request : Base.Add.Request<Dal.Models.Credit, CreateRequest>
{
    public CreateRequest CreateRequest { get; }
    
    public Request(CreateRequest entity) : base(entity)
    {
        CreateRequest = entity;
    }
}