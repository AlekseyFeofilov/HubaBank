using Credit.Data.Responses;
using Credit.Lib.Exceptions;
using MediatR.Pipeline;

namespace Credit.Lib.Feature.CreditTerms.Delete;

public class PostProcessor : IRequestPostProcessor<Request, CreditTermsResponse>
{
    public Task Process(Request request, CreditTermsResponse response, CancellationToken cancellationToken)
    {
        if (response == null)
        {
            throw new EntityNotFoundException(request.Id);
        }
        
        return Task.CompletedTask;
    }
}