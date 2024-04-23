using Credit.Data.Responses;
using MediatR;

namespace Credit.Lib.Feature.Payment.FetchOverdid;

public class Request : IRequest<IReadOnlyCollection<PaymentResponse>>
{
    public Request(Guid? creditId = null)
    {
        CreditId = creditId;
    }

    public Guid? CreditId { get; }
}