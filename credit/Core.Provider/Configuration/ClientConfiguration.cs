using Microsoft.Extensions.Logging;

namespace Core.Provider.Configuration
{
    public class ClientConfiguration : IClientConfiguration
    {
        private readonly ILoggerFactory _loggerFactory;

        public ClientConfiguration(ILoggerFactory loggerFactory)
        {
            _loggerFactory = loggerFactory;
        }

        public ILogger CreateLogger(Type type) => _loggerFactory.CreateLogger(type);
        public virtual bool ShouldRetryOnFail(string endpoint) => false;
    }
}