using System.Net;

namespace Credit.Lib.Exceptions;

public abstract class BusinessException : Exception
{
    public object Entity { get; set; }
    public HttpStatusCode Code { get; }

    public BusinessException(string message, HttpStatusCode code) : base(message)
    {
        Code = code;
    }
}