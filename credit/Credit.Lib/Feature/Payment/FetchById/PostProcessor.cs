using Credit.Data.Responses;
using Credit.Lib.Exceptions;
using MediatR.Pipeline;

namespace Credit.Lib.Feature.Payment.FetchById;

public class PostProcessor : IRequestPostProcessor<Request, PaymentResponse>
{
    public Task Process(Request request, PaymentResponse response, CancellationToken cancellationToken)
    {
        if (response == null)
        {
            throw new EntityNotFoundException(request.Id);
        }
        
        return Task.CompletedTask;
    }
}