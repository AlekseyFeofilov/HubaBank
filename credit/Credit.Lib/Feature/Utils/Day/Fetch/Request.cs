using Credit.Dal.Specifications;

namespace Credit.Lib.Feature.Utils.Day.Fetch;

public class Request : Base.Fetch.Request<Dal.Models.Day, Data.Responses.DayResponse>
{
    //todo
    private static readonly Guid Id = Guid.Parse("3fa85f64-5717-4562-b3fc-2c963f66afa6");
    
    public Request() : base(new IdentitySpecification<Dal.Models.Day, Guid>(Id))
    {
    }
}