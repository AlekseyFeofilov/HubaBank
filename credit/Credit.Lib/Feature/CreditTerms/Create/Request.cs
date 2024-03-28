using Credit.Data.Requests.CreditTerms;

namespace Credit.Lib.Feature.CreditTerms.Create;

public class Request : Base.Add.Request<Dal.Models.CreditTerms, CreateRequest>
{
    public CreateRequest CreateRequest { get; }
    
    public Request(CreateRequest entity) : base(entity)
    {
        CreateRequest = entity;
    }
}