using System.Reflection;
using AutoMapper;
using Core.Provider;
using Credit.Dal;
using Credit.Lib.Extensions;
using Credit.Lib.Mapping;
using Credit.Lib.Models;
using Hangfire;
using Hangfire.PostgreSql;
using MediatR;
using MediatR.Pipeline;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.DependencyInjection.Extensions;
using RabbitMQ.Client;

namespace Credit.Lib;

public static class Bootstrapper
{
    public static IServiceCollection AddCredit(this IServiceCollection services,
        IConfiguration configuration)
    {
        services.AddCoreProvider()
            .AddCreditContext(configuration)
            .AddHangfire(configuration)
            .AddRabbit(configuration)
            .AddMediator(configuration)
            .AddAutoMapper(x => x
                .AddProfiles(new Profile[]
                {
                    new CreditMappingProfile(),
                    new CreditTermsMappingProfile(),
                    new PaymentMappingProfile(),
                    new IdempotentRequestMappingFilter(),
                    new SettingMappingProfile(),
                    new CircuitBreakerMappingProfile(),
                    new DayProfileMapping(),
                }));

        services.AddScoped<MasterBillSettings>(_ =>
            configuration.GetRequiredSection("MasterBillSettings").Get<MasterBillSettings>()!);
        services.AddScoped<AppSettings>(_ =>
            configuration.GetRequiredSection("AppSettings").Get<AppSettings>()!);

        return services;
    }

    private static IServiceCollection AddHangfire(this IServiceCollection services, IConfiguration configuration)
    {
        return services
            .AddHangfire(config =>
                config.UsePostgreSqlStorage(c =>
                    c.UseNpgsqlConnection(configuration.GetConnectionString("Hangfire"))))
            .AddHangfireServer();
    }

    private static IServiceCollection AddRabbit(this IServiceCollection services, IConfiguration configuration)
    {
        var rabbitSettings = configuration.GetRequiredSection("Rabbit").Get<RabbitSettings>()!;

        services.AddScoped<IModel>(_ =>
        {
            var factory = new ConnectionFactory
            {
                HostName = rabbitSettings.RabbitMqHost,
                Port = rabbitSettings.RabbitMqPort,
                UserName = rabbitSettings.RabbitMqUser,
                Password = rabbitSettings.RabbitMqPassword,
                VirtualHost = rabbitSettings.RabbitMqVhost,
                DispatchConsumersAsync = true
            };

            return factory.CreateConnection().CreateModel();
        });

        return services;
    }

    private static IServiceCollection AddMediator(this IServiceCollection services, IConfiguration configuration)
    {
        return services.AddMediatR(cfg =>
            {
                cfg.AutoRegisterRequestProcessors = true;
                cfg.RegisterServicesFromAssembly(typeof(Bootstrapper).Assembly);
            })
            .AddGenericMediator(typeof(Bootstrapper).Assembly)
            .AddTransient(typeof(IPipelineBehavior<,>), typeof(RequestPreProcessorBehavior<,>))
            .AddTransient(typeof(IPipelineBehavior<,>), typeof(RequestPostProcessorBehavior<,>));
    }

    private static IServiceCollection AddGenericMediator(this IServiceCollection services, Assembly assembly)
    {
        var descriptors = assembly.GetTypes()
            .Where(x => !x.IsAbstract)
            .SelectMany(GetDescriptors);

        services.TryAdd(descriptors);
        return services;
    }

    private static IEnumerable<ServiceDescriptor> GetDescriptors(Type type)
    {
        if (!type.HasGenericBase())
        {
            yield break;
        }

        var baseType = type.BaseType!;
        var (tDal, tData) = (baseType.GenericTypeArguments.First(), baseType.GenericTypeArguments.Last());
        Type implementation;

        if (baseType.IsGenericAssignableFrom(typeof(Feature.Base.Add.Request<,>)))
        {
            implementation = typeof(Feature.Base.Add.Handler<,>).MakeGenericType(tDal, tData);
        }
        else if (baseType.IsGenericAssignableFrom(typeof(Feature.Base.Fetch.Request<,>)))
        {
            implementation = typeof(Feature.Base.Fetch.Handler<,>).MakeGenericType(tDal, tData);
        }
        else if (baseType.IsGenericAssignableFrom(typeof(Feature.Base.FetchAll.Request<,>)))
        {
            implementation = typeof(Feature.Base.FetchAll.Handler<,>).MakeGenericType(tDal, tData);
        }
        else if (baseType.IsGenericAssignableFrom(typeof(Feature.Base.Update.Request<,>)))
        {
            implementation = typeof(Feature.Base.Update.Handler<,>).MakeGenericType(tDal, tData);
        }
        else if (baseType.IsGenericAssignableFrom(typeof(Feature.Base.Delete.Request<,>)))
        {
            implementation = typeof(Feature.Base.Delete.Handler<,>).MakeGenericType(tDal, tData);
        }
        else
        {
            yield break;
        }

        var serviceType = typeof(IRequestHandler<,>).MakeGenericType(type, type.GetMediatorResponseType());
        yield return new ServiceDescriptor(serviceType, implementation, ServiceLifetime.Transient);
    }
}