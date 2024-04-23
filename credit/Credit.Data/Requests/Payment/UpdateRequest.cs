using Credit.Primitives;

namespace Credit.Data.Requests.Payment;

public class UpdateRequest
{
    public PaymentStatus? PaymentStatus { get; set; }
    public DateOnly? PaymentDay { get; set; }
    public long? PaymentAmount { get; set; }
    public long? Interest { get; set; }
    public long? Arrears { get; set; }
    public long? ArrearsInterest { get; set; }
}