using Core.Provider;
using LogService.Provider;
using Microsoft.Extensions.DependencyInjection;
using Utils.ClientGenerator.Configuration;

namespace Log.Service.Provider;

public static class Bootstrapper
{
    public static IServiceCollection AddLogServiceProvider(this IServiceCollection services)
    {
        services.AddTransient<ClientConfiguration>()
            .AddHttpClient("LogService", client =>
            {
                client.BaseAddress = new Uri("http://194.147.90.192:9006");;
            })
            .AddTypedClient<ILogServiceProviderV1, LogServiceProviderV1>();

        return services;
    }
}