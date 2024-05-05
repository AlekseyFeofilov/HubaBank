using System.Net;
using Credit.Lib.Exceptions;
using MediatR;
using JsonSerializer = System.Text.Json.JsonSerializer;

namespace Credit.Api.Middlewares
{
    public class ErrorHandlingMiddleware : IMiddleware
    {
        private readonly ILogger<ErrorHandlingMiddleware> _logger;
        private readonly IMediator _mediator;

        public ErrorHandlingMiddleware(ILogger<ErrorHandlingMiddleware> logger, IMediator mediator)
        {
            _logger = logger ?? throw new ArgumentNullException(nameof(logger));
            _mediator = mediator;
        }

        public async Task InvokeAsync(HttpContext context, RequestDelegate next)
        {
            try
            {
                await next(context);
            }
            catch (Exception ex)
            {
                await HandleExceptionAsync(context, ex);
            }
        }

        private async Task HandleExceptionAsync(HttpContext context, Exception exception)
        {
            var code = HttpStatusCode.InternalServerError;
            var result = JsonSerializer.Serialize(new {error = exception.ToString()});

            switch (exception)
            {
                case BusinessException e:
                    code = e.Code;
                    result = JsonSerializer.Serialize(e.Message);
                    LogCustomException(e, e.Entity);
                    break;
                default:
                    _logger.LogError(exception, "Произошла ошибка. Ошибка: {exception}", exception.Message);
                    break;
            }
            
            context.Response.ContentType = "application/json";
            context.Response.StatusCode = (int) code;
            
            _logger.LogError("Тест лог до WriteAsync");
            await context.Response.WriteAsync(result);
            _logger.LogError("Тест лог после WriteAsync");

            try
            {
                await _mediator.Send(new Lib.Feature.LogService.Log.Request
                { HttpContext = context,
                    ResponseBody = result
                });
            }
            catch (Exception e)
            {
                _logger.LogError(e, 
                    "Произошла ошибка при попытке отправить логи в Log.Service. Ошибка: {exception}", e.Message);
            }
        }

        private void LogCustomException(Exception ex, object entity)
        {
            _logger.LogError(ex, "Запрос {entity}", JsonSerializer.Serialize(entity));
        }
    }
}