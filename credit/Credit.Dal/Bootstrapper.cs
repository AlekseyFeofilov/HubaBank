using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;

namespace Credit.Dal;

public static class Bootstrapper
{
    public static IServiceCollection AddCreditContext(this IServiceCollection services, IConfiguration configuration)
    {
        var connectionString = configuration.GetConnectionString("Postgres");
        services.AddDbContext<CreditContext>(options => options.UseNpgsql(connectionString));
        return services;
    }
}