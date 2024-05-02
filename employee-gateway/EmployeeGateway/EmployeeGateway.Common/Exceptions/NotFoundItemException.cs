namespace EmployeeGateway.Common.Exceptions;

public class NotFoundItemException: Exception
{
    public NotFoundItemException() {}
    public NotFoundItemException(string message): base(message) {}
    public NotFoundItemException(string message, Exception innerException) : base(message, innerException) {}
}