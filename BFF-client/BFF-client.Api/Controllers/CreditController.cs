using BFF_client.Api.model.bill;
using BFF_client.Api.model;
using BFF_client.Api.Models;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using System.Net.Http;
using BFF_client.Api.Services.Bill;
using Microsoft.Extensions.Options;
using BFF_client.Api.Models.Credit;
using Microsoft.AspNetCore.Http.Json;
using System.Text.Json;
using BFF_client.Api.Models.bill;
using System.Text;

namespace BFF_client.Api.Controllers
{
    [Route("api/credits")]
    [ApiController]
    public class CreditController : ControllerBase
    {
        private readonly HttpClient _httpClient;
        private readonly ConfigUrls _configUrls;
        private readonly IConfiguration _configuration;
        private readonly IBillService _billService;
        private readonly IHttpClientFactory _httpClientFactory;

        public CreditController(IHttpClientFactory httpClientFactory, IOptions<ConfigUrls> options, IConfiguration configuration, IBillService billService)
        {
            _httpClient = httpClientFactory.CreateClient();
            _configUrls = options.Value;
            _configuration = configuration;
            _billService = billService;
            _httpClientFactory = httpClientFactory;
        }

        [HttpGet("credit/{creditId:guid}")]
        [Produces("application/json")]
        public async Task<ActionResult<CreditDto>> GetCreditById(Guid creditId, [FromHeader] string? requestId = null)
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
                authHeader, _configUrls, _httpClientFactory.CreateClient(), requestId);
            if (profileWithPrivileges == null)
            {
                return Unauthorized();
            }

            string downstreamUrl = _configUrls.credit + "Credit/" + creditId;
            var message = new HttpRequestMessage(new HttpMethod(Request.Method), downstreamUrl);
            message.Headers.Add("requestId", requestId);
            var response = await _httpClient.SendAsync(message);

            if (response.IsSuccessStatusCode)
            {
                var downstreamResponse = await response.Content.ReadAsStringAsync();
                var body = JsonSerializer.Deserialize<CreditDto>(downstreamResponse, ControllersUtils.jsonOptions);
                if (body.AccountId.ToString() != userId)
                {
                    return StatusCode(StatusCodes.Status403Forbidden);
                }
            }

            return await this.GetResultFromResponse<CreditDto>(response);
        }

        [HttpGet("credit")]
        [Produces("application/json")]
        public async Task<ActionResult<List<CreditDto>>> GetAllCredits([FromHeader] string? requestId = null)
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
                authHeader, _configUrls, _httpClientFactory.CreateClient(), requestId);
            if (profileWithPrivileges == null)
            {
                return Unauthorized();
            }

            string downstreamUrl = _configUrls.credit + "Credit/users/" + userId;
            var message = new HttpRequestMessage(new HttpMethod(Request.Method), downstreamUrl);
            message.Headers.Add("requestId", requestId);
            var response = await _httpClient.SendAsync(message);

            return await this.GetResultFromResponse<List<CreditDto>>(response);
        }

        [HttpPost("credit")]
        [Produces("application/json")]
        public async Task<IActionResult> CreateCredit(CreateCreditDto createCredit, [FromHeader] string? requestId = null)
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
                authHeader, _configUrls, _httpClientFactory.CreateClient(), requestId);
            if (profileWithPrivileges == null)
            {
                return Unauthorized();
            }

            string downstreamUrl = _configUrls.credit + "Credit";

            createCredit.AccountId = Guid.Parse(userId);
            var json = JsonSerializer.Serialize(createCredit, ControllersUtils.jsonOptions);
            var content = new StringContent(json, Encoding.UTF8, "application/json");

            var message = new HttpRequestMessage(new HttpMethod(Request.Method), downstreamUrl);
            message.Headers.Add("requestId", requestId);
            message.Headers.Add("idempotentKey", Guid.NewGuid().ToString());
            message.Content = content;
            var response = await _httpClient.SendAsync(message);

            return await this.GetResult(response);
        }

        [HttpGet("creditTerms")]
        [Produces("application/json")]
        public async Task<ActionResult<List<CreditTermsDto>>> GetAllCreditTermsDto([FromHeader] string? requestId = null)
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
                authHeader, _configUrls, _httpClientFactory.CreateClient(), requestId);
            if (profileWithPrivileges == null)
            {
                return Unauthorized();
            }

            string downstreamUrl = _configUrls.credit + "CreditTerms";
            var message = new HttpRequestMessage(new HttpMethod(Request.Method), downstreamUrl);
            message.Headers.Add("requestId", requestId);
            var response = await _httpClient.SendAsync(message);

            return await this.GetResultFromResponse<List<CreditTermsDto>>(response);
        }

        [HttpGet("payment/{creditId:guid}")]
        [Produces("application/json")]
        public async Task<ActionResult<List<CreditPaymentDto>>> GetCreditPayments(Guid creditId, [FromHeader] string? requestId = null)
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
                authHeader, _configUrls, _httpClientFactory.CreateClient(), requestId);
            if (profileWithPrivileges == null)
            {
                return Unauthorized();
            }

            string downstreamUrl = _configUrls.credit + "Payment/" + creditId;
            var message = new HttpRequestMessage(new HttpMethod(Request.Method), downstreamUrl);
            message.Headers.Add("requestId", requestId);
            var response = await _httpClient.SendAsync(message);

            return await this.GetResultFromResponse<List<CreditPaymentDto>>(response);
        }

        [HttpDelete("credit/{creditId:guid}")]
        [Produces("application/json")]
        public async Task<IActionResult> DeleteCreditById(Guid creditId, [FromHeader] string? requestId = null)
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
                authHeader, _configUrls, _httpClientFactory.CreateClient(), requestId);
            if (profileWithPrivileges == null)
            {
                return Unauthorized();
            }

            string downstreamUrl = _configUrls.credit + "Credit/" + creditId;
            var message = new HttpRequestMessage(new HttpMethod(Request.Method), downstreamUrl);
            message.Headers.Add("requestId", requestId);
            var response = await _httpClient.SendAsync(message);

            return await this.GetResult(response);
        }
    }
}
