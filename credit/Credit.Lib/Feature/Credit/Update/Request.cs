using Credit.Dal.Specifications;
using Credit.Data.Requests.Credit;
using Credit.Data.Responses;

namespace Credit.Lib.Feature.Credit.Update;

public class Request : Base.Update.Request<Dal.Models.Credit, CreditResponse>
{
    public Guid Id { get; }
    public UpdateRequest UpdateRequest { get; }
    
    public Request(Guid id, UpdateRequest request) : base(new CreditIdentitySpecification(id) &&
                                                          new ActiveOnlySpecification<Dal.Models.Credit>())
    {
        Id = id;
        UpdateRequest = request;
        
        Expression = credit =>
        {
            if (request.CompletionDate.HasValue)
            {
                credit.CompletionDate = request.CompletionDate.Value;
            }
            
            if (request.InterestRate.HasValue)
            {
                credit.InterestRate = request.InterestRate.Value;
            }
            
            if (request.CreditTermsId.HasValue)
            {
                credit.CreditTermsId = request.CreditTermsId.Value;
            }
            
            if (request.PayOffAccountsPayable.HasValue)
            {
                //todo переделать по-очевиднее
                credit.AccountsPayable -= request.PayOffAccountsPayable.Value;
            }
            
            if (request.Arrears.HasValue)
            {
                credit.AccountsPayable += request.Arrears.Value - credit.Arrears;
                credit.Arrears = request.Arrears.Value;
            }
            
            if (request.ArrearsInterest.HasValue)
            {
                credit.AccountsPayable += request.ArrearsInterest.Value - credit.ArrearsInterest;
                credit.ArrearsInterest = request.ArrearsInterest.Value;
            }
            
            if (request.Fine.HasValue)
            {
                credit.AccountsPayable += request.Fine.Value - credit.Fine;
                credit.Fine = request.Fine.Value;
            }
        };
    }
}