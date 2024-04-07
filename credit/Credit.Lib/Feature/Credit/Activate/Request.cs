using MediatR;

namespace Credit.Lib.Feature.Credit.Activate;

public class Request : IRequest
{
    public Request(Guid creditId)
    {
        CreditId = creditId;
    }

    public Guid CreditId { get; init; }
}