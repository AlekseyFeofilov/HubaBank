using Utils.DateTime;

namespace Credit.Lib.Strategies.CalculatePaymentAmount;

public class DefaultCalculatePaymentAmountStrategy : ICalculatePaymentAmountStrategy
{
    private long AccountsPayable { get; }
    private DateOnly CompletionDate { get; }
    private int MonthsToComplete { get; }
    
    public DefaultCalculatePaymentAmountStrategy(long accountsPayable, DateOnly completionDate)
    {
        AccountsPayable = accountsPayable;
        CompletionDate = completionDate;
        
        MonthsToComplete = CompletionDate.GetDifferenceInMonths(DateTime.Now);
    }
    
    public long Calculate(int monthsAfterToday)
    {
        if (monthsAfterToday == MonthsToComplete)
        {
            return AccountsPayable - AccountsPayable / MonthsToComplete * MonthsToComplete;
        }

        return AccountsPayable / MonthsToComplete;
    }
}