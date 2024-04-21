using MediatR;
using Microsoft.Extensions.Logging;

namespace Credit.Lib.Feature.Ping;

public class Handler : IRequestHandler<Request>
{
    private readonly ILogger<Handler> _logger;

    public Handler(ILoggerFactory loggerFactory)
    {
        _logger = loggerFactory.CreateLogger<Handler>();
    }

    public Task Handle(Request request, CancellationToken cancellationToken)
    {
        Console.WriteLine("Handler executed");
        return Task.CompletedTask;
    }
}