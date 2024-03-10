using MediatR;

namespace Credit.Lib.Feature.Base.Add;

public class Request<TDal, TData> : IRequest<IReadOnlyCollection<TData>>
    where TDal : class
{
    public IReadOnlyCollection<TData> Entities { get; }

    protected Request(TData entity)
    {
        Entities = new[] { entity };
    }

    protected Request(IReadOnlyCollection<TData> entities)
    {
        Entities = entities;
    }
}