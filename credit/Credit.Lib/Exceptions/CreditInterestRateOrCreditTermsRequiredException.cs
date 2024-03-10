using System.Net;

namespace Credit.Lib.Exceptions;

public class CreditInterestRateOrCreditTermsRequiredException : BusinessException
{
    public CreditInterestRateOrCreditTermsRequiredException() : base("Credit interest or credit terms must be specified", HttpStatusCode.BadRequest)
    {
    }
}