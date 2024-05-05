using System.Net;
using MediatR;
using Utils.Hash;

namespace Credit.Api.Middlewares.IdempotentHandlingMiddlewares;

public abstract class IdempotentHandlingMiddleware : IMiddleware
{
    private readonly IMediator _mediator;

    protected IdempotentHandlingMiddleware(IMediator mediator)
    {
        _mediator = mediator;
    }
    
    protected abstract Task<bool> IsCompatible(HttpContext context);

    protected abstract Task<string> GetConfirmationKey(HttpContext context);

    public async Task InvokeAsync(HttpContext context, RequestDelegate next)
    {
        if (!await IsCompatible(context))
        {
            await next(context);
            return;
        }

        if (!TryGetIdempotentKey(context, out var idempotentKey))
        {
            await next(context);
            return;
        }

        var idempotentRequest = await _mediator.Send(new Lib.Feature.Utils.IdempotentRequest.Fetch.Request(idempotentKey));
        if (idempotentRequest == null)
        {
            await ResolveWithoutIdempotentRequestAsync(context, next, idempotentKey);
            return;
        }

        await ResolveWithIdempotentRequestAsync(context, next, idempotentRequest);
    }

    private static bool TryGetIdempotentKey(HttpContext context, out Guid result)
    {
        context.Request.Headers.TryGetValue(Primitives.Constants.HttpContext.Request.Headers.IdempotentKey,
            out var idempotentKeyStringValues);

        return Guid.TryParse(idempotentKeyStringValues.FirstOrDefault(), out result);
    }

    private async Task ResolveWithIdempotentRequestAsync(HttpContext context,
        RequestDelegate next,
        Data.Responses.IdempotenceRequestResponse idempotentRequest)
    {
        switch (idempotentRequest)
        {
            case { Completed: true } when
                !(await GetConfirmationKey(context)).VerifyHash(idempotentRequest.ConfirmationKeyHash):
                await next(context);
                return;

            case { Completed: true }:
                context.Response.StatusCode =
                    (int)(idempotentRequest.HttpStatusCode ?? HttpStatusCode.InternalServerError);
                context.Response.ContentType = "application/json";
                await context.Response.WriteAsync(idempotentRequest.Response);
                return;

            case { Completed: false }:
                context.Response.StatusCode = (int)HttpStatusCode.Conflict;
                return;
        }
    }

    private async Task ResolveWithoutIdempotentRequestAsync(HttpContext context,
        RequestDelegate next,
        Guid idempotentKey)
    {
        var idempotentRequestCreateRequest = new Data.Requests.IdempotentRequest.CreateRequest
        {
            Id = idempotentKey,
            ConfirmationKeyHash = (await GetConfirmationKey(context)).GetHash(),
        };

        await _mediator.Send(new Lib.Feature.Utils.IdempotentRequest.Create.Request(idempotentRequestCreateRequest));
        string responseBodyContent;
        var originalBody = context.Response.Body;

        try
        {
            using var memStream = new MemoryStream();
            context.Response.Body = memStream;

            await next(context);

            memStream.Position = 0;
            responseBodyContent = await new StreamReader(memStream).ReadToEndAsync();

            memStream.Position = 0;
            await memStream.CopyToAsync(originalBody);
        }
        catch (Exception _)
        {
            await _mediator.Send(new Lib.Feature.Utils.IdempotentRequest.Delete.Request(idempotentRequestCreateRequest.Id));
            throw;
        }
        finally
        {
            context.Response.Body = originalBody;
        }

        await _mediator.Send(new Lib.Feature.Utils.IdempotentRequest.Complete.Request(idempotentRequestCreateRequest.Id,
            responseBodyContent, (HttpStatusCode)context.Response.StatusCode));
    }
}