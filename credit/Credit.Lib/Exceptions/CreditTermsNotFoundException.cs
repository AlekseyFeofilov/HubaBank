using System.Net;

namespace Credit.Lib.Exceptions;

public class CreditTermsNotFoundException : BusinessException
{
    public CreditTermsNotFoundException(Guid id) : base($"Credit terms with id {id} was not found", HttpStatusCode.NotFound)
    {
    }
}