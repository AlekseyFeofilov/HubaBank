using BFF_client.Api.model;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Options;
using System.Text.Json;
using System.Text;
using Microsoft.AspNetCore.Http.Json;
using System.Security.Claims;
using BFF_client.Api.model.bill;
using System.Net.Http.Headers;
using System.IdentityModel.Tokens.Jwt;
using Microsoft.Win32;
using BFF_client.Api.Services.Bill;
using System.Net.Http;
using BFF_client.Api.Models;
using RabbitMQ.Client;
using System.Threading.Channels;
using BFF_client.Api.Models.bill;
using BFF_client.Api.Patterns;

namespace BFF_client.Api.Controllers
{
    [Route("api/bills")]
    [ApiController]
    public class BillController : ControllerBase
    {
        private readonly HttpClient _httpClient;
        private readonly ConfigUrls _configUrls;
        private readonly IBillService _billService;
        private readonly IHttpClientFactory _httpClientFactory;
        private readonly ICircuitBreakerService _circuitBreakerService;

        public BillController(
            IHttpClientFactory httpClientFactory, 
            IOptions<ConfigUrls> options,
            IBillService billService,
            ICircuitBreakerService circuitBreaker)
        {
            _httpClient = httpClientFactory.CreateClient();
            _configUrls = options.Value;
            _billService = billService;
            _httpClientFactory = httpClientFactory;
            _circuitBreakerService = circuitBreaker;
        }

        [HttpGet("")]
        [Produces("application/json")]
        public async Task<ActionResult<List<ClientBillDto>>> GetAllBills([FromHeader] string? requestId = null)
        {
            var authHeader = Request.Headers.Authorization.FirstOrDefault();
            if (authHeader == null)
            {
                return Unauthorized();
            }
            var userId = ControllersUtils.GetUserIdByHeader(authHeader);
            if (userId == null)
            {
                return Unauthorized();
            }
            var profileWithPrivileges = await ControllersUtils.GetProfileWithPrivileges(
                authHeader, _configUrls, _httpClientFactory.CreateClient(), requestId, _circuitBreakerService);
            if (profileWithPrivileges == null)
            {
                return Unauthorized();
            }
            if (profileWithPrivileges.Privileges.Contains(Privileges.BILL_READ) == false)
            {
                return StatusCode(StatusCodes.Status403Forbidden);
            }

            string downstreamUrl = _configUrls.core + "users/" + userId + "/bills";
            var message = new HttpRequestMessage(new HttpMethod(Request.Method), downstreamUrl);
            message.Headers.Add("requestId", requestId);
            var response = await _httpClient.SendWithRetryAsync(message, _circuitBreakerService, UnstableService.CORE);

            var knownBills = await _billService.GetKnownUserBills(Guid.Parse(userId));

            return await this.GetResultFromResponse<List<ClientBillDto>>(
                response,
                l => l.ForEach(b => b.isHidden = knownBills.Find(kb => kb.BillId == Guid.Parse(b.Id))?.IsHidden ?? false)
            );
        }

        [HttpPost("")]
        [Produces("application/json")]
        public async Task<ActionResult<ClientBillDto>> CreateBill(CreateBillDto createBill, [FromHeader] string? requestId = null)
        {
            var authHeader = Request.Headers.Authorization.FirstOrDefault();
            if (authHeader == null)
            {
                return Unauthorized();
            }
            var userId = ControllersUtils.GetUserIdByHeader(authHeader);
            if (userId == null)
            {
                return Unauthorized();
            }
            var profileWithPrivileges = await ControllersUtils.GetProfileWithPrivileges(
                authHeader, _configUrls, _httpClientFactory.CreateClient(), requestId, _circuitBreakerService);
            if (profileWithPrivileges == null)
            {
                return Unauthorized();
            }
            if (profileWithPrivileges.Privileges.Contains(Privileges.BILL_WRITE) == false)
            {
                return StatusCode(StatusCodes.Status403Forbidden);
            }

            string downstreamUrl = _configUrls.core + "users/" + userId + "/bills";

            var json = JsonSerializer.Serialize(createBill, ControllersUtils.jsonOptions);
            var content = new StringContent(json, Encoding.UTF8, "application/json");
            
            var message = new HttpRequestMessage(new HttpMethod(Request.Method), downstreamUrl);
            message.Headers.Add("requestId", requestId);
            message.Headers.Add("idempotentKey", Guid.NewGuid().ToString());
            message.Content = content;
            var response = await _httpClient.SendWithRetryAsync(message, _circuitBreakerService, UnstableService.CORE);

            return await this.GetResultFromResponse<ClientBillDto>(response);
        }

        [HttpGet("{billId:guid}")]
        [Produces("application/json")]
        public async Task<ActionResult<ClientBillDto>> GetBillById(Guid billId, [FromHeader] string? requestId = null)
        {
            var authHeader = Request.Headers.Authorization.FirstOrDefault();
            if (authHeader == null)
            {
                return Unauthorized();
            }
            var userId = ControllersUtils.GetUserIdByHeader(authHeader);
            if (userId == null)
            {
                return Unauthorized();
            }
            var profileWithPrivileges = await ControllersUtils.GetProfileWithPrivileges(
                authHeader, _configUrls, _httpClientFactory.CreateClient(), requestId, _circuitBreakerService);
            if (profileWithPrivileges == null)
            {
                return Unauthorized();
            }
            if (profileWithPrivileges.Privileges.Contains(Privileges.BILL_READ) == false)
            {
                return StatusCode(StatusCodes.Status403Forbidden);
            }

            string downstreamUrl = _configUrls.core + "bills/" + billId;
            var message = new HttpRequestMessage(new HttpMethod(Request.Method), downstreamUrl);
            message.Headers.Add("requestId", requestId);
            var response = await _httpClient.SendWithRetryAsync(message, _circuitBreakerService, UnstableService.CORE);

            if (response.IsSuccessStatusCode)
            {
                var IsBillBelongToUser = await ControllersUtils.IsBillBelongToUser(userId, billId, _configUrls, _httpClientFactory.CreateClient(), requestId, _circuitBreakerService);
                if (IsBillBelongToUser == false)
                {
                    return StatusCode(StatusCodes.Status403Forbidden);
                }
            }

            var isHidden = await _billService.GetIsBillHidden(Guid.Parse(userId), billId);

            return await this.GetResultFromResponse<ClientBillDto>(response, b => b.isHidden = isHidden) ;
        }

        [HttpPost("{billId:guid}/hidden")]
        [Produces("application/json")]
        public async Task<IActionResult> ChangeBillHidden(Guid billId, [FromBody] bool isHidden, [FromHeader] string? requestId = null)
        {
            var authHeader = Request.Headers.Authorization.FirstOrDefault();
            if (authHeader == null)
            {
                return Unauthorized();
            }
            var userId = ControllersUtils.GetUserIdByHeader(authHeader);
            if (userId == null)
            {
                return Unauthorized();
            }

            var profileWithPrivileges = await ControllersUtils.GetProfileWithPrivileges(
                authHeader, _configUrls, _httpClientFactory.CreateClient(), requestId, _circuitBreakerService);
            if (profileWithPrivileges == null)
            {
                return Unauthorized();
            }
            var IsBillBelongToUser = await ControllersUtils.IsBillBelongToUser(userId, billId, _configUrls, _httpClientFactory.CreateClient(), requestId, _circuitBreakerService);
            if (IsBillBelongToUser == false)
            {
                return StatusCode(StatusCodes.Status403Forbidden);
            }

            await _billService.SetIsBillHidden(Guid.Parse(userId), billId, isHidden);

            return Ok();
        }

        [HttpDelete("{billId:guid}")]
        [Produces("application/json")]
        public async Task<IActionResult> DeleteBill(Guid billId, [FromHeader] string? requestId = null)
        {
            var authHeader = Request.Headers.Authorization.FirstOrDefault();
            if (authHeader == null)
            {
                return Unauthorized();
            }
            var userId = ControllersUtils.GetUserIdByHeader(authHeader);
            if (userId == null)
            {
                return Unauthorized();
            }
            var profileWithPrivileges = await ControllersUtils.GetProfileWithPrivileges(
                authHeader, _configUrls, _httpClientFactory.CreateClient(), requestId, _circuitBreakerService);
            if (profileWithPrivileges == null)
            {
                return Unauthorized();
            }
            if (profileWithPrivileges.Privileges.Contains(Privileges.BILL_WRITE) == false)
            {
                return StatusCode(StatusCodes.Status403Forbidden);
            }
            var IsBillBelongToUser = await ControllersUtils.IsBillBelongToUser(userId, billId, _configUrls, _httpClientFactory.CreateClient(), requestId, _circuitBreakerService);
            if (IsBillBelongToUser == false)
            {
                return StatusCode(StatusCodes.Status403Forbidden);
            }

            string downstreamUrl = _configUrls.core + "bills/" + billId;
            var message = new HttpRequestMessage(new HttpMethod(Request.Method), downstreamUrl);
            message.Headers.Add("requestId", requestId);
            message.Headers.Add("idempotentKey", Guid.NewGuid().ToString());
            var response = await _httpClient.SendWithRetryAsync(message, _circuitBreakerService, UnstableService.CORE);

            if (response.IsSuccessStatusCode)
            {
                await _billService.DeleteBill(Guid.Parse(userId), billId);
            }
            return await this.GetResult(response);
        }
    }
}
