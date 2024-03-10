using Microsoft.EntityFrameworkCore;

namespace Credit.Dal;

public class CreditContext : DbContext
{
    public DbSet<Models.Credit> Credits { get; set; }

#pragma warning disable CS8618
    public CreditContext(DbContextOptions<CreditContext> options) : base(options)
#pragma warning restore CS8618
    {
        AppContext.SetSwitch("Npgsql.EnableLegacyTimestampBehavior", true);
    }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        base.OnModelCreating(modelBuilder);
        modelBuilder.HasDefaultSchema("credit_db");//todo rename

        modelBuilder.Entity<Models.Credit>()
            .HasKey(x => x.Id);
    }
}