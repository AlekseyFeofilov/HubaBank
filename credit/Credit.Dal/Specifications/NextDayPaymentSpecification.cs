using Credit.Dal.Models;
using EntityFrameworkCore.CommonTools;

namespace Credit.Dal.Specifications;

public class PaymentDayPaymentSpecification : Specification<Payment>
{
    public PaymentDayPaymentSpecification(DateTime paymentDay) : base(x => x.PaymentDay == DateOnly.FromDateTime(paymentDay))
    {
    }
}