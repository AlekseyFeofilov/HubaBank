using BFF_client.Api.Database.Entities;
using Microsoft.EntityFrameworkCore;

namespace BFF_client.Api.Database
{
    public class AppDbContext : DbContext
    {
        public DbSet<UserEntity> Users { get; set; }
        public DbSet<BillEntity> Bills { get; set; }

        public AppDbContext(DbContextOptions<AppDbContext> options) : base(options) { }

        protected override void OnModelCreating(ModelBuilder modelBuilder)
        {
            modelBuilder.Entity<BillEntity>().HasKey(b => new { b.UserId, b.BillId  });
        }
    }
}
