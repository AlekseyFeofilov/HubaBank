using BFF_client.Api.Controllers;
using BFF_client.Api.model;
using BFF_client.Api.model.user;
using BFF_client.Api.Services;
using Microsoft.Extensions.Options;
using static Google.Apis.Requests.BatchRequest;

namespace BFF_client.Api.Patterns
{
    public class ServiceChecker : BackgroundService
    {
        private readonly ICircuitBreakerService _circuitBreakerService;
        private readonly ILogger<ServiceChecker> _logger;
        private readonly ConfigUrls _configUrls;
        private readonly IHttpClientFactory _httpClientFactory;

        public ServiceChecker(
            ICircuitBreakerService circuitBreakerService, 
            ILogger<ServiceChecker> logger, 
            IOptions<ConfigUrls> options,
            IHttpClientFactory clientFactory
            )
        {
            _circuitBreakerService = circuitBreakerService;
            _logger = logger;
            _configUrls = options.Value;
            _httpClientFactory = clientFactory;
        }

        protected override async Task ExecuteAsync(CancellationToken stoppingToken)
        {
            stoppingToken.ThrowIfCancellationRequested();

            var usersClient = _httpClientFactory.CreateClient();
            var coreClient = _httpClientFactory.CreateClient();
            var creditClient = _httpClientFactory.CreateClient();

            var loggerUrl = _configUrls.logger + "errors/percent";


            while (!stoppingToken.IsCancellationRequested)
            {
                await Task.Delay(12000);

                var messageUsers = new HttpRequestMessage(HttpMethod.Get, loggerUrl + "?serviceName=user");
                var messageCore = new HttpRequestMessage(HttpMethod.Get, loggerUrl + "?serviceName=core");
                var messageCredit = new HttpRequestMessage(HttpMethod.Get, loggerUrl + "?serviceName=credit");
                try
                {
                    var userResponse = await usersClient.SendAsync(messageUsers); ;
                    if (userResponse.IsSuccessStatusCode)
                    {
                        var responseString = await userResponse.Content.ReadAsStringAsync();
                        if (responseString != null)
                        {
                            _circuitBreakerService.NewUsersPercent(float.Parse(responseString));
                        }
                    }
                }
                catch (Exception ex)
                {
                    _logger.LogError("Ошибка при запросе userService" + ex.Message);
                }

                try
                {
                    var coreResponse = await coreClient.SendAsync(messageCore);
                    if (coreResponse.IsSuccessStatusCode)
                    {
                        var responseString = await coreResponse.Content.ReadAsStringAsync();
                        if (responseString != null)
                        {
                            _circuitBreakerService.NewCorePercent(float.Parse(responseString));
                        }
                    }
                }
                catch (Exception ex)
                {
                    _logger.LogError("Ошибка при запросе coreService" + ex.Message);
                }

                try
                {
                    var creditResponse = await creditClient.SendAsync(messageCredit);
                    if (creditResponse.IsSuccessStatusCode)
                    {
                        var responseString = await creditResponse.Content.ReadAsStringAsync();
                        if (responseString != null)
                        {
                            _circuitBreakerService.NewUsersPercent(float.Parse(responseString));
                        }
                    }
                }
                catch (Exception ex)
                {
                    _logger.LogError("Ошибка при запросе creditService" + ex.Message);
                }
            }

            await Task.CompletedTask;
        }

        public override Task StopAsync(CancellationToken cancellationToken)
        {
            _logger.LogInformation("ServiceChecker is stopping.");

            return Task.CompletedTask;
        }
    }
}
