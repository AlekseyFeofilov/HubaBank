using MediatR;
using Microsoft.AspNetCore.Http;

namespace Credit.Lib.Feature.LogService.Log;

public class Request : IRequest
{
    public required HttpContext HttpContext { get; init; }
    public required string ResponseBody { get; init; }
}