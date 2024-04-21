using EntityFrameworkCore.CommonTools;
using MediatR;

namespace Credit.Lib.Feature.Base.Update;

public class Request<TDal, TData> : IRequest<TData?>
    where TDal : class
{
    public Specification<TDal>? Specification { get; }
    public Action<TDal> Expression { get; protected init; }

    protected Request(Specification<TDal>? specification)
    {
        Specification = specification;
    }
}