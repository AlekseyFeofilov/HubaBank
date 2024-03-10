using Credit.Data;
using EntityFrameworkCore.CommonTools;
using MediatR;

namespace Credit.Lib.Feature.Base.FetchAll;

public class Request<TDal, TData> : IRequest<IReadOnlyCollection<TData>>
    where TDal : class
{
    public Specification<TDal> Specification { get; }
    public PageFilter PageFilter { get; }

    protected Request(Specification<TDal>? specification, PageFilter? pageFilter)
    {
        Specification = specification ?? new Specification<TDal>(_ => true);
        PageFilter = pageFilter ?? new PageFilter();
    }
}