using Credit.Api.Attributes;
using MediatR;
using Microsoft.AspNetCore.Http.Features;

namespace Credit.Api.Middlewares;

public class BodyBasedIdempotentHandlingMiddleware : IdempotentHandlingMiddleware
{
    public BodyBasedIdempotentHandlingMiddleware(IMediator mediator) : base(mediator)
    {
    }

    protected override Task<bool> IsCompatible(HttpContext context)
    {
        var endpoint = context.Features.Get<IEndpointFeature>()?.Endpoint;
        var attribute = endpoint?.Metadata.GetMetadata<BodyBasedIdempotentHandlingMiddlewareAllow>();
        return Task.FromResult(attribute != null);
    }

    protected override async Task<string> GetConfirmationKey(HttpContext context)
    {
        context.Request.EnableBuffering();
        var requestBodyContent = await new StreamReader(context.Request.Body).ReadToEndAsync();
        context.Request.Body.Position = 0;

        return requestBodyContent;
    }
}