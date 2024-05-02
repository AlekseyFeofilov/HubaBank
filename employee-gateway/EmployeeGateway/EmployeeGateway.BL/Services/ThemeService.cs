using EmployeeGateway.Common.DTO;
using EmployeeGateway.Common.Exceptions;
using EmployeeGateway.Common.ServicesInterface;
using EmployeeGateway.DAL;
using EmployeeGateway.DAL.Entity;
using Microsoft.EntityFrameworkCore;

namespace EmployeeGateway.BL.Services;

public class ThemeService: IThemeService
{
    private readonly AppDbContext _appDbContext;

    public ThemeService(AppDbContext appDbContext)
    {
        _appDbContext = appDbContext;
    }

    public async Task<ThemeSystem> GetTheme(Guid userId)
    {
        var theme = await _appDbContext.Themes.Where(theme => theme.UserId == userId).FirstOrDefaultAsync();
        if (theme != null) 
            return theme.ThemeSystem;
        
        var newThemeEntity = new ThemeEntity
        {
            UserId = userId,
            ThemeSystem = ThemeSystem.Light
        };
        _appDbContext.Add(newThemeEntity);
        await _appDbContext.SaveChangesAsync();
        
        return newThemeEntity.ThemeSystem;
    }

    public async Task ChangeTheme(Guid userId, ThemeSystem themeSystem)
    {
        var theme = await _appDbContext.Themes.Where(theme => theme.UserId == userId).FirstOrDefaultAsync();
        if (theme == null)
        {
            var newThemeEntity = new ThemeEntity
            {
                UserId = userId,
                ThemeSystem = themeSystem
            };
            _appDbContext.Add(newThemeEntity);
        }
        else
        {
            theme.ThemeSystem = themeSystem;
            _appDbContext.Themes.Attach(theme);
        }
        
        await _appDbContext.SaveChangesAsync();
    }
}