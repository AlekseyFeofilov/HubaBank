namespace Credit.Lib.Jobs;

public interface IJobClient
{
    void EnqueuePing();
    void EnqueueNextDayPayments();
    void EnqueuePayment(Guid id);
}