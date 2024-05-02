using EmployeeGateway.Common.DTO;

namespace EmployeeGateway.Common.ServicesInterface;

public interface IThemeService
{
    Task<ThemeSystem> GetTheme(Guid userId);
    Task ChangeTheme(Guid userId, ThemeSystem newTheme);
}