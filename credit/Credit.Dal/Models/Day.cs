namespace Credit.Dal.Models;

public class Day : IIdentity<Guid>
{
    public Guid Id { get; set; }
    public DateTime Now { get; set; }
}