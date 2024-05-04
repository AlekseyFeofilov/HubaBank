using System.Net.Http.Headers;
using System.Text.Json;
using EmployeeGateway.BL;
using EmployeeGateway.Common.DTO;
using EmployeeGateway.Common.Enum;
using EmployeeGateway.Common.Exceptions;
using EmployeeGateway.Common.ServicesInterface;
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
    private readonly ICircuitBreakerService _circuitBreakerService;
    
    public BillGatewayController(ICircuitBreakerService circuitBreakerService, IHttpClientFactory httpClientFactory, IOptions<UrlsMicroserviceOptions> urlsMicroserviceOptions)
    {
        _httpClient = httpClientFactory.CreateClient();
        _urlsMicroservice = urlsMicroserviceOptions.Value;
        _circuitBreakerService = circuitBreakerService;
    }
    
    [HttpGet("{userId:guid}")]
    public async Task<ActionResult<List<ClientBillDto>>> GetBills(Guid userId)
    {
        var retryCount = 0;
        var circuitBreaker = new CircuitBreakerDto();
        if (UtilsService.IsUnstableOperationService())
            return StatusCode(500, "Internal Server Error: нестабильная работа gateway сервиса");
        
        while (true)
        {
            try
            {
                await _circuitBreakerService.CheckStatus(MicroserviceName.Core);
                circuitBreaker = await _circuitBreakerService.GetCircuitBreaker(MicroserviceName.Core);
                if (circuitBreaker == null)
                    return StatusCode(504, "Произошла неизвестная ошибка с подсчетом кол-ва ошибок");

                if (circuitBreaker.CircuitBreakerStatus is CircuitBreakerStatus.Open)
                    return StatusCode(523, "Микросервис временно не доступен");

                retryCount++;
                circuitBreaker.RequestCount++;

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
                
                if (!response.IsSuccessStatusCode)
                {
                    throw new Exception();
                }

                return await this.GetResultFromResponse<List<ClientBillDto>>(response);
            }
            catch (Exception)
            {
                circuitBreaker.ErrorCount += 1;
                
                var percentageError = circuitBreaker.RequestCount / circuitBreaker.ErrorCount * 100;
                
                if (retryCount > 10)
                {
                    throw new MaxCountException(
                        "Превышено максимальное количество попыток получения успешного запроса");
                }

                await _circuitBreakerService.ChangeCircuitBreakerModel(circuitBreaker);

                if (circuitBreaker.ErrorCount + retryCount > 5 && percentageError > 70 &&
                    circuitBreaker.CircuitBreakerStatus is not CircuitBreakerStatus.HalfOpen)
                {
                    await _circuitBreakerService.OpenCircuitBreaker(MicroserviceName.Core);
                }
            }
        }
    }
    
    [HttpGet("{userId:guid}/bill/{billId}")]
    public async Task<ActionResult<ClientBillDto>> GetBill(Guid userId, Guid billId)
    {
        var retryCount = 0;
        var circuitBreaker = new CircuitBreakerDto();
        if (UtilsService.IsUnstableOperationService())
            return StatusCode(500, "Internal Server Error: нестабильная работа gateway сервиса");
        
        while (true)
        {
            try
            {
                await _circuitBreakerService.CheckStatus(MicroserviceName.Core);
                circuitBreaker = await _circuitBreakerService.GetCircuitBreaker(MicroserviceName.Core);
                if (circuitBreaker == null)
                    return StatusCode(504, "Произошла неизвестная ошибка с подсчетом кол-ва ошибок");

                if (circuitBreaker.CircuitBreakerStatus is CircuitBreakerStatus.Open)
                    return StatusCode(523, "Микросервис временно не доступен");

                retryCount++;
                circuitBreaker.RequestCount++;

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
                
                if (!response.IsSuccessStatusCode)
                {
                    throw new Exception();
                }

                var bills = await this.GetResultFromResponse<List<ClientBillDto>>(response);

                var bill = bills.Value?.FirstOrDefault(bill => bill.Id == billId.ToString());
                if (bill == null)
                {
                    throw new NotFoundItemException("Не удалось найти счет");
                }

                return Ok(bill);
            }
            catch (Exception)
            {
                circuitBreaker.ErrorCount += 1;
                
                var percentageError = circuitBreaker.RequestCount / circuitBreaker.ErrorCount * 100;
                
                if (retryCount > 10)
                {
                    throw new MaxCountException(
                        "Превышено максимальное количество попыток получения успешного запроса");
                }

                await _circuitBreakerService.ChangeCircuitBreakerModel(circuitBreaker);

                if (circuitBreaker.ErrorCount + retryCount > 5 && percentageError > 70 &&
                    circuitBreaker.CircuitBreakerStatus is not CircuitBreakerStatus.HalfOpen)
                {
                    await _circuitBreakerService.OpenCircuitBreaker(MicroserviceName.Core);
                }
            }
        }
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
        
        // TODO: Как-то не блокать предыдущий микросервис
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