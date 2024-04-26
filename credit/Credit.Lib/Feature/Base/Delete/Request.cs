using EntityFrameworkCore.CommonTools;
using MediatR;

namespace Credit.Lib.Feature.Base.Delete;

public class Request<TDal, TData> : IRequest<TData?>
    where TDal : class 
{
    public Specification<TDal> Specification { get; }

    protected Request(Specification<TDal> specification)
    {
        Specification = specification;
    }
}