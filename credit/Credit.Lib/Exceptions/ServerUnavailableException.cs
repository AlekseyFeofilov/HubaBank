using System.Net;

namespace Credit.Lib.Exceptions;

public class ServerUnavailableException : BusinessException
{
    public ServerUnavailableException(string serverName) 
        : base($"Server {serverName} is unavailable", HttpStatusCode.ServiceUnavailable)
    {
    }
}