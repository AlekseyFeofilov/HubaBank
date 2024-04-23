using Credit.Dal;
using MediatR;

namespace Credit.Lib.Feature.Base.Delete;

public sealed class Handler<TDal> : IRequestHandler<Request<TDal>>
    where TDal : class
{
    private readonly CreditContext _context;

    public Handler(CreditContext context)
    {
        _context = context;
    }

    public Task Handle(Request<TDal> request, CancellationToken cancellationToken)
    {
        var entities = _context.Set<TDal>()
            .Where(request.Specification);

        _context.Set<TDal>().RemoveRange(entities);
        return _context.SaveChangesAsync(cancellationToken);
    }
}