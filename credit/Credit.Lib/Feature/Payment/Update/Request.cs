using Credit.Dal.Specifications;

namespace Credit.Lib.Feature.Payment.Update;

public class Request : Base.Update.Request<Dal.Models.Payment, Data.Requests.Payment.UpdateRequest>
{
    protected Request(Guid id, Data.Requests.Payment.UpdateRequest request) 
        : base(new IdentitySpecification<Dal.Models.Payment, Guid>(id))
    {
        Expression = payment =>
        {
            if (request.PaymentStatus.HasValue)
            {
                payment.PaymentStatus = request.PaymentStatus.Value;
            }
            
            if (request.PaymentDay.HasValue)
            {
                payment.PaymentDay = request.PaymentDay.Value;
            }
            
            if (request.PaymentAmount.HasValue)
            {
                payment.PaymentAmount = request.PaymentAmount.Value;
            }
            
            if (request.Arrears.HasValue)
            {
                payment.Arrears = request.Arrears.Value;
            }
        };
    }
}