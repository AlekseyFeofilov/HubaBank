using MediatR;

namespace Credit.Lib.Feature.Credit.Interest.Handle;

public class Request : IRequest
{
    public Request(Guid creditId)
    {
        CreditId = creditId;
    }

    public Guid CreditId { get; set; }
}