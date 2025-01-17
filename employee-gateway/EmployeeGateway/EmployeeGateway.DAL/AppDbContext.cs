using EmployeeGateway.DAL.Entity;
using Microsoft.EntityFrameworkCore;

namespace EmployeeGateway.DAL;

public class AppDbContext: DbContext
{
    public DbSet<ThemeEntity> Themes { get; set; }
    public DbSet<UserEntity> Users { get; set; }
    public DbSet<CircuitBreakerEntity> CircuitBreakers { get; set; }
    
    public AppDbContext(DbContextOptions<AppDbContext> options) : base(options) { }

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        base.OnModelCreating(modelBuilder);
    }
}