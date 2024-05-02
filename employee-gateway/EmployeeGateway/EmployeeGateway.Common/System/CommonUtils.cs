using Microsoft.Extensions.Configuration;
using RabbitMQ.Client;

namespace EmployeeGateway.Common.System;

public class CommonUtils
{
    public static IConnection CreateConnection(IConfiguration configuration)
    {
        var factory = new ConnectionFactory
        {
            HostName = configuration["RABBITMQ_HOST"],
            Port = configuration.GetValue<int>("RABBITMQ_PORT"),
            UserName = configuration["RABBITMQ_USER"],
            Password = configuration["RABBITMQ_PASSWORD"],
            VirtualHost = configuration["RABBITMQ_VHOST"],
            DispatchConsumersAsync = true
        };
        return factory.CreateConnection();
    }

}