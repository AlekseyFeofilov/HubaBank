using Credit.Dal.Models;
using Microsoft.EntityFrameworkCore;

namespace Credit.Dal;

public class CreditContext : DbContext
{
    public DbSet<Models.Credit> Credits { get; set; }
    public DbSet<CreditTerms> CreditTerms { get; set; }
    public DbSet<Payment> Payments { get; set; }

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

        modelBuilder.Entity<Models.Credit>(typeBuilder =>
        {
            typeBuilder.HasKey(x => x.Id);

            typeBuilder
                .HasOne(x => x.CreditTerms)
                .WithMany()
                .HasForeignKey(x => x.CreditTermsId)
                .OnDelete(DeleteBehavior.Restrict);
        });

        modelBuilder.Entity<CreditTerms>(typeBuilder =>
        {
            typeBuilder.HasKey(x => x.Id);
        });
        
        modelBuilder.Entity<Payment>(typeBuilder =>
        {
            typeBuilder.HasKey(x => x.Id);

            typeBuilder
                .HasOne(x => x.Credit)
                .WithMany()
                .HasForeignKey(x => x.CreditId)
                .OnDelete(DeleteBehavior.Restrict);
        });
    }
}