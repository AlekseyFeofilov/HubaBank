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
    private readonly IUserService _userService;
    private readonly UrlsMicroserviceOptions _urlsMicroservice;
    private readonly ICircuitBreakerService _circuitBreakerService;
    
    public ProfileGatewayController(IUserService userService, ICircuitBreakerService circuitBreakerService, IHttpClientFactory httpClientFactory, IOptions<UrlsMicroserviceOptions> urlsMicroserviceOptions)
    {
        _httpClient = httpClientFactory.CreateClient();
        _userService = userService;
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
        
        var authHeader = Request.Headers.Authorization.FirstOrDefault();
        if (authHeader == null)
            return Unauthorized();
        
        var userId = UtilsService.GetUserIdByHeader(authHeader);
        var url = _urlsMicroservice.AuthUrl + "/users/api/v1/employees";
        
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
                var message = new HttpRequestMessage(new HttpMethod(Request.Method), url);
                message.Headers.Authorization = new AuthenticationHeaderValue(
                    "Bearer", authHeader[6..]
                );
                message.Headers.Add("requestId", await _userService.GetMessagingToken(new Guid(userId)));

                var response = await _httpClient.SendAsync(message);

                if (response.StatusCode == HttpStatusCode.InternalServerError)
                    throw new InternalServerErrorException();
                
                await _circuitBreakerService.ChangeCircuitBreakerModel(circuitBreaker);
                
                return await this.GetResultFromResponse<List<UserFill>>(response);
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
    
    [HttpGet("clients")]
    public async Task<ActionResult<List<UserFill>>> GetClients()
    {
        var retryCount = 0;
        var circuitBreaker = new CircuitBreakerDto();
        if (UtilsService.IsUnstableOperationService())
            return StatusCode(500, "Internal Server Error: нестабильная работа gateway сервиса");
        
        var authHeader = Request.Headers.Authorization.FirstOrDefault();
        if (authHeader == null)
            return Unauthorized();
        
        var userId = UtilsService.GetUserIdByHeader(authHeader);
        var url = _urlsMicroservice.AuthUrl + "/users/api/v1/users";
        
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
                var message = new HttpRequestMessage(new HttpMethod(Request.Method), url);
                message.Headers.Authorization = new AuthenticationHeaderValue(
                    "Bearer", authHeader[6..]
                );
                message.Headers.Add("requestId", await _userService.GetMessagingToken(new Guid(userId)));
                
                var response = await _httpClient.SendAsync(message);
                
                if (response.StatusCode == HttpStatusCode.InternalServerError)
                    throw new InternalServerErrorException();
        
                return await this.GetResultFromResponse<List<UserFill>>(response);
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
    
    [HttpGet("my")]
    public async Task<ActionResult<UserFill>> GetProfile()
    {
        var retryCount = 0;
        var circuitBreaker = new CircuitBreakerDto();
        if (UtilsService.IsUnstableOperationService())
            return StatusCode(500, "Internal Server Error: нестабильная работа gateway сервиса");
        
        var authHeader = Request.Headers.Authorization.FirstOrDefault();
        if (authHeader == null)
            return Unauthorized();
        
        var userId = UtilsService.GetUserIdByHeader(authHeader);
        var url = _urlsMicroservice.AuthUrl + "my";
        
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
                var message = new HttpRequestMessage(new HttpMethod(Request.Method), url);
                message.Headers.Authorization = new AuthenticationHeaderValue(
                    "Bearer", authHeader[6..]
                );
                message.Headers.Add("requestId", await _userService.GetMessagingToken(new Guid(userId)));
                
                var response = await _httpClient.SendAsync(message);
                
                if (response.StatusCode == HttpStatusCode.InternalServerError)
                    throw new InternalServerErrorException();
        
                return await this.GetResultFromResponse<UserFill>(response);
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
    
    [HttpGet("{userId}")]
    public async Task<ActionResult<UserFill>> GetUser(Guid userId)
    {
        var retryCount = 0;
        var circuitBreaker = new CircuitBreakerDto();
        if (UtilsService.IsUnstableOperationService())
            return StatusCode(500, "Internal Server Error: нестабильная работа gateway сервиса");
        
        var authHeader = Request.Headers.Authorization.FirstOrDefault();
        if (authHeader == null)
            return Unauthorized();
        
        var authUserId = UtilsService.GetUserIdByHeader(authHeader);
        var url = _urlsMicroservice.AuthUrl + $"/users/api/v1/user/{userId}";
        
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
                var message = new HttpRequestMessage(new HttpMethod(Request.Method), url);
                message.Headers.Authorization = new AuthenticationHeaderValue(
                    "Bearer", authHeader[6..]
                );
                message.Headers.Add("requestId", await _userService.GetMessagingToken(new Guid(authUserId)));
                
                var response = await _httpClient.SendAsync(message);
                
                if (response.StatusCode == HttpStatusCode.InternalServerError)
                    throw new InternalServerErrorException();
                
                return await this.GetResultFromResponse<UserFill>(response);
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
    
    [HttpPost("{userId}/block")]
    public async Task<IActionResult> BlockUser(Guid userId)
    {
        var retryCount = 0;
        var circuitBreaker = new CircuitBreakerDto();
        if (UtilsService.IsUnstableOperationService())
            return StatusCode(500, "Internal Server Error: нестабильная работа gateway сервиса");
        
        var authHeader = Request.Headers.Authorization.FirstOrDefault();
        if (authHeader == null)
            return Unauthorized();
        
        var authUserId = UtilsService.GetUserIdByHeader(authHeader);
        var url = _urlsMicroservice.AuthUrl + $"/users/api/v1/user/{userId}/block";
        
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
                var message = new HttpRequestMessage(new HttpMethod(Request.Method), url);
                message.Headers.Authorization = new AuthenticationHeaderValue(
                    "Bearer", authHeader[6..]
                );
                message.Headers.Add("requestId", await _userService.GetMessagingToken(new Guid(authUserId)));
                message.Headers.Add("idempotentKey", new Guid().ToString());
                
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
    
    [HttpPost("{userId}/unblock")]
    public async Task<IActionResult> UnblockUser(Guid userId)
    {
        var retryCount = 0;
        var circuitBreaker = new CircuitBreakerDto();
        if (UtilsService.IsUnstableOperationService())
            return StatusCode(500, "Internal Server Error: нестабильная работа gateway сервиса");
        
        var authHeader = Request.Headers.Authorization.FirstOrDefault();
        if (authHeader == null)
            return Unauthorized();
        
        var authUserId = UtilsService.GetUserIdByHeader(authHeader);
        var url = _urlsMicroservice.AuthUrl + $"/users/api/v1/user/{userId}/unblock";
        
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
                var message = new HttpRequestMessage(new HttpMethod(Request.Method), url);
                message.Headers.Authorization = new AuthenticationHeaderValue(
                    "Bearer", authHeader[6..]
                );
                message.Headers.Add("requestId", await _userService.GetMessagingToken(new Guid(authUserId)));
                message.Headers.Add("idempotentKey", new Guid().ToString());
                
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
                {
                    throw new MaxCountException(
                        "Превышено максимальное количество попыток получения успешного запроса");
                }

                await _circuitBreakerService.ChangeCircuitBreakerModel(circuitBreaker);

                if (circuitBreaker.ErrorCount + retryCount > 30 && percentageError > 70 &&
                    circuitBreaker.CircuitBreakerStatus is not CircuitBreakerStatus.HalfOpen)
                {
                    await _circuitBreakerService.OpenCircuitBreaker(MicroserviceName.Core);
                }
            }
        }
    }
}