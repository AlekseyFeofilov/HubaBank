using MediatR;

namespace Credit.Lib.Feature.Rabbit.QueueDeclare;

public class Request : IRequest
{
    public required string Queue { get; init; }
    public required string RoutingKey { get; init; }
    public required byte[] Body { get; set; } 
}