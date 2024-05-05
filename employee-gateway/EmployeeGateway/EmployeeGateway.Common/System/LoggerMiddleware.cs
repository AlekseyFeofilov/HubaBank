using System.Text;
using System.Text.Json;
using EmployeeGateway.BL;
using EmployeeGateway.Common.DTO.Logger;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Http.Extensions;
using Microsoft.Extensions.Logging;
using Microsoft.Extensions.Options;

namespace EmployeeGateway.Common.System;

public class LoggerMiddleware
{
    private readonly RequestDelegate _next;
    private readonly ILogger<LoggerMiddleware> _logger;
    private readonly UrlsMicroserviceOptions _configUrls;
    private readonly IHttpClientFactory _httpClientFactory;

    public LoggerMiddleware(
        RequestDelegate next,
        ILogger<LoggerMiddleware> logger,
        IOptions<UrlsMicroserviceOptions> options,
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
            }
            finally
            {
                context.Response.Body = originalBody;
            }

            var json = JsonSerializer.Serialize(logDto, UtilsService.jsonOptions);
            var content = new StringContent(json, Encoding.UTF8, "application/json");

            var response = await _httpClientFactory.CreateClient()
                .PostAsync(_configUrls.LoggerUrl.Remove(_configUrls.LoggerUrl.Length - 1), content);
        }
    }
}