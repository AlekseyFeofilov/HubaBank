using AutoMapper;
using AutoMapper.QueryableExtensions;
using Credit.Dal;
using Credit.Lib.Extensions;
using MediatR;
using Microsoft.EntityFrameworkCore;

namespace Credit.Lib.Feature.Base.FetchAll;

public class Handler<TDal, TData> : IRequestHandler<Request<TDal, TData>, IReadOnlyCollection<TData>>
    where TDal : class
{
    private readonly CreditContext _context;
    private readonly IMapper _mapper;

    public Handler(CreditContext context, IMapper mapper)
    {
        _context = context;
        _mapper = mapper;
    }

    public async Task<IReadOnlyCollection<TData>> Handle(Request<TDal, TData> request, CancellationToken cancellationToken)
    {
        return await _context.Set<TDal>()
            .Where(request.Specification)
            .Skip(request.PageFilter.Offset)
            .TakeIf(request.PageFilter.Size)
            .ProjectTo<TData>(_mapper.ConfigurationProvider)
            .ToListAsync(cancellationToken);
    }
}