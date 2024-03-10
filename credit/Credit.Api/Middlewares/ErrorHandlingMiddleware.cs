using System.Net;
using Credit.Lib.Exceptions;
using JsonSerializer = System.Text.Json.JsonSerializer;

namespace Credit.Api.Middlewares
{
    public class ErrorHandlingMiddleware //todo не работает
    {
        private readonly RequestDelegate _next;
        private readonly ILogger _logger;

        public ErrorHandlingMiddleware(RequestDelegate next, ILogger<ErrorHandlingMiddleware> logger)
        {
            _next = next;
            _logger = logger ?? throw new ArgumentNullException(nameof(logger));
        }

        public async Task Invoke(HttpContext context)
        {
            try
            {
                await _next(context);
            }
            catch (Exception ex)
            {
                await HandleExceptionAsync(context, ex);
            }
        }

        private Task HandleExceptionAsync(HttpContext context, Exception exception)
        {
            var code = HttpStatusCode.InternalServerError;
            var result = JsonSerializer.Serialize(new {error = exception.ToString()});

            switch (exception)
            {
                case BusinessException e:
                    code = e.Code;
                    result = e.Message;
                    LogCustomException(e, e.Entity);
                    break;
                default:
                    _logger.LogError(exception, $"Произошла ошибка. Ошибка: {exception.Message}");
                    break;
            }
            
            context.Response.ContentType = "application/json";
            context.Response.StatusCode = (int) code;
            return context.Response.WriteAsync(result);
        }

        private void LogCustomException(Exception ex, object entity)
        {
            _logger.LogError(ex, $"Запрос {JsonSerializer.Serialize(entity)}");
        }
    }
}