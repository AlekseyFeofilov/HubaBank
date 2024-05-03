using Microsoft.AspNetCore.Http;

namespace Utils.Http;

public static class HttpContextExtensions
{
    public static async Task<string> ReadRequest(this HttpContext context)
    {
        context.Request.EnableBuffering();
        var requestBodyContent = await new StreamReader(context.Request.Body).ReadToEndAsync();
        context.Request.Body.Position = 0;

        return requestBodyContent;
    }
    
    public static Dictionary<string, string> ToPlainDictionary(this IHeaderDictionary headers)
    {
        return headers.ToDictionary(a => a.Key, a => string.Join(";", a.Value));
    }

    public static Guid GetXRequestId(this HttpContext context)
    {
        context.Request.Headers.TryGetValue("X-Request-ID", out var requestIdStringValue);
        return Guid.Parse(requestIdStringValue.First()!);
    }
}