using MediatR;

namespace Credit.Lib.Feature.Ping;

public class Handler : IRequestHandler<Request>
{
    public Task Handle(Request request, CancellationToken cancellationToken)
    {
        Console.WriteLine("Handler executed");
        return Task.CompletedTask;
    }
}