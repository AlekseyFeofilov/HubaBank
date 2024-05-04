using System.Text;
using System.Text.Json;
using EmployeeGateway.BL;
using EmployeeGateway.Common.DTO;
using EmployeeGateway.Common.DTO.Credit;
using EmployeeGateway.Common.Enum;
using EmployeeGateway.Common.Exceptions;
using EmployeeGateway.Common.ServicesInterface;
using EmployeeGateway.Common.System;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Options;

namespace EmployeeGateway.Controllers;

[ApiController]
[Route("")]
public class CreditController: ControllerBase
{
    private readonly HttpClient _httpClient;
    private readonly UrlsMicroserviceOptions _urlsMicroservice;
    private readonly ICircuitBreakerService _circuitBreakerService;
    
    public CreditController(ICircuitBreakerService circuitBreakerService, IHttpClientFactory httpClientFactory, IOptions<UrlsMicroserviceOptions> urlsMicroserviceOptions)
    {
        _httpClient = httpClientFactory.CreateClient();
        _urlsMicroservice = urlsMicroserviceOptions.Value;
        _circuitBreakerService = circuitBreakerService;
    }

    [HttpGet("users/{userId:guid}/credits")]
    public async Task<ActionResult<List<CreditDto>>> GetCredits(Guid userId, Guid requestId)
    {
        var retryCount = 0;
        var circuitBreaker = new CircuitBreakerDto();
        if (UtilsService.IsUnstableOperationService())
            return StatusCode(500, "Internal Server Error: нестабильная работа gateway сервиса");
        
        while (true)
        {
            try
            {
                await _circuitBreakerService.CheckStatus(MicroserviceName.Credit);
                circuitBreaker = await _circuitBreakerService.GetCircuitBreaker(MicroserviceName.Credit);
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
        
                var url = _urlsMicroservice.CreditUrl + $"Credit/users/{userId}";
                var message = new HttpRequestMessage(new HttpMethod(Request.Method), url);
                var response = await _httpClient.SendAsync(message);
                
                if (!response.IsSuccessStatusCode)
                {
                    throw new Exception();
                }

                return await this.GetResultFromResponse<List<CreditDto>>(response);
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
                    await _circuitBreakerService.OpenCircuitBreaker(MicroserviceName.Credit);
                }
            }
        }
    }
    
    [HttpGet("credits/{creditId:guid}")]
    public async Task<ActionResult<CreditDto>> GetCredit(Guid creditId)
    {
        var retryCount = 0;
        var circuitBreaker = new CircuitBreakerDto();
        if (UtilsService.IsUnstableOperationService())
            return StatusCode(500, "Internal Server Error: нестабильная работа gateway сервиса");
        
        while (true)
        {
            try
            {
                await _circuitBreakerService.CheckStatus(MicroserviceName.Credit);
                circuitBreaker = await _circuitBreakerService.GetCircuitBreaker(MicroserviceName.Credit);
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
  
                var url = _urlsMicroservice.ClientGateway + $"Credit/{creditId}";
                var message = new HttpRequestMessage(new HttpMethod(Request.Method), url);
                var response = await _httpClient.SendAsync(message);
                
                if (!response.IsSuccessStatusCode)
                {
                    throw new Exception();
                }

                return await this.GetResultFromResponse<CreditDto>(response);
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
                    await _circuitBreakerService.OpenCircuitBreaker(MicroserviceName.Credit);
                }
            }
        }
    }
    
    [HttpGet("credit-terms")]
    public async Task<ActionResult<List<CreditTermDto>>> GetCreditTerms()
    {
        var retryCount = 0;
        var circuitBreaker = new CircuitBreakerDto();
        if (UtilsService.IsUnstableOperationService())
            return StatusCode(500, "Internal Server Error: нестабильная работа gateway сервиса");
        
        while (true)
        {
            try
            {
                await _circuitBreakerService.CheckStatus(MicroserviceName.Credit);
                circuitBreaker = await _circuitBreakerService.GetCircuitBreaker(MicroserviceName.Credit);
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
  
                var url = _urlsMicroservice.CreditUrl + "CreditTerms";
                var message = new HttpRequestMessage(new HttpMethod(Request.Method), url);
                var response = await _httpClient.SendAsync(message);
                
                if (!response.IsSuccessStatusCode)
                {
                    throw new Exception();
                }

                return await this.GetResultFromResponse<List<CreditTermDto>>(response);
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
                    await _circuitBreakerService.OpenCircuitBreaker(MicroserviceName.Credit);
                }
            }
        }
    }
    
    [HttpPut("credit-terms")]
    public async Task<ActionResult<string>> CreateCreditTerm(CreditTermCreateDto model)
    {
        var retryCount = 0;
        var circuitBreaker = new CircuitBreakerDto();
        if (UtilsService.IsUnstableOperationService())
            return StatusCode(500, "Internal Server Error: нестабильная работа gateway сервиса");
        
        while (true)
        {
            try
            {
                await _circuitBreakerService.CheckStatus(MicroserviceName.Credit);
                circuitBreaker = await _circuitBreakerService.GetCircuitBreaker(MicroserviceName.Credit);
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
  
                var downstreamUrl = _urlsMicroservice.CreditUrl + "CreditTerms";
                var json = JsonSerializer.Serialize(model, UtilsService.jsonOptions);
                var content = new StringContent(json, Encoding.UTF8, "application/json");
      
                var response = await _httpClient.PostAsync(downstreamUrl, content);
                
                if (!response.IsSuccessStatusCode)
                {
                    throw new Exception();
                }

                return await this.GetResultFromResponse<string>(response);
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
                    await _circuitBreakerService.OpenCircuitBreaker(MicroserviceName.Credit);
                }
            }
        }
    }
    
    [HttpDelete("credit-terms/{creditTermsId:guid}")]
    public async Task<IActionResult> DeleteCreditTerms(Guid creditTermsId)
    {
        var retryCount = 0;
        var circuitBreaker = new CircuitBreakerDto();
        if (UtilsService.IsUnstableOperationService())
            return StatusCode(500, "Internal Server Error: нестабильная работа gateway сервиса");
        
        while (true)
        {
            try
            {
                await _circuitBreakerService.CheckStatus(MicroserviceName.Credit);
                circuitBreaker = await _circuitBreakerService.GetCircuitBreaker(MicroserviceName.Credit);
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
  
                var url = _urlsMicroservice.CreditUrl + $"CreditTerms/{creditTermsId}";
                var message = new HttpRequestMessage(new HttpMethod(Request.Method), url);
                var response = await _httpClient.SendAsync(message);
                
                if (!response.IsSuccessStatusCode)
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
                    await _circuitBreakerService.OpenCircuitBreaker(MicroserviceName.Credit);
                }
            }
        }
    }
    
    [HttpGet("credits/{creditId:guid}/payments")]
    public async Task<ActionResult<List<CreditPaymentDto>>> GetPaymentsOfCredit(Guid creditId)
    {
        var retryCount = 0;
        var circuitBreaker = new CircuitBreakerDto();
        if (UtilsService.IsUnstableOperationService())
            return StatusCode(500, "Internal Server Error: нестабильная работа gateway сервиса");
        
        while (true)
        {
            try
            {
                await _circuitBreakerService.CheckStatus(MicroserviceName.Credit);
                circuitBreaker = await _circuitBreakerService.GetCircuitBreaker(MicroserviceName.Credit);
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
        
                var url = _urlsMicroservice.CreditUrl + $"Payment/{creditId}";
                var message = new HttpRequestMessage(new HttpMethod(Request.Method), url);
                var response = await _httpClient.SendAsync(message);
                
                if (!response.IsSuccessStatusCode)
                {
                    throw new Exception();
                }

                return await this.GetResultFromResponse<List<CreditPaymentDto>>(response);
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
                    await _circuitBreakerService.OpenCircuitBreaker(MicroserviceName.Credit);
                }
            }
        }
    }
}