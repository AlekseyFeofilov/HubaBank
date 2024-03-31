using BFF_client.Api;
using BFF_client.Api.HubaWebSocket;
using BFF_client.Api.model;
using BFF_client.Api.Services;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.SignalR;
using Microsoft.Extensions.Options;
using Microsoft.OpenApi.Models;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.

builder.Services.AddControllers();
builder.Services.AddHttpClient();
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen(options =>
{
    options.AddSecurityDefinition("Bearer", new OpenApiSecurityScheme
    {
        In = ParameterLocation.Header,
        Description = "Please enter token",
        Type = SecuritySchemeType.Http,
        BearerFormat = "JWT",
        Scheme = "Bearer"
    });
    options.AddSecurityRequirement(new OpenApiSecurityRequirement
    {
        {
            new OpenApiSecurityScheme
            {
                Reference = new OpenApiReference
                {
                    Type = ReferenceType.SecurityScheme,
                    Id = "Bearer"
                }
            },
            Array.Empty<string>()
        }
    });
});

/*builder.Services.AddCors(options =>
{
    options.AddDefaultPolicy(builder =>
    {
        builder.AllowAnyMethod();
        builder.AllowAnyHeader();
        builder.AllowCredentials();
        builder.WithOrigins("*");
    });
});*/

//Database
builder.ConfigureAppDb();

//Services
builder.ConfigureAppServices();

builder.Services.Configure<ConfigUrls>(builder.Configuration.GetRequiredSection("MicroserversUrls"));

builder.Services.AddSingleton<IWebSocketUserDb, WebSocketUserDb>();
/*builder.Services.AddSingleton<IUserIdProvider, UserIdProvider>();
builder.Services.AddSignalR(options => { options.AddFilter<AuthHubFilter>(); });*/

builder.Services.AddHostedService<TransactionsListener>();

var app = builder.Build();

app.UseSwagger();
app.UseSwaggerUI();

app.UseHttpsRedirection();

app.UseWebSockets();
app.UseMiddleware<WebSocketMiddleware>();

app.UseAuthorization();

app.MapControllers();

Configurator.Migrate(app.Services);

app.Run();
