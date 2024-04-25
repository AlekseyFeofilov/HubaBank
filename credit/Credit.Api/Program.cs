using System.Reflection;
using Credit.Api;
using Credit.Api.Middlewares;
using Credit.Data;
using Credit.Lib;
using Credit.Lib.Jobs;
using Hangfire;
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
builder.Services.AddAsyncInitializer<JobsInitializer>();

builder.Services.AddControllers()
    .AddJsonOptions(options =>
    {
        options.JsonSerializerOptions.Converters.Add(new DateOnlyJsonConverter());
    });

builder.Services.AddAsyncInitializer<JobsInitializer>();
builder.Services.AddScoped<IJobAgent, JobAgent>();
builder.Services.AddScoped<IJobClient, JobClient>();

var app = builder.Build();

app.UseMiddleware<RandomFaultMiddleware>();
app.UseMiddleware<ErrorHandlingMiddleware>();
app.UseHangfireDashboard();

// Configure the HTTP request pipeline.
app.UseSwagger();
app.UseSwaggerUI();

app.UseHttpsRedirection();

app.UseAuthorization();

app.MapControllers();
app.MapHangfireDashboard();

await app.InitAndRunAsync();