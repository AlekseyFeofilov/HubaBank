using Credit.Dal.Specifications;
using Credit.Data.Responses;

namespace Credit.Lib.Feature.CreditTerms.Delete;

public class Request : Base.Update.Request<Dal.Models.CreditTerms, CreditTermsResponse>
{
    public Guid Id { get; }
    public Request(Guid id) : base(new IdentitySpecification<Dal.Models.CreditTerms, Guid>(id) &&
                                   new ActiveOnlySpecification<Dal.Models.CreditTerms>())
    {
        Id = id;
        Expression = creditTerms => creditTerms.IsDeleted = true;
    }
}