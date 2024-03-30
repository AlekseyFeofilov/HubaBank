using Hangfire;
using Utils.DateTime;

namespace Credit.Lib.Jobs;

public class JobClient : IJobClient
{
    private readonly IRecurringJobManager _recurringJobManager;
    private readonly IBackgroundJobClient _backgroundJobClient;
    //todo разобраться с обработкой разных queue
    private readonly Func<string, string, string> _createJobPrefix;

    public JobClient(IRecurringJobManager recurringJobManager, IBackgroundJobClient backgroundJobClient)
    {
        _recurringJobManager = recurringJobManager;
        _backgroundJobClient = backgroundJobClient;
        _createJobPrefix = (id, prefix) => $"{prefix}--{id}";
    }

    public void EnqueuePing()
    {
        _recurringJobManager.AddOrUpdate<IJobAgent>("ping", 
            agent => agent.Ping(), 
            "0/30 * * * *");
    }

    //todo переделать локигу на поиск сегодняшних оплат, потому что джобы отрабатывают сразу
    public void EnqueueNextDayPayments()
    {
        _recurringJobManager.AddOrUpdate<IJobAgent>("EnqueueNextDayPayments",
            agent => agent.EnqueueNextDayPayments(),
            "0 0 * * *");
    }

    public void EnqueuePayment(Guid id)
    {
        _backgroundJobClient.Enqueue<IJobAgent>(agent => agent.EnqueuePayment(id));
    }
}