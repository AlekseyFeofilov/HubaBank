using System.Net.Http.Headers;
using System.Text.Json;
using EmployeeGateway.BL;
using EmployeeGateway.BL.Services;
using EmployeeGateway.Common.Exceptions;
using EmployeeGateway.Common.System;
using EmployeeGateway.Models;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Options;

namespace EmployeeGateway.Controllers;

[ApiController]
[Route("api/gateway/bills/")]
public class BillGatewayController: ControllerBase
{
    private readonly HttpClient _httpClient;
    private readonly UrlsMicroserviceOptions _urlsMicroservice;
    
    public BillGatewayController(IHttpClientFactory httpClientFactory, IOptions<UrlsMicroserviceOptions> urlsMicroserviceOptions)
    {
        _httpClient = httpClientFactory.CreateClient();
        _urlsMicroservice = urlsMicroserviceOptions.Value;
    }
    
    
    [HttpGet("{userId:guid}")]
    public async Task<ActionResult<List<ClientBillDto>>> GetBills(Guid userId)
    {
        var authHeader = Request.Headers.Authorization.FirstOrDefault();
        if (authHeader == null)
        {
            return Unauthorized();
        }
        
        var currentUserId = UtilsService.GetUserIdByHeader(authHeader);
        if (currentUserId == null)
        {
            return Unauthorized();
        }

        await CheckAvailableUser(new Guid(currentUserId), "BILL_READ_OTHERS");
  
        var url = _urlsMicroservice.ClientGateway + $"{userId}/bills";
        var message = new HttpRequestMessage(new HttpMethod(Request.Method), url);
        message.Headers.Authorization = new AuthenticationHeaderValue(
            "Bearer", authHeader[6..]
        );
        var response = await _httpClient.SendAsync(message);

        return await this.GetResultFromResponse<List<ClientBillDto>>(response);
    }
    
    [HttpGet("{userId:guid}/bill/{billId}")]
    public async Task<ActionResult<ClientBillDto>> GetBill(Guid userId, Guid billId)
    {
        var authHeader = Request.Headers.Authorization.FirstOrDefault();
        if (authHeader == null)
        {
            return Unauthorized();
        }
        
        var currentUserId = UtilsService.GetUserIdByHeader(authHeader);
        if (currentUserId == null)
        {
            return Unauthorized();
        }

        await CheckAvailableUser(new Guid(currentUserId), "BILL_READ_OTHERS");
  
        var url = _urlsMicroservice.ClientGateway + $"{userId}/bills";
        var message = new HttpRequestMessage(new HttpMethod(Request.Method), url);
        message.Headers.Authorization = new AuthenticationHeaderValue(
            "Bearer", authHeader[6..]
        );
        var response = await _httpClient.SendAsync(message);

        var bills = await this.GetResultFromResponse<List<ClientBillDto>>(response);

        var bill = bills.Value?.FirstOrDefault(bill => bill.Id == billId.ToString());
        if (bill == null)
        {
            throw new NotFoundItemException("Не удалось найти счет");
        }

        return Ok(bill);
    }
    
    private async Task<UserFill> GetUserProfile(Guid userId)
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
        var message = new HttpRequestMessage(new HttpMethod(Request.Method), url);
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
            
            return body;
        }
        else
        {
            throw new BadHttpRequestException("Не удалось выполнить получение профиля пользователя для проверки возможностей");
        }
    }

    private async Task CheckAvailableUser(Guid userId, string needPrivilege)
    {
        var userProfile = await GetUserProfile(userId);
        if (!userProfile.Privileges.Contains(needPrivilege))
        {
            throw new ForbiddenException("Не достаточно прав для данного действия");
        }
    }
}