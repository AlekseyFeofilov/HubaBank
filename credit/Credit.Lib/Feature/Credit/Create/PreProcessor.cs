using Credit.Lib.Exceptions;
using MediatR.Pipeline;
using Microsoft.Extensions.Logging;

namespace Credit.Lib.Feature.Credit.Create;

public class PreProcessor : IRequestPreProcessor<Request>
{
    private readonly ILogger<PreProcessor> _logger;

    public PreProcessor(ILogger<PreProcessor> logger)
    {
        _logger = logger;
    }

    public Task Process(Request request, CancellationToken cancellationToken)
    {
        _logger.LogWarning("Trying to create credit with id {creditId}", request.CreateRequest.Id);
        
        if (request.CreateRequest.CreditTermsId == null &&
            request.CreateRequest.InterestRate == null)
        {
            _logger.LogWarning("Failed to create credit with id {creditId}", request.CreateRequest.Id);
            throw new CreditInterestRateOrCreditTermsRequiredException();
        }

        //todo инкапсулировать + переименовать
        request.CreateRequest.AccountsPayable = request.CreateRequest.Principal;
        
        return Task.CompletedTask;
    }
}