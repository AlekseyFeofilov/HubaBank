using Core.Provider.v1;
using Core.Provider.v2;
using Microsoft.Extensions.DependencyInjection;
using Utils.ClientGenerator.Configuration;

namespace Core.Provider;

public static class Bootstrapper
{
    public static IServiceCollection AddCoreProvider(this IServiceCollection services)
    {
        services.AddTransient<ClientConfiguration>()
            .AddHttpClient("Core", client =>
            {
                client.BaseAddress = new Uri("http://194.147.90.192:9004");
                client.DefaultRequestHeaders.Add("X-API-KEY", "55wSKcrRx4C0paIf4hLbNqhzygviC8G3igpg5kdZs5WqipmNDkeOZ149vGS1DJ9W");
            })
            .AddTypedClient<ICoreProviderV1, CoreProviderV1>()
            .AddTypedClient<ICoreProviderV2, CoreProviderV2>();

        return services;
    }
}