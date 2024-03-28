using System.Reflection;
using AutoMapper;
using Credit.Lib.Extensions;
using Credit.Lib.Mapping;
using Hangfire;
using Hangfire.PostgreSql;
using MediatR;
using MediatR.Pipeline;
using Microsoft.Extensions.Configuration;
using Microsoft.Extensions.DependencyInjection;
using Microsoft.Extensions.DependencyInjection.Extensions;

namespace Credit.Lib;

public static class Bootstrapper
{
    public static IServiceCollection AddCredit(this IServiceCollection services,
        IConfiguration configuration)
    {
        services.AddTransient(typeof(IPipelineBehavior<,>), typeof(RequestPreProcessorBehavior<,>));
        services.AddTransient(typeof(IPipelineBehavior<,>), typeof(RequestPostProcessorBehavior<,>));
        services.AddMediatR(cfg =>
        {
            cfg.AutoRegisterRequestProcessors = true;
            cfg.RegisterServicesFromAssembly(typeof(Bootstrapper).Assembly);
            
        })
        .AddGenericMediator(typeof(Bootstrapper).Assembly)
        .AddAutoMapper(x => x
            .AddProfiles(new Profile[]
            {
                new CreditMappingProfile(),
                new CreditTermsMappingProfile(),
            }));

        // ServiceRegistrar.AddMediatRClasses(services, mediatorServiceConfiguration);
        // services.AddMediatR(cfg =>
        //     {
        //         cfg.RegisterServicesFromAssembly(typeof(Bootstrapper).Assembly);
        //         cfg.AddOpenBehavior(typeof(RequestPreProcessorBehavior<,>));
        //     });
        //     .AddGenericMediator(typeof(Bootstrapper).Assembly)
        //     .AddAutoMapper(x => x.AddProfile(new CreditMappingProfile()))
        //     // .AddHangfire(configuration)
        //     ;

        return services;
    }

    private static IServiceCollection AddHangfire(this IServiceCollection services, IConfiguration configuration)
    {
        var connectionString = configuration.GetConnectionString("Postgres");

        services.AddHangfire(config =>
        {
            config.UsePostgreSqlStorage(c =>
                c.UseNpgsqlConnection(connectionString));
            
            config.UseSimpleAssemblyNameTypeSerializer();
            config.SetDataCompatibilityLevel(CompatibilityLevel.Version_180);
        });

        return services;
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
        else
        {
            yield break;
        }

        var serviceType = typeof(IRequestHandler<,>).MakeGenericType(type, type.GetMediatorResponseType());
        yield return new ServiceDescriptor(serviceType, implementation, ServiceLifetime.Transient);
    }
}