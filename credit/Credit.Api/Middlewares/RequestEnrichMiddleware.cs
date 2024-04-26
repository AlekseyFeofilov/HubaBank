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
        if (context.Request.Query.TryGetValue(Primitives.Constants.HttpContext.Request.Query.RequestId,
                out var requestId))
        {
            context.Request.Headers[Primitives.Constants.HttpContext.Request.Headers.RequestId] =
                requestId.FirstOrDefault();
        }

        if (context.Request.Query.TryGetValue(Primitives.Constants.HttpContext.Request.Query.IdempotentKey,
                out var idempotentKey))
        {
            context.Request.Headers[Primitives.Constants.HttpContext.Request.Headers.IdempotentKey] =
                idempotentKey.FirstOrDefault();
        }

        if (!context.Request.Headers.TryGetValue(Primitives.Constants.HttpContext.Request.Headers.IdempotentKey, out _))
        {
            context.Request.Headers[Primitives.Constants.HttpContext.Request.Headers.IdempotentKey] =
                Guid.NewGuid().ToString();
        }

        await _next(context);
    }
}