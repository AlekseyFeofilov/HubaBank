
using BFF_client.Api.Database;
using BFF_client.Api.Database.Entities;

namespace BFF_client.Api.Services.User
{
    public class UserService : IUserService
    {
        private readonly AppDbContext _dbContext;

        public UserService(AppDbContext dbContext)
        {
            _dbContext = dbContext;
        }

        public async Task<bool> GetIsDarkTheme(Guid userId)
        {
            var user = await _dbContext.Users.FindAsync(userId);
            return user?.IsDarkTheme ?? false;
        }

        public async Task SetIsDarkTheme(Guid userId, bool isDarkTheme)
        {
            var user = await _dbContext.Users.FindAsync(userId);
            if (user != null)
            {
                user.IsDarkTheme = isDarkTheme;
            }
            else
            {
                await _dbContext.AddAsync(new UserEntity { Id = userId, IsDarkTheme = isDarkTheme });
            }

            await _dbContext.SaveChangesAsync();
        }

        public async Task<string?> GetMessagingToken(Guid userId)
        {
            var user = await _dbContext.Users.FindAsync(userId);
            return user?.MessagingToken;
        }

        public async Task SetMessagingToken(Guid userId, string? token)
        {
            var user = await _dbContext.Users.FindAsync(userId);
            if (user != null)
            {
                user.MessagingToken = token;
            }
            else
            {
                await _dbContext.AddAsync(new UserEntity { Id = userId, MessagingToken = token });
            }

            await _dbContext.SaveChangesAsync();
        }
    }
}
