using MediatR.Pipeline;

namespace Credit.Lib.Feature.Ping;

public class PreProcessor : IRequestPreProcessor<Request>
{
    public Task Process(Request request, CancellationToken cancellationToken)
    {
        Console.WriteLine("PreProcessor executed");
        return Task.CompletedTask;
    }
}