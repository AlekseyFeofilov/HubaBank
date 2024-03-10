using System.Net;
using System.Text;
using EmployeeGateway.Models;
using Microsoft.AspNetCore.Mvc;
using System.Text.Json;
using EmployeeGateway.BL;
using Microsoft.Extensions.Options;

namespace EmployeeGateway.Controllers;

[ApiController]
[Route("api/gateway/auth/")]
public class AuthGatewayController : ControllerBase
{
    private readonly HttpClient _httpClient;
    private readonly ILogger<AuthGatewayController> _logger;
    private readonly UrlsMicroserviceOptions _urlsMicroservice;

    public AuthGatewayController(IHttpClientFactory httpClientFactory, ILogger<AuthGatewayController> logger, IOptions<UrlsMicroserviceOptions> urlsMicroserviceOptions)
    {
        _httpClient = httpClientFactory.CreateClient();
        _logger = logger;
        _urlsMicroservice = urlsMicroserviceOptions.Value;
    }

    [HttpPost("register")]
    public async Task<IActionResult> Register([FromBody] RegisterCredential requestBody)
    {
        var serializeOptions = new JsonSerializerOptions
        {
            PropertyNamingPolicy = JsonNamingPolicy.CamelCase,
            WriteIndented = true
        };
        var json = JsonSerializer.Serialize(requestBody, serializeOptions);
        var content = new StringContent(json, Encoding.UTF8, "application/json");
        var url = _urlsMicroservice.AuthUrl + "/users/api/v1/register";
        var response = await _httpClient.PostAsync(url, content);
        
        if (response.IsSuccessStatusCode)
        {
            var downstreamResponse = await response.Content.ReadAsStringAsync();
            var model = JsonSerializer.Deserialize<TokenPairs>(downstreamResponse);
            return Ok(model);
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
    
    [HttpPost("login")]
    public async Task<IActionResult> Auth([FromBody] LoginCredentials requestBody)
    {
        const string downstreamUrl = "http://194.147.90.192:9003/users/api/v1/login";

        var serializeOptions = new JsonSerializerOptions
        {
            PropertyNamingPolicy = JsonNamingPolicy.CamelCase,
            WriteIndented = true
        };
        var json = JsonSerializer.Serialize(requestBody, serializeOptions);
        var content = new StringContent(json, Encoding.UTF8, "application/json");
        var url = _urlsMicroservice.AuthUrl + "/login";
    
        try
        {
            var response = await _httpClient.PostAsync(downstreamUrl, content);
        
            if (response.IsSuccessStatusCode)
            {
                var downstreamResponse = await response.Content.ReadAsStringAsync();
                var model = JsonSerializer.Deserialize<TokenPairs>(downstreamResponse);
                return Ok(model);
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
                    _logger.LogError($"Error in Auth endpoint: {response.StatusCode}");
                    var errorResponse = await response.Content.ReadAsStringAsync();
                    _logger.LogError($"Error details: {errorResponse}");
                
                    return StatusCode((int)response.StatusCode);
                }
            }
        }
        catch (Exception ex)
        {
            _logger.LogError($"Exception in Auth endpoint: {ex.Message}");
            return StatusCode(500, "Internal Server Error");
        }
    }
    
}