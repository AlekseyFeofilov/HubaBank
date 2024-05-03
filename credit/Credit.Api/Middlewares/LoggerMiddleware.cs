using MediatR;

namespace Credit.Api.Middlewares;

public class LoggerMiddleware : IMiddleware
{
    private readonly IMediator _mediator;

    public LoggerMiddleware(IMediator mediator)
    {
        _mediator = mediator;
    }
    
    public async Task InvokeAsync(HttpContext context, RequestDelegate next)
    {
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
        finally
        {
            context.Response.Body = originalBody;
        }

        await _mediator.Send(new Lib.Feature.LogService.Log.Request
        { HttpContext = context,
            ResponseBody = responseBodyContent
        });
    }
}