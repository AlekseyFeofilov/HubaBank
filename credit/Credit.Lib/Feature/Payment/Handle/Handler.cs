using MediatR;

namespace Credit.Lib.Feature.Payment.Handle;

public class Handler : IRequestHandler<Request>
{
    private readonly IMediator _mediator;

    public Handler(IMediator mediator)
    {
        _mediator = mediator;
    }

    public Task Handle(Request request, CancellationToken cancellationToken)
    {
        
        
        Console.WriteLine($"Payment {request.Id} was handled");
        return Task.CompletedTask;
    }
}