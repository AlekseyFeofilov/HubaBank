using BFF_client.Api.Controllers;
using BFF_client.Api.model;
using BFF_client.Api.Models.Logger;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Http.Extensions;
using Microsoft.Extensions.Options;
using Microsoft.Win32;
using System.Net.Http;
using System.Text;
using System.Text.Json;

namespace BFF_client.Api.Services
{
    public class LoggerMiddleware
    {
        private readonly RequestDelegate _next;
        private readonly ILogger<LoggerMiddleware> _logger;
        private readonly ConfigUrls _configUrls;
        private readonly IHttpClientFactory _httpClientFactory;

        public LoggerMiddleware(
            RequestDelegate next,
            ILogger<LoggerMiddleware> logger,
            IOptions<ConfigUrls> options,
            IHttpClientFactory clientFactory
            )
        {
            _next = next;
            _logger = logger;
            _configUrls = options.Value;
            _httpClientFactory = clientFactory;
        }

        public async Task InvokeAsync(HttpContext context)
        {
            context.Request.EnableBuffering();
            var originalBody = context.Response.Body;
            var requestReader = new StreamReader(context.Request.Body);

            var requestBody = await requestReader.ReadToEndAsync();
            
            context.Request.Body.Position = 0;
            using (var ms = new MemoryStream())
            {
                context.Response.Body = ms;
                await _next(context);
                ms.Position = 0;
                var responseReader = new StreamReader(ms);

                var responseBody = responseReader.ReadToEnd();

                ms.Position = 0;
                context.Request.Headers.TryGetValue("requestId", out var requestId);
                var logDto = new PublishLogDto
                {
                    RequestId = requestId,
                    Request = new RequestDto
                    {
                        Url = context.Request.GetDisplayUrl(),
                        Method = context.Request.Method,
                        Headers = context.Response.Headers.ToDictionary(x => x.Key, a => string.Join(";", a.Value)),
                        Body = requestBody
                    },
                    Response = new ResponseDto
                    {
                        Status = context.Response.StatusCode,
                        Headers = context.Response.Headers.ToDictionary(x => x.Key, a => string.Join(";", a.Value)),
                        Body = responseBody
                    }
                };
                try
                {
                    await ms.CopyToAsync(originalBody);
                } finally
                {
                    context.Response.Body = originalBody;
                }
                
                var json = JsonSerializer.Serialize(logDto, ControllersUtils.jsonOptions);
                var content = new StringContent(json, Encoding.UTF8, "application/json");

                var response = await _httpClientFactory.CreateClient().PostAsync(_configUrls.logger.Remove(_configUrls.logger.Length - 1), content);
            }
        }
    }
}
