using Credit.Dal.Specifications;
using Credit.Data.Responses;

namespace Credit.Lib.Feature.Payment.Fetch.ById;

public class Request : Base.Fetch.Request<Dal.Models.Payment, PaymentResponse>
{
    public Guid Id { get; }

    public Request(Guid id) : base(new IdentitySpecification<Dal.Models.Payment, Guid>(id))
    {
        Id = id;
    }
}