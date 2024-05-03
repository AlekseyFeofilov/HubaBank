using System.Reflection;
using Credit.Api;
using Credit.Api.Middlewares;
using Credit.Api.Middlewares.IdempotentHandlingMiddlewares;
using Credit.Dal;
using Credit.Data;
using Credit.Lib;
using Credit.Lib.Jobs;
using Hangfire;
using Log.Service.Provider;
using Microsoft.OpenApi.Any;
using Microsoft.OpenApi.Models;
using DateOnlyJsonConverter = Credit.Api.Converters.DateOnlyJsonConverter;

var builder = WebApplication.CreateBuilder(args);

// Add services to the container.

builder.Services.AddControllers();
// Learn more about configuring Swagger/OpenAPI at https://aka.ms/aspnetcore/swashbuckle
builder.Services.AddEndpointsApiExplorer();
builder.Services.AddSwaggerGen(options =>
{
    options.IncludeXmlComments(Path.Combine(AppContext.BaseDirectory,
        $"{Assembly.GetExecutingAssembly().GetName().Name}.xml"));
    options.IncludeCreditDataXmlComments();

    options.MapType<DateOnly>(() => new OpenApiSchema
    {
        Type = "string",
        Format = "date",
        Example = new OpenApiString("2022-01-01")
    });
    
    options.CustomSchemaIds( type => type.ToString() );
});

builder.Services.AddCredit(builder.Configuration);
builder.Services.AddLogServiceProvider();
builder.Services.AddAsyncInitializer<JobsInitializer>();

builder.Services.AddControllers()
    .AddJsonOptions(options =>
    {
        options.JsonSerializerOptions.Converters.Add(new DateOnlyJsonConverter());
    });

builder.Services.AddAsyncInitializer<JobsInitializer>();
builder.Services.AddScoped<IJobAgent, JobAgent>();
builder.Services.AddScoped<IJobClient, JobClient>();
builder.Services.AddScoped<BodyBasedIdempotentHandlingMiddleware>();
builder.Services.AddScoped<IdBasedIdempotentHandlingMiddleware>();
builder.Services.AddScoped<RandomFaultMiddleware>();
builder.Services.AddScoped<LoggerMiddleware>();
builder.Services.AddScoped<ErrorHandlingMiddleware>();

var app = builder.Build();

app.Services.AutoMigrateCreditContext();

app.UseMiddleware<ErrorHandlingMiddleware>();
app.UseMiddleware<RequestEnrichMiddleware>();
app.UseWhen(context => !context.Request.Path.StartsWithSegments("/swagger") 
                       && !context.Request.Path.StartsWithSegments("/hangfire"),
    appBuilder =>
{
    appBuilder.UseMiddleware<LoggerMiddleware>();
});
app.UseMiddleware<RandomFaultMiddleware>();
app.UseMiddleware<BodyBasedIdempotentHandlingMiddleware>();
app.UseMiddleware<IdBasedIdempotentHandlingMiddleware>();

app.UseHangfireDashboard();

// Configure the HTTP request pipeline.
app.UseSwagger();
app.UseSwaggerUI();

app.UseHttpsRedirection();

app.UseAuthorization();

app.MapControllers();
app.MapHangfireDashboard();

await app.InitAndRunAsync();