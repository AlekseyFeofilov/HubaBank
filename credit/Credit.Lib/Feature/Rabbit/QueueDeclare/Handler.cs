using Credit.Lib.Models;
using MediatR;
using RabbitMQ.Client;

namespace Credit.Lib.Feature.Rabbit.QueueDeclare;

public class Handler : IRequestHandler<Request>
{
    private readonly IModel _chanel;

    public Handler(IModel chanel)
    {
        _chanel = chanel;
    }

    public Task Handle(Request request, CancellationToken cancellationToken)
    {
        _chanel.QueueDeclare(request.Queue,
            true,
            false,
            false,
            new Dictionary<string, object> { { "x-queue-type", "quorum" } });

        _chanel.BasicPublish(RabbitConstants.TransferRequestExchange, 
            request.RoutingKey, 
            null, 
            request.Body);
        
        return Task.CompletedTask;
    }
}