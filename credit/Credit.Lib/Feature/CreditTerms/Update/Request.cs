using Credit.Dal.Specifications;
using Credit.Data.Requests.CreditTerms;
using Credit.Data.Responses;

namespace Credit.Lib.Feature.CreditTerms.Update;

public class Request : Base.Update.Request<Dal.Models.CreditTerms, CreditTermsResponse>
{
    public Guid Id { get; }
    public UpdateRequest UpdateRequest { get; }
    public Request(Guid id, UpdateRequest request) : base(new IdentitySpecification<Dal.Models.CreditTerms, Guid>(id) &&
                                                          new ActiveOnlySpecification<Dal.Models.CreditTerms>())
    {
        Id = id;
        UpdateRequest = request;
        
        Expression = creditTerms =>
        {
            creditTerms.InterestRate = request.InterestRate;
        };
    }
}