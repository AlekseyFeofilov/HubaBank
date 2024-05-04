using MediatR;

namespace Credit.Lib.Feature.MasterBill.Balance.Fetch;

public class Request : IRequest<long>
{
    public required Guid RequestId { get; init; }
}