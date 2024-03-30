using Credit.Dal.Models;
using EntityFrameworkCore.CommonTools;

namespace Credit.Dal.Specifications;

public class TomorrowPaymentSpecification : Specification<Payment>
{
    public TomorrowPaymentSpecification() : base(x => x.PaymentDay == DateOnly.FromDateTime(DateTime.Now.AddDays(1)))
    {
    }
}