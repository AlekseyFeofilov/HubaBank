using System.Net;
using System.Net.Http.Headers;
using System.Text;
using System.Text.Json;
using EmployeeGateway.BL;
using EmployeeGateway.BL.Services;
using EmployeeGateway.Common.System;
using EmployeeGateway.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Options;

namespace EmployeeGateway.Controllers;

[ApiController]
[Route("api/gateway/roles/")]
public class RolesGatewayController : ControllerBase
{
    private readonly HttpClient _httpClient;
    private readonly ILogger<AuthGatewayController> _logger;
    private readonly UrlsMicroserviceOptions _urlsMicroservice;

    public RolesGatewayController(IHttpClientFactory httpClientFactory, ILogger<AuthGatewayController> logger,
        IOptions<UrlsMicroserviceOptions> urlsMicroserviceOptions)
    {
        _httpClient = httpClientFactory.CreateClient();
        _logger = logger;
        _urlsMicroservice = urlsMicroserviceOptions.Value;
    }

    [HttpPost("{userId:guid}")]
    public async Task<IActionResult> SetRoles(Guid userId, Roles roles)
    {
        var authHeader = Request.Headers.Authorization.FirstOrDefault();
        if (authHeader == null)
        {
            return Unauthorized();
        }
        var serializeOptions = new JsonSerializerOptions
        {
            PropertyNamingPolicy = JsonNamingPolicy.CamelCase,
            WriteIndented = true
        };
        var currentUserId = UtilsService.GetUserIdByHeader(authHeader);
        if (currentUserId == null)
        {
            return Unauthorized();
        }

        var currentRoles = await GetRoles(new Guid(currentUserId));
        roles.Names.AddRange(currentRoles);
        
        var json = JsonSerializer.Serialize(roles, serializeOptions);
        var content = new StringContent(json, Encoding.UTF8, "application/json");
        var url = _urlsMicroservice.AuthUrl + $"/users/api/v1/user/{userId}/roles";
        var message = new HttpRequestMessage(HttpMethod.Post, url)
        {
            Content = content
        };
        message.Headers.Authorization = new AuthenticationHeaderValue(
            "Bearer", authHeader[6..]
        );
        var response = await _httpClient.SendAsync(message);

        return await this.GetResult(response);
    }
    
    [HttpDelete("{userId:guid}/{roleName}")]
    public async Task<IActionResult> DeleteRole(Guid userId, string roleName)
    {
        var authHeader = Request.Headers.Authorization.FirstOrDefault();
        if (authHeader == null)
        {
            return Unauthorized();
        }
        var serializeOptions = new JsonSerializerOptions
        {
            PropertyNamingPolicy = JsonNamingPolicy.CamelCase,
            WriteIndented = true
        };
        
        var currentRoles = await GetRoles(userId);
        currentRoles.Remove(roleName);
        
        var json = JsonSerializer.Serialize(new Roles
        {
            Names = currentRoles
        }, serializeOptions);
        var content = new StringContent(json, Encoding.UTF8, "application/json");
        var url = _urlsMicroservice.AuthUrl + $"/users/api/v1/user/{userId}/roles";
        var message = new HttpRequestMessage(HttpMethod.Post, url)
        {
            Content = content
        };
        message.Headers.Authorization = new AuthenticationHeaderValue(
            "Bearer", authHeader[6..]
        );
        var response = await _httpClient.SendAsync(message);

        return await this.GetResult(response);
    }
    
    private async Task<List<string>> GetRoles(Guid userId)
    {
        var serializeOptions = new JsonSerializerOptions
        {
            PropertyNamingPolicy = JsonNamingPolicy.CamelCase,
            WriteIndented = true
        };
        
        var authHeader = Request.Headers.Authorization.FirstOrDefault();
        if (authHeader == null)
        {
            throw new UnauthorizedAccessException();
        }
  
        var url = _urlsMicroservice.AuthUrl + $"/users/api/v1/user/{userId}";
        var message = new HttpRequestMessage(HttpMethod.Get, url);
        message.Headers.Authorization = new AuthenticationHeaderValue(
            "Bearer", authHeader[6..]
        );
        var response = await _httpClient.SendAsync(message);
        
        if (response.IsSuccessStatusCode)
        {
            var downstreamResponse = await response.Content.ReadAsStringAsync();
            var body = JsonSerializer.Deserialize<UserFill>(downstreamResponse, serializeOptions);
            if (body == null)
                throw new InvalidCastException("Не вышло выполнить серилизацию данных");
            
            return body.Roles;
        }
        else
        {
            throw new BadHttpRequestException("Не удалось выполнить получение профиля пользователя для проверки возможностей");
        }
    }
}