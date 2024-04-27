namespace Credit.Api.Middlewares;

public class RequestEnrichMiddleware
{
    private readonly RequestDelegate _next;

    public RequestEnrichMiddleware(RequestDelegate next)
    {
        _next = next;
    }

    public async Task Invoke(HttpContext context)
    {
        if (context.Request.Headers.TryGetValue(Primitives.Constants.HttpContext.Request.Headers.RequestId,
                out var requestId))
        {
            context.Request.Headers[Primitives.Constants.HttpContext.Request.Headers.XRequestId] =
                requestId.FirstOrDefault();
        }

        if (context.Request.Headers.TryGetValue(Primitives.Constants.HttpContext.Request.Headers.IdempotentKey,
                out var idempotentKey))
        {
            context.Request.Headers[Primitives.Constants.HttpContext.Request.Headers.XIdempotentKey] =
                idempotentKey.FirstOrDefault();
        }

        if (!context.Request.Headers.TryGetValue(Primitives.Constants.HttpContext.Request.Headers.XRequestId, out _))
        {
            context.Request.Headers[Primitives.Constants.HttpContext.Request.Headers.XRequestId] =
                Guid.NewGuid().ToString();
        }
        
        if (!context.Request.Headers.TryGetValue(Primitives.Constants.HttpContext.Request.Headers.XIdempotentKey, out _))
        {
            context.Request.Headers[Primitives.Constants.HttpContext.Request.Headers.XIdempotentKey] =
                Guid.NewGuid().ToString();
        }

        await _next(context);
    }
}