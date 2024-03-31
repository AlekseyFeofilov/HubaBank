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

    private readonly ICoreProviderV1 _coreProviderV1;

    public JobsInitializer(IMediator mediator, IJobClient jobClient, ICoreProviderV1 coreProviderV1)
    {
        _mediator = mediator;
        _jobClient = jobClient;
        _coreProviderV1 = coreProviderV1;
    }

    public async Task InitializeAsync(CancellationToken cancellationToken)
    {
        await Debug(cancellationToken);
        await StartJobs();
    }

    private Task StartJobs()
    {
        // _jobClient.EnqueuePing();
        _jobClient.EnqueueTodayPayments();
        return Task.CompletedTask;
    }

    private async Task Debug(CancellationToken cancellationToken)
    {
        // Console.WriteLine("Debug method in Initializer started");
        // var bill = await _coreProvider.Async(Guid.Parse("e171f12a-8116-4c03-9d9d-e9b387851f3c"), cancellationToken);
        //
        // Console.WriteLine(bill.Id);
        // Console.WriteLine(bill.Balance);
        // Console.WriteLine();

        await _mediator.Send(new Lib.Feature.Ping.Request(), cancellationToken);
        // Console.WriteLine("Pong");
    }
}