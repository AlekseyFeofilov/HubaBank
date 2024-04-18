using MediatR;

namespace Credit.Lib.Feature.Credit.Arrears.EnqueueActualisation;

public class Request : IRequest
{
    public Request(Guid creditId)
    {
        CreditId = creditId;
    }

    public Guid CreditId { get; }
}