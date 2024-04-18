using MediatR;

namespace Credit.Lib.Feature.MasterBill.MakeTransaction;

public class Request : IRequest
{
    public Request(Guid secondBillId, long amountOfMoney)
    {
        AmountOfMoney = amountOfMoney;
        SecondBillId = secondBillId;
    }

    public long AmountOfMoney { get; set; }
    public Guid SecondBillId { get; set; }
}