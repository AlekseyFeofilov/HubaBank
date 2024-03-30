using Credit.Data.Requests.Credit;
using MediatR;
using MediatR.Pipeline;

namespace Credit.Lib.Feature.Credit.Create;

public class PostProcessor : IRequestPostProcessor<Request, IReadOnlyCollection<CreateRequest>>
{
    private readonly IMediator _mediator;

    public PostProcessor(IMediator mediator)
    {
        _mediator = mediator;
    }
    
    public Task Process(Request request, IReadOnlyCollection<CreateRequest> response, CancellationToken cancellationToken)
    {
        var credit = response.First();
        Console.WriteLine($"Credit {credit.Id} was created");
        
        var notification = new Notification
        {
            Id = credit.Id,
            AccountsPayable = credit.Principal,
            CompletionDate = credit.CompletionDate,
        };
        
        return _mediator.Publish(notification, cancellationToken);
    }
}