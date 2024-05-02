using System.Net.Http.Headers;
using EmployeeGateway.BL;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Options;
using EmployeeGateway.BL.Services;
using EmployeeGateway.Common.System;
using EmployeeGateway.Models;

namespace EmployeeGateway.Controllers;

[ApiController]
[Route("api/gateway/profile")]
public class ProfileGatewayController: ControllerBase
{
    private readonly HttpClient _httpClient;
    private readonly UrlsMicroserviceOptions _urlsMicroservice;
    
    public ProfileGatewayController(IHttpClientFactory httpClientFactory, IOptions<UrlsMicroserviceOptions> urlsMicroserviceOptions)
    {
        _httpClient = httpClientFactory.CreateClient();
        _urlsMicroservice = urlsMicroserviceOptions.Value;
    }
    
    [HttpGet("employees")]
    public async Task<ActionResult<List<UserFill>>> GetEmployees()
    {
        var authHeader = Request.Headers.Authorization.FirstOrDefault();
        if (authHeader == null)
        {
            return Unauthorized();
        }
        var url = _urlsMicroservice.AuthUrl + "/users/api/v1/employees";
        var message = new HttpRequestMessage(new HttpMethod(Request.Method), url);
        message.Headers.Authorization = new AuthenticationHeaderValue(
            "Bearer", authHeader[6..]
        );
        var response = await _httpClient.SendAsync(message);
        
        return await this.GetResultFromResponse<List<UserFill>>(response);
    }
    
    [HttpGet("clients")]
    public async Task<ActionResult<List<UserFill>>> GetClients()
    {
        var authHeader = Request.Headers.Authorization.FirstOrDefault();
        if (authHeader == null)
        {
            return Unauthorized();
        }
        var url = _urlsMicroservice.AuthUrl + "/users/api/v1/users";
        var message = new HttpRequestMessage(new HttpMethod(Request.Method), url);
        message.Headers.Authorization = new AuthenticationHeaderValue(
            "Bearer", authHeader[6..]
        );
        var response = await _httpClient.SendAsync(message);
        
        return await this.GetResultFromResponse<List<UserFill>>(response);
    }
    
    [HttpGet("my")]
    public async Task<ActionResult<UserFill>> GetProfile()
    {
        var authHeader = Request.Headers.Authorization.FirstOrDefault();
        if (authHeader == null)
        {
            return Unauthorized();
        }
        var url = _urlsMicroservice.AuthUrl + "/users/api/v1/users/my";
        var message = new HttpRequestMessage(new HttpMethod(Request.Method), url);
        message.Headers.Authorization = new AuthenticationHeaderValue(
            "Bearer", authHeader[6..]
        );
        var response = await _httpClient.SendAsync(message);
        
        return await this.GetResultFromResponse<UserFill>(response);
    }
    
    [HttpGet("{userId}")]
    public async Task<ActionResult<UserFill>> GetUser(Guid userId)
    {
        var authHeader = Request.Headers.Authorization.FirstOrDefault();
        if (authHeader == null)
        {
            return Unauthorized();
        }
  
        var url = _urlsMicroservice.AuthUrl + $"/users/api/v1/user/{userId}";
        var message = new HttpRequestMessage(new HttpMethod(Request.Method), url);
        message.Headers.Authorization = new AuthenticationHeaderValue(
            "Bearer", authHeader[6..]
        );
        var response = await _httpClient.SendAsync(message);
        
        return await this.GetResultFromResponse<UserFill>(response);
    }
    
    [HttpPost("{userId}/block")]
    public async Task<IActionResult> BlockUser(Guid userId)
    {
        var authHeader = Request.Headers.Authorization.FirstOrDefault();
        if (authHeader == null)
        {
            return Unauthorized();
        }

        var url = _urlsMicroservice.AuthUrl + $"/users/api/v1/user/{userId}/block";
        var message = new HttpRequestMessage(new HttpMethod(Request.Method), url);
        message.Headers.Authorization = new AuthenticationHeaderValue(
            "Bearer", authHeader[6..]
        );
        var response = await _httpClient.SendAsync(message);
        
        return await this.GetResult(response);
    }
    
    [HttpPost("{userId}/unblock")]
    public async Task<IActionResult> UnblockUser(Guid userId)
    {
        var authHeader = Request.Headers.Authorization.FirstOrDefault();
        if (authHeader == null)
        {
            return Unauthorized();
        }
        var url = _urlsMicroservice.AuthUrl + $"/users/api/v1/user/{userId}/unblock";
        var message = new HttpRequestMessage(new HttpMethod(Request.Method), url);
        message.Headers.Authorization = new AuthenticationHeaderValue(
            "Bearer", authHeader[6..]
        );
        var response = await _httpClient.SendAsync(message);
        
        return await this.GetResult(response);
    }
}