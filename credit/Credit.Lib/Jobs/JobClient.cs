using Hangfire;

namespace Credit.Lib.Jobs;

public class JobClient : IJobClient
{
    private readonly IRecurringJobManager _recurringJobManager;
    private readonly Func<Guid, string, string> _createJobPrefix;

    public JobClient(IRecurringJobManager recurringJobManager)
    {
        _recurringJobManager = recurringJobManager;
        _createJobPrefix = (id, prefix) => $"{prefix}-{id}";
    }

    public void EnqueuePing()
    {
        _recurringJobManager.AddOrUpdate<IJobAgent>("ping", 
            agent => agent.Ping(), 
            "0/5 * * * *");
    }
}