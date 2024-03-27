using AutoMapper;
using Credit.Dal;
using MediatR;
using Microsoft.EntityFrameworkCore;

namespace Credit.Lib.Feature.Base.Update;

public class Handler<TDal, TData> : IRequestHandler<Request<TDal, TData>, TData?>
    where TDal : class
{
    private readonly CreditContext _context;
    private readonly IMapper _mapper;

    public Handler(CreditContext context, IMapper mapper)
    {
        _context = context;
        _mapper = mapper;
    }

    public async Task<TData?> Handle(Request<TDal, TData> request, CancellationToken cancellationToken)
    {
        var entity = await _context.Set<TDal>()
            .Where(request.Specification)
            .FirstOrDefaultAsync(cancellationToken);

        if (entity == null)
        {
            return default;
        }
        
        request.Expression(entity);
        await _context.SaveChangesAsync(cancellationToken);
        return _mapper.Map<TData>(entity);
    }
}