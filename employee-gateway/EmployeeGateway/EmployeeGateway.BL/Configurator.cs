using EmployeeGateway.BL.Services;
using EmployeeGateway.Common.Enum;
using EmployeeGateway.Common.ServicesInterface;
using EmployeeGateway.DAL;
using Microsoft.AspNetCore.Builder;
using Microsoft.EntityFrameworkCore;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;

namespace EmployeeGateway.BL;

public static class Configurator
{
    public static void ConfigureAppDb(this WebApplicationBuilder builder)
    {
        var connection = builder.Configuration.GetConnectionString("DefaultConnection");
        builder.Services.AddDbContext<AppDbContext>(options =>
            options.UseNpgsql(connection)
        );
    }
    
    public static void ConfigureAppServices(this WebApplicationBuilder builder)
    {
        builder.Services.AddScoped<IThemeService, ThemeService>();
        builder.Services.AddSingleton<IFirebaseNotificationService, FirebaseNotificationService>();
        builder.Services.AddSingleton<IWebSocketUserDb, WebSocketUserDb>();
        builder.Services.AddHostedService<TransactionsListener>();
        builder.Services.AddScoped<IUserService, UserService>();
        builder.Services.AddScoped<ICircuitBreakerService, CircuitBreakerService>();
    }
    
    public static void ConfigureMicroserviceUrls(this WebApplicationBuilder builder)
    {
        builder.Services.Configure<UrlsMicroserviceOptions>(builder.Configuration.GetSection("MicroservicesUrls"));
    }
    
    public static async Task SeedCircuitBreaker(IServiceProvider serviceProvider)
    {
        using (var scope = serviceProvider.CreateScope())
        {
            var service = scope.ServiceProvider.GetRequiredService<ICircuitBreakerService>();

            await service.CreateCircuitBreaker(MicroserviceName.User);
            await service.CreateCircuitBreaker(MicroserviceName.Core);
            await service.CreateCircuitBreaker(MicroserviceName.Credit);
        }
    }
    
    public static void ConfigureMigrate(IServiceProvider serviceProvider)
    {
        using (var scope = serviceProvider.CreateScope())
        {
            var dbContext = scope.ServiceProvider.GetRequiredService<AppDbContext>();

            if (dbContext.Database.GetPendingMigrations().Any())
            {
                dbContext.Database.Migrate();
            }
        }
    }
}