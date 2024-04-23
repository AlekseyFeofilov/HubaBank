using Credit.Dal.Models;
using Credit.Primitives;
using EntityFrameworkCore.CommonTools;

namespace Credit.Dal.Specifications;

public class OverdidPaymentSpecification : Specification<Payment>
{
    public OverdidPaymentSpecification() : base(x => x.PaymentStatus == PaymentStatus.Overdue)
    {
        
    }
}