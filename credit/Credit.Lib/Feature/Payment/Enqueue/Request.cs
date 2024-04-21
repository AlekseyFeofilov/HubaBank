using MediatR;

namespace Credit.Lib.Feature.Payment.Enqueue;

public class Request : IRequest
{
    public Guid Id { get; set; }
    
    public Request(Guid id)
    {
        Id = id;
    }
}