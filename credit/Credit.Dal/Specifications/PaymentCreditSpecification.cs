using Credit.Dal.Models;
using EntityFrameworkCore.CommonTools;

namespace Credit.Dal.Specifications;

public class PaymentCreditSpecification : Specification<Payment>
{
    public PaymentCreditSpecification(Guid creditId) : base(x => Equals(x.Credit.Id, creditId))
    {
    }
}