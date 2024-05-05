using BFF_client.Api.Database;
using BFF_client.Api.HubaWebSocket;
using BFF_client.Api.Patterns;
using BFF_client.Api.Services;
using BFF_client.Api.Services.Bill;
using BFF_client.Api.Services.User;
using FirebaseAdmin;
using Google.Apis.Auth.OAuth2;
using Microsoft.EntityFrameworkCore;

namespace BFF_client.Api
{
    public static class Configurator
    {
        public static void ConfigureAppDb(this WebApplicationBuilder builder)
        {
            var connection = builder.Configuration.GetConnectionString("DefaultConnection");
            builder.Services.AddDbContext<AppDbContext>(options => options.UseNpgsql(connection));
        }

        public static void ConfigureAppServices(this WebApplicationBuilder builder)
        {
            builder.Services.AddScoped<IUserService, UserService>();
            builder.Services.AddScoped<IBillService, BillService>();

            builder.Services.AddSingleton<ICircuitBreakerService, CircuitBreakerService>();
            builder.Services.AddSingleton<IWebSocketUserDb, WebSocketUserDb>();

            builder.Services.AddHostedService<TransactionsListener>();
            builder.Services.AddHostedService<ServiceChecker>();
        }

        public static void InitializeFirebaseMessaging()
        {
            FirebaseApp.Create(new AppOptions
            {
                Credential = GoogleCredential.FromFile("hubabank-firebase.json")
            });
        }

        public static void Migrate(IServiceProvider serviceProvider)
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
}
