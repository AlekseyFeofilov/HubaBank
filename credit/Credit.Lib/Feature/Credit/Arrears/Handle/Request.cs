using MediatR;

namespace Credit.Lib.Feature.Credit.Arrears.Handle;

public class Request : IRequest
{
    public Request(Guid creditId)
    {
        CreditId = creditId;
    }

    public Guid CreditId { get; set; }
    public required Guid RequestId { get; init; }
}