using System.ComponentModel;
using Hangfire;

namespace Credit.Lib.Jobs;

public interface IJobAgent
{
    [DisplayName("Тестовая отправка Ping через Hangfire")]
    [AutomaticRetry(Attempts = 0)]
    public Task Ping();
}