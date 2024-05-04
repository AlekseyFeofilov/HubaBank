using EmployeeGateway.Common.ServicesInterface;
using EmployeeGateway.DAL;
using EmployeeGateway.DAL.Entity;

namespace EmployeeGateway.BL.Services;

public class UserService: IUserService
{
    private readonly AppDbContext _appDbContext;

    public UserService(AppDbContext appDbContext)
    {
        _appDbContext = appDbContext;
    }

    
    public async Task<string?> GetMessagingToken(Guid userId)
    {
        var user = await _appDbContext.Users.FindAsync(userId);
        return user?.MessagingToken;
    }

    public async Task SetMessagingToken(Guid userId, string token)
    {
        var user = await _appDbContext.Users.FindAsync(userId);
        if (user != null)
        {
            user.MessagingToken = token;
        }
        else
        {
            await _appDbContext.AddAsync(new UserEntity { Id = userId, MessagingToken = token });
        }

        await _appDbContext.SaveChangesAsync();
    }

}