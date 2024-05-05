using Credit.Lib.Feature.Utils.Setting.Seed;
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

    public async Task InitializeAsync(CancellationToken cancellationToken)
    {
        await StartJobs();
        await SeedData();
    }

    private Task StartJobs()
    {
        _jobClient.EnqueueTodayPayments();
        return Task.CompletedTask;
    }

    private async Task SeedData()
    {
        await _mediator.Send(new Request());
        await _mediator.Send(new Lib.Feature.Utils.CircuitBreaker.Seed.Request());
        await _mediator.Send(new Lib.Feature.Utils.Day.Seed.Request());
    }
}