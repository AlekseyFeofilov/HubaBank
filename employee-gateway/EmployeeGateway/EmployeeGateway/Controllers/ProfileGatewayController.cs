using System.Net;
using System.Net.Http.Headers;
using System.Text;
using EmployeeGateway.BL;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Options;
using System.Text.Json;
using EmployeeGateway.Models;
using Microsoft.AspNetCore.Authorization;

namespace EmployeeGateway.Controllers;

[ApiController]
[Route("api/gateway/profile")]
public class ProfileGatewayController: ControllerBase
{
    private readonly HttpClient _httpClient;
    private readonly ILogger<AuthGatewayController> _logger;
    private readonly UrlsMicroserviceOptions _urlsMicroservice;
    
    public ProfileGatewayController(IHttpClientFactory httpClientFactory, ILogger<AuthGatewayController> logger, IOptions<UrlsMicroserviceOptions> urlsMicroserviceOptions)
    {
        _httpClient = httpClientFactory.CreateClient();
        _logger = logger;
        _urlsMicroservice = urlsMicroserviceOptions.Value;
    }
    
    [HttpGet("employees")]
    public async Task<ActionResult<List<UserFill>>> GetEmployees()
    {
        var url = _urlsMicroservice.AuthUrl + "/users/api/v1/employees";
        var message = new HttpRequestMessage(new HttpMethod(Request.Method), url);
        message.Headers.Authorization = new AuthenticationHeaderValue(
            "Bearer", Request.Headers.Authorization.First().Substring(6)
        );
        var response = await _httpClient.SendAsync(message);
        
        if (response.IsSuccessStatusCode)
        {
            var serializeOptions = new JsonSerializerOptions
            {
                PropertyNamingPolicy = JsonNamingPolicy.CamelCase,
                WriteIndented = true
            };
            var downstreamResponse = await response.Content.ReadAsStringAsync();
            var models = JsonSerializer.Deserialize<List<UserFill>>(downstreamResponse, serializeOptions);
            return Ok(models);
        }
        else
        {
            if (response.StatusCode == HttpStatusCode.BadRequest)
            {
                var errorResponse = await response.Content.ReadAsStringAsync();
                return BadRequest(errorResponse);
            }
            else if (response.StatusCode == HttpStatusCode.NotFound)
            {
                return NotFound();
            }
            else
            {
                return StatusCode((int)response.StatusCode);
            }
        }
    }
    
    [HttpGet("clients")]
    public async Task<ActionResult<List<UserFill>>> GetClients()
    {
        var url = _urlsMicroservice.AuthUrl + "/users/api/v1/users";
        var message = new HttpRequestMessage(new HttpMethod(Request.Method), url);
        message.Headers.Authorization = new AuthenticationHeaderValue(
            "Bearer", Request.Headers.Authorization.First().Substring(6)
        );
        var response = await _httpClient.SendAsync(message);
        
        if (response.IsSuccessStatusCode)
        {
            var serializeOptions = new JsonSerializerOptions
            {
                PropertyNamingPolicy = JsonNamingPolicy.CamelCase,
                WriteIndented = true
            };
            var downstreamResponse = await response.Content.ReadAsStringAsync();
            var models = JsonSerializer.Deserialize<List<UserFill>>(downstreamResponse, serializeOptions);
            if (models != null)
            {
                var result = models.Where(e => e.employee == false).ToList();
                return Ok(result);
            }
            else
            {
                return BadRequest("Не удалось выполнить преобразование");
            }
        }
        else
        {
            if (response.StatusCode == HttpStatusCode.BadRequest)
            {
                var errorResponse = await response.Content.ReadAsStringAsync();
                return BadRequest(errorResponse);
            }
            else if (response.StatusCode == HttpStatusCode.NotFound)
            {
                return NotFound();
            }
            else
            {
                return StatusCode((int)response.StatusCode);
            }
        }
    }
    
    [HttpGet("my")]
    public async Task<ActionResult<UserFill>> GetProfile()
    {
        var url = _urlsMicroservice.AuthUrl + "/users/api/v1/users/my";
        var message = new HttpRequestMessage(new HttpMethod(Request.Method), url);
        message.Headers.Authorization = new AuthenticationHeaderValue(
            "Bearer", Request.Headers.Authorization.First().Substring(6)
        );
        var response = await _httpClient.SendAsync(message);
        
        if (response.IsSuccessStatusCode)
        {
            var serializeOptions = new JsonSerializerOptions
            {
                PropertyNamingPolicy = JsonNamingPolicy.CamelCase,
                WriteIndented = true
            };
            var downstreamResponse = await response.Content.ReadAsStringAsync();
            var models = JsonSerializer.Deserialize<UserFill>(downstreamResponse, serializeOptions);
            return Ok(models);
        }
        else
        {
            if (response.StatusCode == HttpStatusCode.BadRequest)
            {
                var errorResponse = await response.Content.ReadAsStringAsync();
                return BadRequest(errorResponse);
            }
            else if (response.StatusCode == HttpStatusCode.NotFound)
            {
                return NotFound();
            }
            else
            {
                return StatusCode((int)response.StatusCode);
            }
        }
    }
    
    [HttpGet("{userId}")]
    public async Task<ActionResult<UserFill>> GetUser(Guid userId)
    {
        var serializeOptions = new JsonSerializerOptions
        {
            PropertyNamingPolicy = JsonNamingPolicy.CamelCase,
            WriteIndented = true
        };
        var json = JsonSerializer.Serialize(userId, serializeOptions);
        var content = new StringContent(json, Encoding.UTF8, "application/json");
        var url = _urlsMicroservice.AuthUrl + $"/users/api/v1/user/{userId}";
        var message = new HttpRequestMessage(new HttpMethod(Request.Method), url)
        {
            Content = content
        };
        message.Headers.Authorization = new AuthenticationHeaderValue(
            "Bearer", Request.Headers.Authorization.First().Substring(6)
        );
        var response = await _httpClient.SendAsync(message);
        
        if (response.IsSuccessStatusCode)
        {
            var downstreamResponse = await response.Content.ReadAsStringAsync();
            var models = JsonSerializer.Deserialize<UserFill>(downstreamResponse, serializeOptions);
            return Ok(models);
        }
        else
        {
            if (response.StatusCode == HttpStatusCode.BadRequest)
            {
                var errorResponse = await response.Content.ReadAsStringAsync();
                return BadRequest(errorResponse);
            }
            else if (response.StatusCode == HttpStatusCode.NotFound)
            {
                return NotFound();
            }
            else
            {
                return StatusCode((int)response.StatusCode);
            }
        }
    }
    
    [HttpPost("{userId}/block")]
    public async Task<IActionResult> BlockUser(Guid userId)
    {
        var serializeOptions = new JsonSerializerOptions
        {
            PropertyNamingPolicy = JsonNamingPolicy.CamelCase,
            WriteIndented = true
        };
        var json = JsonSerializer.Serialize(userId, serializeOptions);
        var content = new StringContent(json, Encoding.UTF8, "application/json");
        var url = _urlsMicroservice.AuthUrl + $"/users/api/v1/user/{userId}/block";
        var message = new HttpRequestMessage(new HttpMethod(Request.Method), url)
        {
            Content = content
        };
        message.Headers.Authorization = new AuthenticationHeaderValue(
            "Bearer", Request.Headers.Authorization.First().Substring(6)
        );
        var response = await _httpClient.SendAsync(message);
        
        if (response.IsSuccessStatusCode)
        {
            var downstreamResponse = await response.Content.ReadAsStringAsync();
            return Ok(downstreamResponse);
        }
        else
        {
            if (response.StatusCode == HttpStatusCode.BadRequest)
            {
                var errorResponse = await response.Content.ReadAsStringAsync();
                return BadRequest(errorResponse);
            }
            else if (response.StatusCode == HttpStatusCode.NotFound)
            {
                return NotFound();
            }
            else
            {
                return StatusCode((int)response.StatusCode);
            }
        }
    }
    
    [HttpPost("{userId}/unblock")]
    public async Task<IActionResult> UnblockUser(Guid userId)
    {
        var serializeOptions = new JsonSerializerOptions
        {
            PropertyNamingPolicy = JsonNamingPolicy.CamelCase,
            WriteIndented = true
        };
        var json = JsonSerializer.Serialize(userId, serializeOptions);
        var content = new StringContent(json, Encoding.UTF8, "application/json");
        var url = _urlsMicroservice.AuthUrl + $"/users/api/v1/user/{userId}/unblock";
        var message = new HttpRequestMessage(new HttpMethod(Request.Method), url)
        {
            Content = content
        };
        message.Headers.Authorization = new AuthenticationHeaderValue(
            "Bearer", Request.Headers.Authorization.First().Substring(6)
        );
        var response = await _httpClient.SendAsync(message);
        
        if (response.IsSuccessStatusCode)
        {
            var downstreamResponse = await response.Content.ReadAsStringAsync();
            return Ok(downstreamResponse);
        }
        else
        {
            if (response.StatusCode == HttpStatusCode.BadRequest)
            {
                var errorResponse = await response.Content.ReadAsStringAsync();
                return BadRequest(errorResponse);
            }
            else if (response.StatusCode == HttpStatusCode.NotFound)
            {
                return NotFound();
            }
            else
            {
                return StatusCode((int)response.StatusCode);
            }
        }
    }
}