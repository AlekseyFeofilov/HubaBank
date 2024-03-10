using System.Net;
using System.Net.Http.Headers;
using System.Text.Json;
using EmployeeGateway.BL;
using EmployeeGateway.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Options;

namespace EmployeeGateway.Controllers;

[ApiController]
[Route("api/gateway/bills/")]
public class BillGatewayController: ControllerBase
{
    private readonly HttpClient _httpClient;
    private readonly ILogger<AuthGatewayController> _logger;
    private readonly UrlsMicroserviceOptions _urlsMicroservice;
    
    public BillGatewayController(IHttpClientFactory httpClientFactory, ILogger<AuthGatewayController> logger, IOptions<UrlsMicroserviceOptions> urlsMicroserviceOptions)
    {
        _httpClient = httpClientFactory.CreateClient();
        _logger = logger;
        _urlsMicroservice = urlsMicroserviceOptions.Value;
    }
    
    [HttpGet("{userId}")]
    public async Task<ActionResult<List<ClientBill>>> GetBills(Guid userId)
    {
        var serializeOptions = new JsonSerializerOptions
        {
            PropertyNamingPolicy = JsonNamingPolicy.CamelCase,
            WriteIndented = true
        };
        var url = _urlsMicroservice.CoreUrl + $"/users/{userId}/bills";
        var message = new HttpRequestMessage(new HttpMethod(Request.Method), url);
        message.Headers.Authorization = new AuthenticationHeaderValue(
            "Bearer", Request.Headers.Authorization.First().Substring(6)
        );
        var response = await _httpClient.SendAsync(message);
        
        if (response.IsSuccessStatusCode)
        {
            var downstreamResponse = await response.Content.ReadAsStringAsync();
            var models = JsonSerializer.Deserialize<List<ClientBill>>(downstreamResponse, serializeOptions);
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
    
    [HttpGet("{userId}/bill/{billId}")]
    public async Task<ActionResult<ClientBill>> GetBill(Guid userId, Guid billId)
    {
        var serializeOptions = new JsonSerializerOptions
        {
            PropertyNamingPolicy = JsonNamingPolicy.CamelCase,
            WriteIndented = true
        };
        var requestData = new
        {
            GuidParameter1 = userId,
            GuidParameter2 = billId.ToString()
        };
        var url = _urlsMicroservice.CoreUrl + $"/users/{userId}/bills/{billId}";
        var message = new HttpRequestMessage(new HttpMethod(Request.Method), url);
        message.Headers.Authorization = new AuthenticationHeaderValue(
            "Bearer", Request.Headers.Authorization.First().Substring(6)
        );
        var response = await _httpClient.SendAsync(message);
        
        if (response.IsSuccessStatusCode)
        {
            var downstreamResponse = await response.Content.ReadAsStringAsync();
            var models = JsonSerializer.Deserialize<ClientBill>(downstreamResponse, serializeOptions);
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
    
    [HttpGet("{userId}/bill/{billId}/transactions")]
    public async Task<ActionResult<List<Transaction>>> GetTransactions(Guid userId, Guid billId)
    {
        var serializeOptions = new JsonSerializerOptions
        {
            PropertyNamingPolicy = JsonNamingPolicy.CamelCase,
            WriteIndented = true
        };
        var url = _urlsMicroservice.CoreUrl + $"/users/{userId}/bills/{billId}/transactions";
        var message = new HttpRequestMessage(new HttpMethod(Request.Method), url);
        message.Headers.Authorization = new AuthenticationHeaderValue(
            "Bearer", Request.Headers.Authorization.First().Substring(6)
        );
        var response = await _httpClient.SendAsync(message);
        
        if (response.IsSuccessStatusCode)
        {
            var downstreamResponse = await response.Content.ReadAsStringAsync();
            var models = JsonSerializer.Deserialize<List<Transaction>>(downstreamResponse, serializeOptions);
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
}