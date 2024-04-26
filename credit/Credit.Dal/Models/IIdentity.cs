namespace Credit.Dal.Models;

public interface IIdentity<TKey>
{
    TKey Id { get; set; }
}