using BFF_client.Api.model;
using BFF_client.Api.model.bill;
using BFF_client.Api.Models;
using BFF_client.Api.Patterns;
using BFF_client.Api.Services.Bill;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Options;
using System.Net.Http.Headers;

namespace BFF_client.Api.Controllers
{
    [Route("api/employers")]
    [ApiController]
    public class EmployerController : ControllerBase
    {
        private readonly HttpClient _httpClient;
        private readonly ConfigUrls _configUrls;
        private readonly IBillService _billService;
        private readonly IHttpClientFactory _httpClientFactory;
        private readonly ICircuitBreakerService _circuitBreakerService;

        public EmployerController(IHttpClientFactory httpClientFactory, IOptions<ConfigUrls> options, IBillService billService, ICircuitBreakerService circuitBreaker)
        {
            _httpClient = httpClientFactory.CreateClient();
            _configUrls = options.Value;
            _billService = billService;
            _httpClientFactory = httpClientFactory;
            _circuitBreakerService = circuitBreaker;
        }

        [HttpGet("{userId:guid}/bills")]
        [Produces("application/json")]
        public async Task<ActionResult<List<ClientBillDto>>> GetAllUserBills(Guid userId, [FromHeader] string? requestId = null)
        {
            var authHeader = Request.Headers.Authorization.FirstOrDefault();
            if (authHeader == null)
            {
                return Unauthorized();
            }
            var employerId = ControllersUtils.GetUserIdByHeader(authHeader);
            if (employerId == null)
            {
                return Unauthorized();
            }
            var profileWithPrivileges = await ControllersUtils.GetProfileWithPrivileges(
                authHeader, _configUrls, _httpClientFactory.CreateClient(), requestId, _circuitBreakerService);
            if (profileWithPrivileges == null)
            {
                return Unauthorized();
            }
            if (profileWithPrivileges.Privileges.Contains(Privileges.BILL_READ_OTHERS) == false)
            {
                return Forbid();
            }

            string downstreamUrl = _configUrls.core + "users/" + userId + "/bills";
            var message = new HttpRequestMessage(new HttpMethod(Request.Method), downstreamUrl);
            message.Headers.Add("requestId", requestId);
            var response = await _httpClient.SendWithRetryAsync(message, _circuitBreakerService, UnstableService.CORE);

            var knownBills = await _billService.GetKnownUserBills(userId);

            return await this.GetResultFromResponse<List<ClientBillDto>>(
                response,
                l => l.ForEach(b => b.isHidden = knownBills.Find(kb => kb.BillId == Guid.Parse(b.Id))?.IsHidden ?? false)
            );
        }
    }
}
