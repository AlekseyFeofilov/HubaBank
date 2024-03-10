namespace Credit.Dal.Models;

public class Credit : IIdentity<Guid>
{
    public Guid Id { get; }
    public Guid AccountId { get; set; }
}