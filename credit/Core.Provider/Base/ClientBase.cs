using System.Text;
using Core.Provider.Configuration;
using Microsoft.Extensions.Logging;

namespace Core.Provider.Base
{
    public class ClientBase : IClientBase
    {
        private readonly IClientConfiguration _configuration;
        private readonly ILogger _logger;

        protected ClientBase(IClientConfiguration configuration)
        {
            _configuration = configuration;
            _logger = configuration.CreateLogger(GetType());
        }

        protected virtual Task PrepareRequestAsync(HttpClient client, HttpRequestMessage request, StringBuilder urlBuilder)
        {
            // удаляем leading slash из пути запроса, чтобы url формировался правильно, когда BaseAddress содержит сегменты пути
            if (urlBuilder.ToString(0, 1) == "/")
            {
                urlBuilder.Remove(0, 1);
            }

            return Task.CompletedTask;
        }

        protected virtual async Task ProcessResponseAsync(HttpClient client, HttpResponseMessage response, CancellationToken cancellationToken)
        {
            var request = response.RequestMessage;
            if (response.IsSuccessStatusCode)
            {
                _logger.LogInformation(
                    "Успешное выполнение запроса. Endpoint: {endpoint} Type: {logType} Status: {status} StatusCode: {statusCode} MediaType: {mediaType}",
                    request?.RequestUri, request?.Method, response.StatusCode, (int) response.StatusCode, request?.Content?.Headers.ContentType);

                await LogRequestBodyIfDebugEnabled();
            }
            else
            {
                _logger.LogWarning(
                    "Ошибка выполнения запроса. Endpoint: {endpoint} Type: {logType} Status: {status} StatusCode: {statusCode} MediaType: {mediaType}",
                    request?.RequestUri, request?.Method, response.StatusCode, (int) response.StatusCode, request?.Content?.Headers.ContentType);

                await LogRequestBodyIfDebugEnabled();
            }

            async Task LogRequestBodyIfDebugEnabled()
            {
                if (_logger.IsEnabled(LogLevel.Debug))
                {
                    var requestContent = request?.Content != null ? await request.Content.ReadAsStringAsync() : null;
                    _logger.LogDebug("Тело запроса. Endpoint: {endpoint} Type: {logType} Data: {request} MediaType: {mediaType}",
                        request?.RequestUri, request?.Method, requestContent, request?.Content?.Headers.ContentType);
                }
            }
        }
    }
}