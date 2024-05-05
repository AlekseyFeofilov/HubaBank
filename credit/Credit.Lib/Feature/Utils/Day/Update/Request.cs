using Credit.Dal.Specifications;
using Credit.Primitives;

namespace Credit.Lib.Feature.Utils.Day.Update;

public class Request : Base.Update.Request<Dal.Models.Day, Data.Responses.DayResponse>
{
    //todo
    private static readonly Guid Id = Guid.Parse("3fa85f64-5717-4562-b3fc-2c963f66afa6");
    
    public Request(int? addDays = null, int? addMonths = null)
        : base(new IdentitySpecification<Dal.Models.Day, Guid>(Id))
    {
        Expression = day =>
        {
            if (addDays.HasValue)
            {
                day.Now = day.Now.AddDays(addDays.Value);
            }
            
            if (addMonths.HasValue)
            {
                day.Now = day.Now.AddMonths(addMonths.Value);
            }
        };
    }
}