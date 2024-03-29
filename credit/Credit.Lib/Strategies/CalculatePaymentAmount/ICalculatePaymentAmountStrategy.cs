namespace Credit.Lib.Strategies.CalculatePaymentAmount;

public interface ICalculatePaymentAmountStrategy
{ 
    long Calculate(int monthsAfterToday);
}