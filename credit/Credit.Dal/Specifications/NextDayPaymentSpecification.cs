using Credit.Dal.Models;
using EntityFrameworkCore.CommonTools;

namespace Credit.Dal.Specifications;

public class TodayPaymentSpecification : Specification<Payment>
{
    public TodayPaymentSpecification() : base(x => x.PaymentDay == DateOnly.FromDateTime(DateTime.Now.AddDays(0)))
    {
    }
}