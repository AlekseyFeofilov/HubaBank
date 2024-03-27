using Credit.Lib.Exceptions;
using MediatR.Pipeline;

namespace Credit.Lib.Feature.Credit.Update;

public class PreProcessor : IRequestPreProcessor<Request>
{
    public Task Process(Request request, CancellationToken cancellationToken)
    {
        if (request.UpdateRequest.CreditTermsId == null &&
            request.UpdateRequest.InterestRate == null)
        {
            throw new CreditInterestRateOrCreditTermsRequiredException();
        }
        
        return Task.CompletedTask;
    }
}