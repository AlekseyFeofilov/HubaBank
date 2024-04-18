using MediatR;

namespace Credit.Lib.Feature.Credit.HaveArrears;

public class Request : IRequest<bool>
{
    public Request(Guid creditId)
    {
        CreditId = creditId;
    }

    public Guid CreditId { get; set; }
}