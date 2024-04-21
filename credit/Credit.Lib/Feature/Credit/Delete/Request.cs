using Credit.Dal.Specifications;
using Credit.Data.Responses;

namespace Credit.Lib.Feature.Credit.Delete;

public class Request : Base.Update.Request<Dal.Models.Credit, CreditResponse>
{
    public Guid Id { get; }

    public Request(Guid id) : base(new CreditIdentitySpecification(id) &&
                                   new ActiveOnlySpecification<Dal.Models.Credit>())
    {
        Id = id;
        Expression = credit => credit.IsDeleted = true;
    }
}