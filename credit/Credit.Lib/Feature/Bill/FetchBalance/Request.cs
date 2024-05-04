using MediatR;

namespace Credit.Lib.Feature.Bill.FetchBalance;

public class Request : IRequest<long>
{
    public Request(Guid billId)
    {
        BillId = billId;
    }

    public Guid BillId { get; set; }
    public required Guid RequestId { get; init; }
}