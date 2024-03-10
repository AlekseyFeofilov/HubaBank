using System.Net;
using System.Net.Http.Headers;
using System.Text;
using System.Text.Json;
using EmployeeGateway.BL;
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

    [HttpPost("setClient/{userId}")]
    public async Task<IActionResult> SetClientRole(Guid userId, Roles roles)
    {
        var serializeOptions = new JsonSerializerOptions
        {
            PropertyNamingPolicy = JsonNamingPolicy.CamelCase,
            WriteIndented = true
        };
        var requestData = new
        {
            GuidParameter1 = userId,
            GuidParameter2 = roles
        };
        var json = JsonSerializer.Serialize(roles, serializeOptions);
        var content = new StringContent(json, Encoding.UTF8, "application/json");
        var url = _urlsMicroservice.AuthUrl + $"/users/api/v1/user/{userId}/roles";
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

    [HttpPost("setEmployee/{userId}")]
    public async Task<IActionResult> SetEmployeeRole(Guid userId)
    {
        var serializeOptions = new JsonSerializerOptions
        {
            PropertyNamingPolicy = JsonNamingPolicy.CamelCase,
            WriteIndented = true
        };
        var json = JsonSerializer.Serialize(userId, serializeOptions);
        var content = new StringContent(json, Encoding.UTF8, "application/json");
        var url = _urlsMicroservice.AuthUrl + $"/users/api/v1/setEmployeeUser/{userId}";
        var response = await _httpClient.PostAsync(url, content);

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