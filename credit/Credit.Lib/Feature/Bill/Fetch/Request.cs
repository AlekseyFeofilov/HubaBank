using Core.Provider.v3;
using MediatR;

namespace Credit.Lib.Feature.Bill.Fetch;

public class Request : IRequest<BillDtoV2>
{
    public Request(Guid billId)
    {
        BillId = billId;
    }

    public Guid BillId { get; set; }
    public required Guid RequestId { get; init; }
}