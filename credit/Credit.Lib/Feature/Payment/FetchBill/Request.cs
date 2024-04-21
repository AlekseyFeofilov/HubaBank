using Core.Provider.v2;
using MediatR;

namespace Credit.Lib.Feature.Payment.FetchBill;

public class Request : IRequest<BillDtoV2>
{
    public Request(Guid paymentId)
    {
        PaymentId = paymentId;
    }

    public Guid PaymentId { get; set; } 
}