using Credit.Data.Requests.Credit;
using MediatR;
using MediatR.Pipeline;
using Microsoft.Extensions.Logging;

namespace Credit.Lib.Feature.Credit.Create;

public class PostProcessor : IRequestPostProcessor<Request, IReadOnlyCollection<CreateRequest>>
{
    private readonly IMediator _mediator;
    private readonly ILogger<PostProcessor> _logger;

    public PostProcessor(IMediator mediator, ILogger<PostProcessor> logger)
    {
        _mediator = mediator;
        _logger = logger;
    }
    
    public Task Process(Request request, IReadOnlyCollection<CreateRequest> response, CancellationToken cancellationToken)
    {
        var credit = response.First();
        _logger.LogWarning("Credit {creditId} was created", credit.Id);
        
        var notification = new Notification
        {
            CreditId = credit.Id,
            Principal = credit.Principal,
            CompletionDate = credit.CompletionDate,
        };
        
        _logger.LogWarning("Published notification about credit with id {creditId} creation", request.CreateRequest.Id);
        return _mediator.Publish(notification, cancellationToken);
    }
}