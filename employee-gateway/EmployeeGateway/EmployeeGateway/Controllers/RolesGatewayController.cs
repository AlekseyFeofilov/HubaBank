using System.Net;
using System.Net.Http.Headers;
using System.Text;
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
[Route("api/gateway/roles/")]
public class RolesGatewayController : ControllerBase
{
    private readonly HttpClient _httpClient;
    private readonly IUserService _userService;
    private readonly UrlsMicroserviceOptions _urlsMicroservice;
    private readonly ICircuitBreakerService _circuitBreakerService;

    public RolesGatewayController(IUserService userService, ICircuitBreakerService circuitBreakerService, IHttpClientFactory httpClientFactory,
        IOptions<UrlsMicroserviceOptions> urlsMicroserviceOptions)
    {
        _httpClient = httpClientFactory.CreateClient();
        _urlsMicroservice = urlsMicroserviceOptions.Value;
        _circuitBreakerService = circuitBreakerService;
        _userService = userService;
    }

    [HttpPost("{userId:guid}")]
    public async Task<IActionResult> SetRoles(Guid userId, Roles roles, [FromHeader] string? requestId = null)
    {
        var retryCount = 0;
        var circuitBreaker = new CircuitBreakerDto();
        if (UtilsService.IsUnstableOperationService())
            return StatusCode(500, "Internal Server Error: нестабильная работа gateway сервиса");
        
        var authHeader = Request.Headers.Authorization.FirstOrDefault();
        if (authHeader == null)
            return Unauthorized();
        
        var authUserId = UtilsService.GetUserIdByHeader(authHeader);
        if (authUserId == null)
            return Unauthorized();
        
        var json = JsonSerializer.Serialize(roles, UtilsService.jsonOptions);
        var content = new StringContent(json, Encoding.UTF8, "application/json");
        var url = _urlsMicroservice.AuthUrl + $"/users/api/v1/user/{userId}/roles";
        
        while (true)
        {
            await _circuitBreakerService.CheckStatus(MicroserviceName.User);
            circuitBreaker = await _circuitBreakerService.GetCircuitBreaker(MicroserviceName.User);
            if (circuitBreaker == null)
                return StatusCode(504, "Произошла неизвестная ошибка с подсчетом кол-ва ошибок");

            if (circuitBreaker.CircuitBreakerStatus is CircuitBreakerStatus.Open)
                return StatusCode(523, "Микросервис временно не доступен");

            retryCount++;
            circuitBreaker.RequestCount++;
            
            try
            {
                var currentRoles = await GetRoles(new Guid(authUserId));
                roles.Names.AddRange(currentRoles);
                
                var message = new HttpRequestMessage(HttpMethod.Post, url)
                {
                    Content = content
                };
                message.Headers.Authorization = new AuthenticationHeaderValue(
                    "Bearer", authHeader[6..]
                );
                message.Headers.Add("requestId", requestId);
                message.Headers.Add("idempotentKey", Guid.NewGuid().ToString());

                var response = await _httpClient.SendAsync(message);
                
                if (response.StatusCode == HttpStatusCode.InternalServerError)
                    throw new InternalServerErrorException();

                return await this.GetResult(response);
            }
            catch (InternalServerErrorException)
            {
                circuitBreaker.ErrorCount += 1;
                
                var percentageError = circuitBreaker.RequestCount / circuitBreaker.ErrorCount * 100;
                
                if (retryCount > 50)
                    throw new MaxCountException(
                        "Превышено максимальное количество попыток получения успешного запроса");

                await _circuitBreakerService.ChangeCircuitBreakerModel(circuitBreaker);

                if (circuitBreaker.ErrorCount + retryCount > 30 && percentageError > 70 &&
                    circuitBreaker.CircuitBreakerStatus is not CircuitBreakerStatus.HalfOpen)
                { 
                    await _circuitBreakerService.OpenCircuitBreaker(MicroserviceName.Core);
                }
            }
        }
    }
    
    [HttpDelete("{userId:guid}/{roleName}")]
    public async Task<IActionResult> DeleteRole(Guid userId, string roleName, [FromHeader] string? requestId = null)
    {
        var retryCount = 0;
        var circuitBreaker = new CircuitBreakerDto();
        if (UtilsService.IsUnstableOperationService())
            return StatusCode(500, "Internal Server Error: нестабильная работа gateway сервиса");
        
        var authHeader = Request.Headers.Authorization.FirstOrDefault();
        if (authHeader == null)
            return Unauthorized();
        
        var authUserId = UtilsService.GetUserIdByHeader(authHeader);
        if (authUserId == null)
            return Unauthorized();
        
        var url = _urlsMicroservice.AuthUrl + $"/users/api/v1/user/{userId}/roles";
        
        while (true)
        {
            await _circuitBreakerService.CheckStatus(MicroserviceName.User);
            circuitBreaker = await _circuitBreakerService.GetCircuitBreaker(MicroserviceName.User);
            if (circuitBreaker == null)
                return StatusCode(504, "Произошла неизвестная ошибка с подсчетом кол-ва ошибок");

            if (circuitBreaker.CircuitBreakerStatus is CircuitBreakerStatus.Open)
                return StatusCode(523, "Микросервис временно не доступен");

            retryCount++;
            circuitBreaker.RequestCount++;
            
            try
            {
                var currentRoles = await GetRoles(userId);
                currentRoles.Remove(roleName);
        
                var json = JsonSerializer.Serialize(new Roles
                {
                    Names = currentRoles
                }, UtilsService.jsonOptions);
                var content = new StringContent(json, Encoding.UTF8, "application/json");
                var message = new HttpRequestMessage(HttpMethod.Post, url)
                {
                    Content = content
                };
                message.Headers.Authorization = new AuthenticationHeaderValue(
                    "Bearer", authHeader[6..]
                );
                message.Headers.Add("requestId", requestId);
                message.Headers.Add("idempotentKey", Guid.NewGuid().ToString());

                var response = await _httpClient.SendAsync(message);
                
                if (response.StatusCode == HttpStatusCode.InternalServerError)
                    throw new InternalServerErrorException();
                
                return await this.GetResult(response);
            }
            catch (InternalServerErrorException)
            {
                circuitBreaker.ErrorCount += 1;
                
                var percentageError = circuitBreaker.RequestCount / circuitBreaker.ErrorCount * 100;
                
                if (retryCount > 50)
                    throw new MaxCountException(
                        "Превышено максимальное количество попыток получения успешного запроса");

                await _circuitBreakerService.ChangeCircuitBreakerModel(circuitBreaker);

                if (circuitBreaker.ErrorCount + retryCount > 30 && percentageError > 70 &&
                    circuitBreaker.CircuitBreakerStatus is not CircuitBreakerStatus.HalfOpen)
                { 
                    await _circuitBreakerService.OpenCircuitBreaker(MicroserviceName.Core);
                }
            }
        }
    }
    
    private async Task<List<string>> GetRoles(Guid userId)
    {
        // TODO: добавить retry и breaker
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