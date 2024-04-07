using System.ComponentModel;
using Hangfire;

namespace Credit.Lib.Jobs;

public interface IJobAgent
{
    [DisplayName("Тестовая отправка Ping через Hangfire")]
    [AutomaticRetry(Attempts = 3, OnAttemptsExceeded = AttemptsExceededAction.Delete)]
    public Task Ping();

    [DisplayName("Запуск отложенных оплат по кредиту")]
    [AutomaticRetry(Attempts = 3)]
    public Task EnqueueTodayPayments();
    
    [DisplayName("Запуск отложенной оплаты {0} по кредита")]
    [AutomaticRetry(Attempts = 10)]
    public Task EnqueuePayment(Guid paymentId);
    
    [DisplayName("Запуск активации кредита {0}")]
    [AutomaticRetry(Attempts = 10)]
    public Task EnqueueCreditActivation(Guid creditId);
}