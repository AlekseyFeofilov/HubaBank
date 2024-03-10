using Credit.Dal.Models;
using EntityFrameworkCore.CommonTools;

namespace Credit.Dal.Specifications;

public class IdentitySpecification<T, TKey> : Specification<T> 
    where T : IIdentity<TKey> 
    where TKey : struct
{
    public IdentitySpecification(TKey key) : base(x => Equals(x.Id, key))
    {
    }

    public IdentitySpecification(IEnumerable<TKey>? keys)
    {
        if (keys == null)
        {
            Predicate = _ => true;
            return;
        }

        var keysCollection = keys as IReadOnlyList<TKey> ?? keys.ToArray();

        if (keysCollection.Count == 1)
        {
            var key = keysCollection.First();
            Predicate = x => Equals(x.Id, key);
        }
        else
        {
            Predicate = x => keysCollection.Contains(x.Id);
        }
    } 
}