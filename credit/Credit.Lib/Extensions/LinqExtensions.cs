namespace Credit.Lib.Extensions;

public static class LinqExtensions
{
    public static IQueryable<TSource> TakeIf<TSource>(this IQueryable<TSource> source, int count)
    {
        return count > 0 ? source.Take(count) : source;
    }
    
    public static IEnumerable<TSource> TakeIf<TSource>(this IEnumerable<TSource> source, int count)
    {
        return count > 0 ? source.Take(count) : source;
    }
}