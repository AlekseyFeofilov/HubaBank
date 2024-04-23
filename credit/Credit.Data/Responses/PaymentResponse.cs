using Credit.Primitives;

namespace Credit.Data.Responses;

public class PaymentResponse
{
    public Guid Id { get; set; }
    public Guid CreditId { get; set; }
    public PaymentStatus PaymentStatus { get; set; }
    public DateOnly PaymentDay { get; set; }
    public long PaymentAmount { get; set; }
    public long Interest { get; set; }
    public long Arrears { get; set; }
    public long ArrearsInterest { get; set; }
}