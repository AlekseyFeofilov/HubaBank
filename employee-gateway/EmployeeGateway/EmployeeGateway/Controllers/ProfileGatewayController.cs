using System.Net;
using System.Net.Http.Headers;
using EmployeeGateway.BL;
using EmployeeGateway.Common.DTO;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Options;
using EmployeeGateway.Common.Enum;
using EmployeeGateway.Common.Exceptions;
using EmployeeGateway.Common.ServicesInterface;
using EmployeeGateway.Common.System;
using EmployeeGateway.Models;

namespace EmployeeGateway.Controllers;

[ApiController]
[Route("api/gateway/profile")]
public class ProfileGatewayController: ControllerBase
{
    private readonly HttpClient _httpClient;
    private readonly UrlsMicroserviceOptions _urlsMicroservice;
    private readonly ICircuitBreakerService _circuitBreakerService;
    
    public ProfileGatewayController(ICircuitBreakerService circuitBreakerService, IHttpClientFactory httpClientFactory, IOptions<UrlsMicroserviceOptions> urlsMicroserviceOptions)
    {
        _httpClient = httpClientFactory.CreateClient();
        _urlsMicroservice = urlsMicroserviceOptions.Value;
        _circuitBreakerService = circuitBreakerService;
    }
    
    [HttpGet("employees")]
    public async Task<ActionResult<List<UserFill>>> GetEmployees()
    {
        var retryCount = 0;
        var circuitBreaker = new CircuitBreakerDto();
        if (UtilsService.IsUnstableOperationService())
            return StatusCode(500, "Internal Server Error: нестабильная работа gateway сервиса");
        
        while (true)
        {
            try
            {
                await _circuitBreakerService.CheckStatus(MicroserviceName.User);
                circuitBreaker = await _circuitBreakerService.GetCircuitBreaker(MicroserviceName.User);
                if (circuitBreaker == null)
                    return StatusCode(504, "Произошла неизвестная ошибка с подсчетом кол-ва ошибок");

                if (circuitBreaker.CircuitBreakerStatus is CircuitBreakerStatus.Open)
                    return StatusCode(523, "Микросервис временно не доступен");

                retryCount++;
                circuitBreaker.RequestCount++;

                var authHeader = Request.Headers.Authorization.FirstOrDefault();
                if (authHeader == null)
                    return Unauthorized();

                var url = _urlsMicroservice.AuthUrl + "/users/api/v1/employees";
                var message = new HttpRequestMessage(new HttpMethod(Request.Method), url);
                message.Headers.Authorization = new AuthenticationHeaderValue(
                    "Bearer", authHeader[6..]
                );
                var response = await _httpClient.SendAsync(message);

                if (!response.IsSuccessStatusCode)
                {
                    throw new Exception();
                }
                
                await _circuitBreakerService.ChangeCircuitBreakerModel(circuitBreaker);
                
                return await this.GetResultFromResponse<List<UserFill>>(response);
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
                    await _circuitBreakerService.OpenCircuitBreaker(MicroserviceName.User);
                }
            }
        }
    }
    
    [HttpGet("clients")]
    public async Task<ActionResult<List<UserFill>>> GetClients()
    {
        var retryCount = 0;
        var circuitBreaker = new CircuitBreakerDto();
        if (UtilsService.IsUnstableOperationService())
            return StatusCode(500, "Internal Server Error: нестабильная работа gateway сервиса");
        
        while (true)
        {
            try
            {
                await _circuitBreakerService.CheckStatus(MicroserviceName.User);
                circuitBreaker = await _circuitBreakerService.GetCircuitBreaker(MicroserviceName.User);
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
                var url = _urlsMicroservice.AuthUrl + "/users/api/v1/users";
                var message = new HttpRequestMessage(new HttpMethod(Request.Method), url);
                message.Headers.Authorization = new AuthenticationHeaderValue(
                    "Bearer", authHeader[6..]
                );
                var response = await _httpClient.SendAsync(message);
        
                return await this.GetResultFromResponse<List<UserFill>>(response);
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
                    await _circuitBreakerService.OpenCircuitBreaker(MicroserviceName.User);
                }
            }
        }
    }
    
    [HttpGet("my")]
    public async Task<ActionResult<UserFill>> GetProfile()
    {
        var retryCount = 0;
        var circuitBreaker = new CircuitBreakerDto();
        if (UtilsService.IsUnstableOperationService())
            return StatusCode(500, "Internal Server Error: нестабильная работа gateway сервиса");
        
        while (true)
        {
            try
            {
                await _circuitBreakerService.CheckStatus(MicroserviceName.User);
                circuitBreaker = await _circuitBreakerService.GetCircuitBreaker(MicroserviceName.User);
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
                var url = _urlsMicroservice.AuthUrl + "my";
                var message = new HttpRequestMessage(new HttpMethod(Request.Method), url);
                message.Headers.Authorization = new AuthenticationHeaderValue(
                    "Bearer", authHeader[6..]
                );
                var response = await _httpClient.SendAsync(message);
                
                if (response is { IsSuccessStatusCode: false, StatusCode: >= (HttpStatusCode)500 })
                {
                    throw new Exception();
                }
        
                return await this.GetResultFromResponse<UserFill>(response);
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
                    await _circuitBreakerService.OpenCircuitBreaker(MicroserviceName.User);
                }
            }
        }
    }
    
    [HttpGet("{userId}")]
    public async Task<ActionResult<UserFill>> GetUser(Guid userId)
    {
        var retryCount = 0;
        var circuitBreaker = new CircuitBreakerDto();
        if (UtilsService.IsUnstableOperationService())
            return StatusCode(500, "Internal Server Error: нестабильная работа gateway сервиса");
        
        while (true)
        {
            try
            {
                await _circuitBreakerService.CheckStatus(MicroserviceName.User);
                circuitBreaker = await _circuitBreakerService.GetCircuitBreaker(MicroserviceName.User);
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
  
                var url = _urlsMicroservice.AuthUrl + $"/users/api/v1/user/{userId}";
                var message = new HttpRequestMessage(new HttpMethod(Request.Method), url);
                message.Headers.Authorization = new AuthenticationHeaderValue(
                    "Bearer", authHeader[6..]
                );
                var response = await _httpClient.SendAsync(message);
                
                if (!response.IsSuccessStatusCode)
                {
                    throw new Exception();
                }
        
                return await this.GetResultFromResponse<UserFill>(response);
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
                    await _circuitBreakerService.OpenCircuitBreaker(MicroserviceName.User);
                }
            }
        }
    }
    
    [HttpPost("{userId}/block")]
    public async Task<IActionResult> BlockUser(Guid userId)
    {
        var retryCount = 0;
        var circuitBreaker = new CircuitBreakerDto();
        if (UtilsService.IsUnstableOperationService())
            return StatusCode(500, "Internal Server Error: нестабильная работа gateway сервиса");
        
        while (true)
        {
            try
            {
                await _circuitBreakerService.CheckStatus(MicroserviceName.User);
                circuitBreaker = await _circuitBreakerService.GetCircuitBreaker(MicroserviceName.User);
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

                var url = _urlsMicroservice.AuthUrl + $"/users/api/v1/user/{userId}/block";
                var message = new HttpRequestMessage(new HttpMethod(Request.Method), url);
                message.Headers.Authorization = new AuthenticationHeaderValue(
                    "Bearer", authHeader[6..]
                );
                var response = await _httpClient.SendAsync(message);
        
                return await this.GetResult(response);
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
                    await _circuitBreakerService.OpenCircuitBreaker(MicroserviceName.User);
                }
            }
        }
    }
    
    [HttpPost("{userId}/unblock")]
    public async Task<IActionResult> UnblockUser(Guid userId)
    {
        var retryCount = 0;
        var circuitBreaker = new CircuitBreakerDto();
        if (UtilsService.IsUnstableOperationService())
            return StatusCode(500, "Internal Server Error: нестабильная работа gateway сервиса");
        
        while (true)
        {
            try
            {
                await _circuitBreakerService.CheckStatus(MicroserviceName.User);
                circuitBreaker = await _circuitBreakerService.GetCircuitBreaker(MicroserviceName.User);
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
                var url = _urlsMicroservice.AuthUrl + $"/users/api/v1/user/{userId}/unblock";
                var message = new HttpRequestMessage(new HttpMethod(Request.Method), url);
                message.Headers.Authorization = new AuthenticationHeaderValue(
                    "Bearer", authHeader[6..]
                );
                var response = await _httpClient.SendAsync(message);
                
                if (response is { IsSuccessStatusCode: false, StatusCode: >= (HttpStatusCode)500 })
                {
                    throw new Exception();
                }
        
                return await this.GetResult(response);
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
                    await _circuitBreakerService.OpenCircuitBreaker(MicroserviceName.User);
                }
            }
        }
    }
}