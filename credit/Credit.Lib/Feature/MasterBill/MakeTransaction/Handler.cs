using Core.Provider.v1;
using Credit.Lib.Exceptions;
using Credit.Lib.Models;
using MediatR;

namespace Credit.Lib.Feature.MasterBill.MakeTransaction;

public class Handler : IRequestHandler<Request>
{
    private readonly ICoreProviderV1 _coreProviderV1;
    private readonly MasterBillSettings _masterBillSettings;

    public Handler(ICoreProviderV1 coreProviderV1, MasterBillSettings masterBillSettings)
    {
        _coreProviderV1 = coreProviderV1;
        _masterBillSettings = masterBillSettings;
    }

    public async Task Handle(Request request, CancellationToken cancellationToken)
    {
        //todo желательно делать это одной операцией, но это к Роме
        if (request.AmountOfMoney == 0)
        {
            throw new BadRequestException("Transaction must be with non zero balance change");
        }
        
        await _coreProviderV1.CreateTransactionAsync(_masterBillSettings.MasterBillId, new TransactionCreationDto
        {
            BalanceChange = request.AmountOfMoney
        }, cancellationToken);
        
        await _coreProviderV1.CreateTransactionAsync(request.SecondBillId, new TransactionCreationDto
        {
            BalanceChange = -request.AmountOfMoney
        }, cancellationToken);
    }
}