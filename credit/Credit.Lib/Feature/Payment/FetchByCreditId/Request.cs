using Credit.Dal.Specifications;
using Credit.Data;
using Credit.Data.Responses;

namespace Credit.Lib.Feature.Payment.FetchByCreditId;

public class Request : Base.FetchAll.Request<Dal.Models.Payment, PaymentResponse>
{
    public Request(Guid creditId, PageFilter? pageFilter) 
        : base(new PaymentCreditSpecification(creditId), pageFilter)
    {
    }
}