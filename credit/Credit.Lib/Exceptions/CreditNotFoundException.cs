using System.Net;

namespace Credit.Lib.Exceptions;

public class CreditNotFoundException : BusinessException
{
    public CreditNotFoundException(Guid id) : base($"Credit with id {id} was not found", HttpStatusCode.NotFound)
    {
    }
}