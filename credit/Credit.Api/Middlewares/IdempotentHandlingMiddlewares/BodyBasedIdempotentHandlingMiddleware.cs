using Credit.Api.Attributes;
using MediatR;
using Microsoft.AspNetCore.Http.Features;
using Utils.Http;

namespace Credit.Api.Middlewares.IdempotentHandlingMiddlewares;

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

    protected override Task<string> GetConfirmationKey(HttpContext context)
    {
        return context.ReadRequest();
    }
}