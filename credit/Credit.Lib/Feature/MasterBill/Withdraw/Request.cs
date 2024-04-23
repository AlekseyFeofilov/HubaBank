using MediatR;

namespace Credit.Lib.Feature.MasterBill.Withdraw;

public class Request : IRequest
{
    public Request(Guid destinationBillId, long amountOfMoney)
    {
        AmountOfMoney = amountOfMoney;
        DestinationBillId = destinationBillId;
    }

    public long AmountOfMoney { get; set; }
    public Guid DestinationBillId { get; set; }
}