using EmployeeGateway.Common.DTO;
using EmployeeGateway.Common.ServicesInterface;
using EmployeeGateway.Common.System;
using Microsoft.AspNetCore.Mvc;

namespace EmployeeGateway.Controllers;

[ApiController]
[Route("api/gateway/themes")]
public class ThemeController: ControllerBase
{
    private readonly IThemeService _themeService;

    public ThemeController(IThemeService themeService)
    {
        _themeService = themeService;
    }
    
    [HttpGet]
    public async Task<ActionResult<ThemeSystem>> GetUserTheme()
    {
        var authHeader = Request.Headers.Authorization.FirstOrDefault();
        if (authHeader == null)
        {
            return Unauthorized();
        }
        var userId = UtilsService.GetUserIdByHeader(authHeader);
        if (userId == null)
        {
            return Unauthorized();
        }
        
        return Ok(await _themeService.GetTheme(new Guid(userId)));
    }
    
    [HttpPut]
    public async Task<IActionResult> UpdateUserThemeSystem(ThemeSystem themeSystem)
    {
        var authHeader = Request.Headers.Authorization.FirstOrDefault();
        if (authHeader == null)
        {
            return Unauthorized();
        }
        var userId = UtilsService.GetUserIdByHeader(authHeader);
        if (userId == null)
        {
            return Unauthorized();
        }

        await _themeService.ChangeTheme(new Guid(userId), themeSystem);
        return Ok();
    }
}