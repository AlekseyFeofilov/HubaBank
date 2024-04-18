using Credit.Primitives;

namespace Credit.Dal.Models;

public class Payment : IIdentity<Guid>
{
    public Guid Id { get; set; }
    public Guid CreditId { get; set; }
    public Credit Credit { get; set; }
    public PaymentStatus PaymentStatus { get; set; }
    public DateOnly PaymentDay { get; set; }
    public long PaymentAmount { get; set; }
    public long Arrears { get; set; }
}