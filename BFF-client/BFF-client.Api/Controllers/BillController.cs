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

namespace BFF_client.Api.Controllers
{
    [Route("api/bills")]
    [ApiController]
    public class BillController : ControllerBase
    {
        private readonly HttpClient _httpClient;
        private readonly ConfigUrls _configUrls;

        public BillController(IHttpClientFactory httpClientFactory, IOptions<ConfigUrls> options)
        {
            _httpClient = httpClientFactory.CreateClient();
            _configUrls = options.Value;
        }

        [HttpGet("")]
        [Produces("application/json")]
        public async Task<ActionResult<List<ClientBillDto>>> GetAllBills()
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
            string downstreamUrl = _configUrls.core + "users/" + userId + "/bills";

            var message = new HttpRequestMessage(new HttpMethod(Request.Method), downstreamUrl);
            message.Headers.Authorization = new AuthenticationHeaderValue(
                "Bearer", authHeader.Substring(6)
                );
            var response = await _httpClient.SendAsync(message);

            return await this.GetResultFromResponse<List<ClientBillDto>>(response);
        }

        [HttpPost("")]
        [Produces("application/json")]
        public async Task<ActionResult<ClientBillDto>> CreateBill()
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
            string downstreamUrl = _configUrls.core + "users/" + userId + "/bills";

            var message = new HttpRequestMessage(new HttpMethod(Request.Method), downstreamUrl);
            message.Headers.Authorization = new AuthenticationHeaderValue(
                "Bearer", authHeader.Substring(6)
                );
            var response = await _httpClient.SendAsync(message);

            return await this.GetResultFromResponse<ClientBillDto>(response);
        }

        [HttpGet("{billId:guid}")]
        [Produces("application/json")]
        public async Task<ActionResult<ClientBillDto>> GetBillById(Guid billId)
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
            string downstreamUrl = _configUrls.core + "users/" + userId + "/bills/" + billId;

            var message = new HttpRequestMessage(new HttpMethod(Request.Method), downstreamUrl);
            message.Headers.Authorization = new AuthenticationHeaderValue(
                "Bearer", authHeader.Substring(6)
                );
            var response = await _httpClient.SendAsync(message);

            return await this.GetResultFromResponse<ClientBillDto>(response);
        }

        [HttpDelete("{billId:guid}")]
        [Produces("application/json")]
        public async Task<IActionResult> DeleteBill(Guid billId)
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
            string downstreamUrl = _configUrls.core + "users/" + userId + "/bills/" + billId;

            var message = new HttpRequestMessage(new HttpMethod(Request.Method), downstreamUrl);
            message.Headers.Authorization = new AuthenticationHeaderValue(
                "Bearer", authHeader.Substring(6)
                );
            var response = await _httpClient.SendAsync(message);

            return await this.GetResult(response);
        }

        [HttpGet("{billId:guid}/transactions")]
        [Produces("application/json")]
        public async Task<ActionResult<List<TransactionDto>>> GetBillHistory(Guid billId)
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
            string downstreamUrl = _configUrls.core + "users/" + userId + "/bills/" + billId + "/transactions";

            var message = new HttpRequestMessage(new HttpMethod(Request.Method), downstreamUrl);
            message.Headers.Authorization = new AuthenticationHeaderValue(
                "Bearer", authHeader.Substring(6)
                );
            var response = await _httpClient.SendAsync(message);

            return await this.GetResultFromResponse<List<TransactionDto>>(response);
        }

        [HttpPost("{billId:guid}/transactions")]
        [Produces("application/json")]
        public async Task<IActionResult> ChangeBillBalance(Guid billId, TransactionCreationDto transactionCreation)
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
            string downstreamUrl = _configUrls.core + "users/" + userId + "/bills/" + billId + "/transactions";

            var message = new HttpRequestMessage(new HttpMethod(Request.Method), downstreamUrl);
            message.Headers.Authorization = new AuthenticationHeaderValue(
                "Bearer", authHeader.Substring(6)
                );
            var json = JsonSerializer.Serialize(transactionCreation, ControllersUtils.jsonOptions);
            message.Content = new StringContent(json, Encoding.UTF8, "application/json");
            var response = await _httpClient.SendAsync(message);

            return await this.GetResult(response);
        }
    }
}
