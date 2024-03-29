using MediatR;

namespace Credit.Lib.Feature.Ping;

public class Handler : IRequestHandler<Request>
{
    public Task Handle(Request request, CancellationToken cancellationToken)
    {
        Console.WriteLine(5 - 5 / 3 * 3);
        Console.WriteLine("Handler executed");
        return Task.CompletedTask;
    }
}