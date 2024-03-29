using Credit.Data.Requests.Credit;
using MediatR;
using MediatR.Pipeline;

namespace Credit.Lib.Feature.Credit.Create;

public class PostProcessor : IRequestPostProcessor<Request, CreateRequest>
{
    private readonly IMediator _mediator;

    public PostProcessor(IMediator mediator)
    {
        _mediator = mediator;
    }
    
    public Task Process(Request request, CreateRequest response, CancellationToken cancellationToken)
    {
        var notification = new Notification
        {
            Id = response.Id,
            AccountsPayable = response.Principal,
            CompletionDate = response.CompletionDate,
        };
        
        return _mediator.Publish(notification, cancellationToken);
    }
}