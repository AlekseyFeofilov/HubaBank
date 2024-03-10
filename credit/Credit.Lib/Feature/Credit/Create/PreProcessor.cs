using Credit.Lib.Exceptions;
using MediatR.Pipeline;

namespace Credit.Lib.Feature.Credit.Create;

public class PreProcessor : IRequestPreProcessor<Request>
{
    public Task Process(Request request, CancellationToken cancellationToken)
    {
        if (request.CreateRequest.CreditTermsId == null &&
            request.CreateRequest.InterestRate == null)
        {
            throw new CreditInterestRateOrCreditTermsRequiredException();
        }
        
        return Task.CompletedTask;
    }
}