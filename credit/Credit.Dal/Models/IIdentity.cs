namespace Credit.Dal.Models;

public interface IIdentity<out TKey>
{
    TKey Id { get; }
}