using Microsoft.Extensions.Logging;

namespace Utils.ClientGenerator.Configuration
{
    public interface IClientConfiguration
    {
        ILogger CreateLogger(Type type);
        bool ShouldRetryOnFail(string endpoint);
    }
}