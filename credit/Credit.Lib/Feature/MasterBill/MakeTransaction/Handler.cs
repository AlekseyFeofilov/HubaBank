using Core.Provider.v1;
using Credit.Lib.Exceptions;
using Credit.Lib.Extensions;
using Credit.Lib.Models;
using Credit.Primitives;
using MediatR;

namespace Credit.Lib.Feature.MasterBill.MakeTransaction;

public class Handler : IRequestHandler<Request>
{
    private readonly ICoreProviderV1 _coreProviderV1;
    private readonly IMediator _mediator;
    private readonly Guid _masterBillId;

    public Handler(ICoreProviderV1 coreProviderV1, MasterBillSettings masterBillSettings, IMediator mediator)
    {
        _coreProviderV1 = coreProviderV1;
        _masterBillId = masterBillSettings.MasterBillId;
        _mediator = mediator;
    }

    public async Task Handle(Request request, CancellationToken cancellationToken)
    {
        if (request.AmountOfMoney == 0)
        {
            throw new BadRequestException("Transaction must be with non zero balance change");
        }
        
        var (sourceBillId, targetBillId) = request.TransactionType switch
        {
            TransactionType.Deposit => (request.SecondBillId, _masterBillId),
            TransactionType.Withdraw => (_masterBillId, request.SecondBillId),
            _ => throw new ArgumentOutOfRangeException()
        };
        
        var body = new Data.Requests.Core.Transfer.Request
        {
            SourceBillId = sourceBillId,
            TargetBillId = targetBillId,
            Amount = request.AmountOfMoney,
        }.ToJsonByteArray();

        await _mediator.Send(new Rabbit.QueueDeclare.Request
        {
            Body = body,
            Queue = RabbitConstants.TransferToBillRequestQueue,
            RoutingKey = "to_bill"
        }, cancellationToken);
    }
}