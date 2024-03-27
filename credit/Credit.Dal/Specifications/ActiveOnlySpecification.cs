using Credit.Dal.Models;
using EntityFrameworkCore.CommonTools;

namespace Credit.Dal.Specifications;

public class ActiveOnlySpecification<T> : Specification<T>
    where T: IDeletable
{
    public ActiveOnlySpecification() : base(x => !x.IsDeleted)
    {
    }
}