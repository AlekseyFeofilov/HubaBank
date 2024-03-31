using MediatR;

namespace Credit.Lib.A;

public class Handler : IRequestHandler<Request>
{
    public Task Handle(Request request, CancellationToken cancellationToken)
    {
        throw new NotImplementedException();
    }
}