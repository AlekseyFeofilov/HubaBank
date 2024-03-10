using Credit.Data.Responses;
using Credit.Lib.Exceptions;
using MediatR.Pipeline;

namespace Credit.Lib.Feature.Credit.FetchById;

public class PostProcessor : IRequestPostProcessor<Request, CreditResponse>
{
    public Task Process(Request request, CreditResponse response, CancellationToken cancellationToken)
    {
        if (response == null)
        {
            throw new CreditNotFoundException(request.Id);
        }
        
        return Task.CompletedTask;
    }
}