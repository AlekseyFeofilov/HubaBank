using EntityFrameworkCore.CommonTools;
using MediatR;

namespace Credit.Lib.Feature.Base.Update;

public class Request<TDal, TData> : IRequest<TData>
    where TDal : class
{
    public Specification<TDal>? Specification { get; }
    public required Action<TDal> Expression { get; init; }

    protected Request(Specification<TDal>? specification)
    {
        Specification = specification;
    }
}