using Credit.Dal;
using MediatR;

namespace Credit.Lib.Feature.Base.Delete;

public sealed class Handler<TDal, TData> : IRequestHandler<Request<TDal, TData>, TData?>
    where TDal : class
{
    private readonly CreditContext _context;

    public Handler(CreditContext context)
    {
        _context = context;
    }

    public async Task<TData?> Handle(Request<TDal, TData> request, CancellationToken cancellationToken)
    {
        var entities = _context.Set<TDal>()
            .Where(request.Specification);

        var debug = entities.ToList();

        _context.Set<TDal>().RemoveRange(entities);
        await _context.SaveChangesAsync(cancellationToken);
        return default;
    }
}