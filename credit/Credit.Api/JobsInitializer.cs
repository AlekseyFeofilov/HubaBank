using Core.Provider;
using Core.Provider.v1;
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
        await _mediator.Send(new Lib.Feature.Setting.Seed.Request());
    }
}