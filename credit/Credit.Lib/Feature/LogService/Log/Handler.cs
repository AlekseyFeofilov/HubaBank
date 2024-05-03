using LogService.Provider;
using MediatR;
using Microsoft.AspNetCore.Http.Extensions;
using Utils.Http;

namespace Credit.Lib.Feature.LogService.Log;

public class Handler : IRequestHandler<Request>
{
    private readonly ILogServiceProviderV1 _logServiceProvider;

    public Handler(ILogServiceProviderV1 logServiceProvider)
    {
        _logServiceProvider = logServiceProvider;
    }

    public async Task Handle(Request request, CancellationToken cancellationToken)
    {
        var context = request.HttpContext;
        
        
        await _logServiceProvider.PublishLogAsync(new PublishLogDto
        {
            RequestId = context.GetXRequestId(),
            PublishService = "credit",
            Request = new RequestDto
            {
                Url = context.Request.GetDisplayUrl(),
                Method = context.Request.Method,
                Headers = context.Response.Headers.ToPlainDictionary(),
                Body = await context.ReadRequest()
            },
            Response = new ResponseDto
            {
                Status = context.Response.StatusCode,
                Headers = context.Response.Headers.ToPlainDictionary(),
                Body = request.ResponseBody
            },
            OtherInfo = "Лёша - секс символ HITs'а"
        }, cancellationToken);
    }
}