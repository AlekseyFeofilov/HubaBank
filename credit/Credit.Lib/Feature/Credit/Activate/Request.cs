using Credit.Lib.Strategies.CalculatePaymentAmount;
using MediatR;

namespace Credit.Lib.Feature.Credit.Activate;

public class Request : IRequest
{
    public Guid CreditId { get; init; }
    public ICalculatePaymentAmountStrategy CalculatePaymentAmountStrategy { get; init; }
}