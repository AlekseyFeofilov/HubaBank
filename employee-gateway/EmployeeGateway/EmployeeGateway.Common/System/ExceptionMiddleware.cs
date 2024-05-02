using EmployeeGateway.Common.DTO;
using EmployeeGateway.Common.Exceptions;
using Microsoft.AspNetCore.Http;
using Microsoft.Extensions.Logging;

namespace EmployeeGateway.Common.System;

public class ExceptionMiddleware
{
    private readonly RequestDelegate _requestDelegate;
    private readonly ILogger<ExceptionMiddleware> _logger;

    public ExceptionMiddleware(RequestDelegate requestDelegate, ILogger<ExceptionMiddleware> logger)
    {
        _requestDelegate = requestDelegate;
        _logger = logger;
    }

    public async Task Invoke(HttpContext context)
    {
        try
        {
            await _requestDelegate(context);
        }
        catch (IncorrectDataException exception)
        {
            _logger.LogError(exception.Message);
            context.Response.StatusCode = StatusCodes.Status400BadRequest;
            await context.Response.WriteAsJsonAsync(new ErrorResponse { StatusCode = 400, Message = exception.Message });
        }
        catch (ForbiddenException exception)
        {
            _logger.LogError(exception.Message);
            context.Response.StatusCode = StatusCodes.Status403Forbidden;
            await context.Response.WriteAsJsonAsync(new ErrorResponse { StatusCode = 403, Message = exception.Message });
        }
        catch (NotFoundItemException exception)
        {
            _logger.LogError(exception.Message);
            context.Response.StatusCode = StatusCodes.Status404NotFound;
            await context.Response.WriteAsJsonAsync(new ErrorResponse { StatusCode = 404, Message = exception.Message });
        }
        catch (Exception e)
        {
            _logger.LogError(e.Message);
            context.Response.StatusCode = StatusCodes.Status500InternalServerError;
            await context.Response.WriteAsJsonAsync(new ErrorResponse { StatusCode = 500, Message = e.Message });
        }
    }
}