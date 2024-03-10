using AutoMapper;
using AutoMapper.QueryableExtensions;
using Credit.Dal;
using MediatR;
using Microsoft.EntityFrameworkCore;

namespace Credit.Lib.Feature.Base.Fetch;

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

    public Task<TData?> Handle(Request<TDal, TData> request, CancellationToken cancellationToken)
    {
        return _context.Set<TDal>()
            .Where(request.Specification)
            .ProjectTo<TData>(_mapper.ConfigurationProvider)
            .FirstOrDefaultAsync(cancellationToken);
    }
}