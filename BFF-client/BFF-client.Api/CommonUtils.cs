using RabbitMQ.Client;

namespace BFF_client.Api
{
    public static class CommonUtils
    {
        public static IConnection CreateConnection(IConfiguration configuration)
        {
            var factory = new ConnectionFactory
            {
                HostName = configuration["RABBITMQ_HOST"],
                Port = configuration.GetValue<int>("RABBITMQ_PORT"),
                UserName = configuration["RABBITMQ_USER"],
                Password = configuration["RABBITMQ_PASSWORD"],
                VirtualHost = configuration["RABBITMQ_VHOST"]
            };
            return factory.CreateConnection();
        }
    }
}
