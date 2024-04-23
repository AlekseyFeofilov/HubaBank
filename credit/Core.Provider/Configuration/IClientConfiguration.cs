using Microsoft.Extensions.Logging;

namespace Core.Provider.Configuration
{
    public interface IClientConfiguration
    {
        ILogger CreateLogger(Type type);
        bool ShouldRetryOnFail(string endpoint);
    }
}