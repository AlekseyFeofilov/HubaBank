namespace EmployeeGateway.Common.Exceptions;

public class MaxCountException: Exception
{
    public MaxCountException() {}
    public MaxCountException(string message): base(message) {}
    public MaxCountException(string message, Exception innerException) : base(message, innerException) {}
}