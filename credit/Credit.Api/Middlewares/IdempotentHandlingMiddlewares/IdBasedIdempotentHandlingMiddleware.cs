using Credit.Api.Attributes;
using MediatR;
using Microsoft.AspNetCore.Http.Features;

namespace Credit.Api.Middlewares.IdempotentHandlingMiddlewares;

public class IdBasedIdempotentHandlingMiddleware : IdempotentHandlingMiddleware
{
    private const string Id = "id";
    
    public IdBasedIdempotentHandlingMiddleware(IMediator mediator) : base(mediator)
    {
    }

    protected override Task<bool> IsCompatible(HttpContext context)
    {
        var endpoint = context.Features.Get<IEndpointFeature>()?.Endpoint;
        var attribute = endpoint?.Metadata.GetMetadata<IdBasedIdempotentHandlingMiddlewareAllow>();

        if (attribute == null)
        {
            return Task.FromResult(false);
        }
        
        context.GetRouteData().Values.TryGetValue(Id, out var id);
        return Task.FromResult(Guid.TryParse(id?.ToString() ?? "", out _));
    }

    protected override Task<string> GetConfirmationKey(HttpContext context)
    {
        context.GetRouteData().Values.TryGetValue(Id, out var id);
        return Task.FromResult((string)id!);
    }
}