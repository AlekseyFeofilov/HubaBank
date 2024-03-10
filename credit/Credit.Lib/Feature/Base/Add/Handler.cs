using AutoMapper;
using Credit.Dal;
using MediatR;

namespace Credit.Lib.Feature.Base.Add;

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
        var mappedEntities = _mapper.Map<IReadOnlyCollection<TDal>>(request.Entities);

        foreach (var entity in mappedEntities)
        {
            await _context.AddAsync(entity, cancellationToken);
        }

        await _context.SaveChangesAsync(cancellationToken);
        return _mapper.Map<IReadOnlyCollection<TData>>(mappedEntities);
    }
}