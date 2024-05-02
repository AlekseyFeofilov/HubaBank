using System.Text;
using EmployeeGateway.Models;
using Microsoft.AspNetCore.Mvc;
using System.Text.Json;
using EmployeeGateway.BL;
using EmployeeGateway.BL.Services;
using EmployeeGateway.Common.DTO;
using EmployeeGateway.Common.System;
using Microsoft.Extensions.Options;

namespace EmployeeGateway.Controllers;

[ApiController]
[Route("api/gateway/auth/")]
public class AuthGatewayController : ControllerBase
{
    private readonly HttpClient _httpClient;
    private readonly UrlsMicroserviceOptions _urlsMicroservice;

    public AuthGatewayController(IHttpClientFactory httpClientFactory, IOptions<UrlsMicroserviceOptions> urlsMicroserviceOptions)
    {
        _httpClient = httpClientFactory.CreateClient();
        _urlsMicroservice = urlsMicroserviceOptions.Value;
    }

    [HttpPost("register")]
    public async Task<ActionResult<TokenPairs>> Register([FromBody] RegisterCredential requestBody)
    {
        string downstreamUrl = _urlsMicroservice.AuthUrl + "register";
        var json = JsonSerializer.Serialize(requestBody, UtilsService.jsonOptions);
        var content = new StringContent(json, Encoding.UTF8, "application/json");

        var response = await _httpClient.PostAsync(downstreamUrl, content);

        var tokens = await this.GetResultFromResponse<TokenPairs>(response);

        if (!response.IsSuccessStatusCode)
        {
            return tokens;
        }

        var downstreamResponse = await response.Content.ReadAsStringAsync();
        var body = JsonSerializer.Deserialize<TokenPairs>(downstreamResponse, UtilsService.jsonOptions);
        var userId = UtilsService.GetUserIdByHeader(body.accessToken);

        string downstreamUrlRole = _urlsMicroservice.AuthUrl + "user/" + userId + "/roles";
        var clientRole = new EditUserRoleDto { names = new List<string> { "CLIENT" } };
        var jsonRole = JsonSerializer.Serialize(clientRole, UtilsService.jsonOptions);
        var contentRole = new StringContent(jsonRole, Encoding.UTF8, "application/json");

        var responseRole = await _httpClient.PostAsync(downstreamUrlRole, contentRole);

        if (responseRole.IsSuccessStatusCode)
        {
            return tokens;
        }
        return StatusCode(StatusCodes.Status500InternalServerError);
    }
    
    [HttpPost("login")]
    [Produces("application/json")]
    public async Task<ActionResult<TokenPairs>> PostLogin([FromBody] LoginCredentials credentials)
    {
        string downstreamUrl = _urlsMicroservice.AuthUrl + "jwt";

        var content = new StringContent(credentials.JwtSso, Encoding.UTF8, "application/json");

        var response = await _httpClient.PostAsync(downstreamUrl, content);

        return await this.GetResultFromResponse<TokenPairs>(response);
    }
}