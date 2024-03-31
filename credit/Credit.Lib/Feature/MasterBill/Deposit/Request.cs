using MediatR;

namespace Credit.Lib.Feature.MasterBill.Deposit;

public class Request : IRequest
{
    public Request(Guid sourceBillId, long amountOfMoney)
    {
        AmountOfMoney = amountOfMoney;
        SourceBillId = sourceBillId;
    }

    public long AmountOfMoney { get; set; }
    public Guid SourceBillId { get; set; }
}