namespace Credit.Lib.Jobs;

public interface IJobClient
{
    void EnqueuePing();
    void EnqueueTodayPayments();
    void EnqueuePayment(Guid paymentId);
    void EnqueueCreditActivation(Guid creditId);
}