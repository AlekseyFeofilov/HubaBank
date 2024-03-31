using Utils.DateTime;

namespace Credit.Lib.Strategies.CalculatePaymentAmount;

public class DefaultCalculatePaymentAmountStrategy : ICalculatePaymentAmountStrategy
{
    private long Principal { get; }
    private DateOnly CompletionDate { get; }
    private int MonthsToComplete { get; }
    
    public DefaultCalculatePaymentAmountStrategy(long principal, DateOnly completionDate)
    {
        Principal = principal;
        CompletionDate = completionDate;
        
        MonthsToComplete = CompletionDate.GetDifferenceInMonths(DateTime.Now);
    }
    
    public long Calculate(int monthsAfterToday)
    {
        if (monthsAfterToday == MonthsToComplete)
        {
            return Principal - Principal / MonthsToComplete * MonthsToComplete;
        }

        //todo здесть надо округлять не до меньшего, а до большего, чтобы в последний месяц нужно было платить меньше денег, а не больше
        return Principal / MonthsToComplete;
    }
}