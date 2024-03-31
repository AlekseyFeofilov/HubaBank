using Credit.Dal.Specifications;

namespace Credit.Lib.Feature.Payment.Update;

public class Request : Base.Update.Request<Dal.Models.Payment, Data.Requests.Payment.UpdateRequest>
{
    public Guid PaymentId { get; set; }
    public Data.Requests.Payment.UpdateRequest UpdateRequest { get; set; }
    
    public Request(Guid id, Data.Requests.Payment.UpdateRequest request) 
        : base(new IdentitySpecification<Dal.Models.Payment, Guid>(id))
    {
        PaymentId = id;
        UpdateRequest = request;
        
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