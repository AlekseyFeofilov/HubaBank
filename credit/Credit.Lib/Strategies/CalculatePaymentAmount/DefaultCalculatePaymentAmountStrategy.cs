using System.Diagnostics.CodeAnalysis;
using Utils.DateTime;

namespace Credit.Lib.Strategies.CalculatePaymentAmount;

public class DefaultCalculatePaymentAmountStrategy : ICalculatePaymentAmountStrategy
{
    private long Principal { get; }
    private DateOnly CompletionDate { get; }
    private int MonthsToComplete { get; }
    private long MonthPayment { get; }
    
    [SuppressMessage("ReSharper", "PossibleLossOfFraction")]
    public DefaultCalculatePaymentAmountStrategy(long principal, DateOnly completionDate)
    {
        Principal = principal;
        CompletionDate = completionDate;
        MonthsToComplete = CompletionDate.GetDifferenceInMonths(DateTime.Now);
        
        // todo если булет слишком маленький кредит, то он вырастет за счёт этой логике. Надо запретить делать такие маленькие кредиты
        MonthPayment = (long)Math.Ceiling(1.0 * Principal / MonthsToComplete / 10) * 10;
    }
    
    public long Calculate(int monthsAfterToday)
    {
        if (monthsAfterToday != MonthsToComplete) return MonthPayment;
        
        var lastPayment = Principal - MonthPayment * (MonthsToComplete - 1);
        return lastPayment > 0 ? lastPayment : 10;

    }
}