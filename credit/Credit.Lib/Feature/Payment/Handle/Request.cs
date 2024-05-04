using MediatR;

namespace Credit.Lib.Feature.Payment.Handle;

public class Request : IRequest
{
    public Guid PaymentId { get; set; }
    
    public Request(Guid paymentId)
    {
        PaymentId = paymentId;
    }
    
    public required Guid RequestId { get; init; }
}