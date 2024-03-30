using MediatR;

namespace Credit.Lib.Feature.Payment.Handle;

public class Request : IRequest
{
    public Guid Id { get; set; }
    
    public Request(Guid id)
    {
        Id = id;
    }
}