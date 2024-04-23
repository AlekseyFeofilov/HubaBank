using Credit.Primitives;
using MediatR;

namespace Credit.Lib.Feature.MasterBill.MakeTransaction;

public class Request : IRequest
{
    public required long AmountOfMoney { get; init; }
    public required Guid SecondBillId { get; init; }
    public required TransactionType TransactionType { get; init; }
}