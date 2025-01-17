using System.Net;
using System.Text;
using EmployeeGateway.Models;
using Microsoft.AspNetCore.Mvc;
using System.Text.Json;
using EmployeeGateway.BL;
using EmployeeGateway.Common.DTO;
using EmployeeGateway.Common.Enum;
using EmployeeGateway.Common.Exceptions;
using EmployeeGateway.Common.ServicesInterface;
using EmployeeGateway.Common.System;
using Microsoft.Extensions.Options;

namespace EmployeeGateway.Controllers;

[ApiController]
[Route("api/gateway/auth/")]
public class AuthGatewayController : ControllerBase
{
    private readonly HttpClient _httpClient;
    private readonly IUserService _userService;
    private readonly UrlsMicroserviceOptions _urlsMicroservice;
    private readonly ICircuitBreakerService _circuitBreakerService;

    public AuthGatewayController(ICircuitBreakerService circuitBreakerService, IHttpClientFactory httpClientFactory, IUserService userService, IOptions<UrlsMicroserviceOptions> urlsMicroserviceOptions)
    {
        _httpClient = httpClientFactory.CreateClient();
        _userService = userService;
        _urlsMicroservice = urlsMicroserviceOptions.Value;
        _circuitBreakerService = circuitBreakerService;
    }

    [HttpPost("register")]
    public async Task<ActionResult<TokenPairs>> Register([FromBody] RegisterCredential requestBody, [FromHeader] string? requestId = null)
    {
        var retryCount = 0;
        var circuitBreaker = new CircuitBreakerDto();
        if (UtilsService.IsUnstableOperationService())
            return StatusCode(500, "Internal Server Error: нестабильная работа gateway сервиса");
        
        string downstreamUrl = _urlsMicroservice.AuthUrl + "register";
        var json = JsonSerializer.Serialize(requestBody, UtilsService.jsonOptions);
        var content = new StringContent(json, Encoding.UTF8, "application/json");
        var message = new HttpRequestMessage(new HttpMethod(Request.Method), downstreamUrl)
        {
            Content = content
        };
        message.Headers.Add("requestId", requestId);
        message.Headers.Add("idempotentKey", Guid.NewGuid().ToString());
        
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
                var response = await _httpClient.SendAsync(message);

                var tokens = await this.GetResultFromResponse<TokenPairs>(response);

                if (response.StatusCode == HttpStatusCode.InternalServerError)
                {
                    throw new InternalServerErrorException();
                }

                var downstreamResponse = await response.Content.ReadAsStringAsync();
                var body = JsonSerializer.Deserialize<TokenPairs>(downstreamResponse, UtilsService.jsonOptions);
                var userId = UtilsService.GetUserIdByHeader(body.accessToken);

                string downstreamUrlRole = _urlsMicroservice.AuthUrl + "user/" + userId + "/roles";
                var clientRole = new EditUserRoleDto { names = new List<string>() { "CLIENT" } };
                var jsonRole = JsonSerializer.Serialize(clientRole, UtilsService.jsonOptions);
                var contentRole = new StringContent(jsonRole, Encoding.UTF8, "application/json");
            
                var messageRole = new HttpRequestMessage(new HttpMethod(Request.Method), downstreamUrlRole);
                messageRole.Headers.Add("requestId", requestId);
                messageRole.Headers.Add("idempotentKey", Guid.NewGuid().ToString());
                messageRole.Content = contentRole;
                var responseRole = await _httpClient.SendAsync(messageRole);
                
                if (responseRole.StatusCode == HttpStatusCode.InternalServerError)
                {
                    throw new InternalServerErrorException();
                }

                await _userService.SetMessagingToken(Guid.Parse(userId), requestBody.MessagingToken.ToString());
                return tokens;
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
    
    [HttpPost("login")]
    [Produces("application/json")]
    public async Task<ActionResult<TokenPairs>> Login([FromBody] LoginCredentials credentials, [FromHeader] string? requestId = null)
    {
        var retryCount = 0;
        var circuitBreaker = new CircuitBreakerDto();
        if (UtilsService.IsUnstableOperationService())
            return StatusCode(500, "Internal Server Error: нестабильная работа gateway сервиса");
        
        string downstreamUrl = _urlsMicroservice.AuthUrl + "login";

        var json = JsonSerializer.Serialize(credentials, UtilsService.jsonOptions);
        var content = new StringContent(json, Encoding.UTF8, "application/json");
        var message = new HttpRequestMessage(new HttpMethod(Request.Method), downstreamUrl)
        {
            Content = content
        };
        message.Headers.Add("requestId", requestId);
        message.Headers.Add("idempotentKey", Guid.NewGuid().ToString());
        
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
                var response = await _httpClient.SendAsync(message);
                
                if (response.StatusCode == HttpStatusCode.InternalServerError)
                {
                    throw new InternalServerErrorException();
                }
                
                var downstreamResponse = await response.Content.ReadAsStringAsync();
                var body = JsonSerializer.Deserialize<TokenPairs>(downstreamResponse, UtilsService.jsonOptions);
                var userId = UtilsService.GetUserIdByHeader(body.accessToken);
                await _userService.SetMessagingToken(Guid.Parse(userId), credentials.MessagingToken.ToString());
        
                return await this.GetResultFromResponse<TokenPairs>(response);
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
    
     [HttpPost("login-sso")]
    [Produces("application/json")]
    public async Task<ActionResult<TokenPairs>> LoginSso([FromBody] LoginSsoCredentials credentials, [FromHeader] string? requestId = null)
    {
        var retryCount = 0;
        var circuitBreaker = new CircuitBreakerDto();
        if (UtilsService.IsUnstableOperationService())
            return StatusCode(500, "Internal Server Error: нестабильная работа gateway сервиса");
        
        string downstreamUrl = _urlsMicroservice.AuthUrl + "jwt";
        var content = new StringContent(credentials.JwtSso, Encoding.UTF8, "application/json");
        var message = new HttpRequestMessage(new HttpMethod(Request.Method), downstreamUrl)
        {
            Content = content
        };
        message.Headers.Add("requestId", requestId);
        message.Headers.Add("idempotentKey", Guid.NewGuid().ToString());
        
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
                var response = await _httpClient.SendAsync(message);
                
                if (response.StatusCode == HttpStatusCode.InternalServerError)
                {
                    throw new InternalServerErrorException();
                }
                
                var downstreamResponse = await response.Content.ReadAsStringAsync();
                var body = JsonSerializer.Deserialize<TokenPairs>(downstreamResponse, UtilsService.jsonOptions);
                var userId = UtilsService.GetUserIdByHeader(body.accessToken);
                await _userService.SetMessagingToken(Guid.Parse(userId), credentials.MessagingToken.ToString());
        
                return await this.GetResultFromResponse<TokenPairs>(response);
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