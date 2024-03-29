using Credit.Primitives;

namespace Credit.Data.Requests.Payment;

public class CreateRequest
{
    public Guid Id { get; set; }
    public PaymentStatus PaymentStatus { get; set; }
    public DateOnly PaymentDay { get; set; }
    public long PaymentAmount { get; set; }
    public long Arrears { get; set; }
}