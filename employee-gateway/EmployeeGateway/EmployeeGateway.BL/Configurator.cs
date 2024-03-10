using Microsoft.AspNetCore.Builder;
using Microsoft.Extensions.DependencyInjection;

namespace EmployeeGateway.BL;

public static class Configurator
{
    public static void ConfigureMicroserviceUrls(this WebApplicationBuilder builder)
    {
        builder.Services.Configure<UrlsMicroserviceOptions>(builder.Configuration.GetSection("MicroservicesUrls"));
    }
}