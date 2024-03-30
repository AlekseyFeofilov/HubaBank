using Credit.Lib.Jobs;
using Extensions.Hosting.AsyncInitialization;
using MediatR;

namespace Credit.Api;

public class JobsInitializer : IAsyncInitializer
{
    private readonly IMediator _mediator;

    private readonly IJobClient _jobClient;

    public JobsInitializer(IMediator mediator, IJobClient jobClient)
    {
        _mediator = mediator;
        _jobClient = jobClient;
    }

    public Task InitializeAsync(CancellationToken cancellationToken)
    {
        return StartJobs();
    }

    public Task StartJobs()
    {
        _jobClient.EnqueuePing();
        return Task.CompletedTask;
    }

    public async Task Debug(CancellationToken cancellationToken)
    {
        // await _mediator.Send(new Lib.Feature.Ping.Request(), cancellationToken);
        // Console.WriteLine("Pong");
    }
}