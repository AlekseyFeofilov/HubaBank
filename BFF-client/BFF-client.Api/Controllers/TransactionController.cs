using BFF_client.Api.model;
using BFF_client.Api.model.bill;
using BFF_client.Api.Models.bill;
using BFF_client.Api.Models;
using BFF_client.Api.Services.Bill;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.Extensions.Options;
using System.Text.Json;
using System.Text;
using RabbitMQ.Client;
using BFF_client.Api.Patterns;

namespace BFF_client.Api.Controllers
{
    [Route("api/transactions")]
    [ApiController]
    public class TransactionController : ControllerBase
    {
        private readonly HttpClient _httpClient;
        private readonly ConfigUrls _configUrls;
        private readonly IConfiguration _configuration;
        private readonly IHttpClientFactory _httpClientFactory;
        private readonly ICircuitBreakerService _circuitBreakerService;

        public TransactionController(IHttpClientFactory httpClientFactory, IOptions<ConfigUrls> options, IConfiguration configuration, ICircuitBreakerService circuitBreaker)
        {
            _httpClient = httpClientFactory.CreateClient();
            _configUrls = options.Value;
            _configuration = configuration;
            _httpClientFactory = httpClientFactory;
            _circuitBreakerService = circuitBreaker;
        }

        [HttpPost("tobill")]
        [Produces("application/json")]
        public async Task<IActionResult> MakeToBillTransaction(TransactionToBillDto transactionCreation, [FromHeader] string? requestId = null)
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
            if (profileWithPrivileges.Privileges.Contains(Privileges.TRANSACTION_WRITE) == false)
            {
                return StatusCode(StatusCodes.Status403Forbidden);
            }
            var IsBillBelongToUser = await ControllersUtils.IsBillBelongToUser(userId, transactionCreation.SourceBillId, _configUrls, _httpClientFactory.CreateClient(), requestId, _circuitBreakerService);
            if (IsBillBelongToUser == false)
            {
                return StatusCode(StatusCodes.Status403Forbidden);
            }

            using var connection = CommonUtils.CreateConnection(_configuration);
            using var channel = connection.CreateModel();
            channel.QueueDeclare("transfer_to_bill_request_queue", true, false, false, new Dictionary<string, object>() { { "x-queue-type", "quorum" } });

            var json = JsonSerializer.Serialize(transactionCreation, ControllersUtils.jsonOptions);
            var body = Encoding.UTF8.GetBytes(json);

            channel.BasicPublish("transfer_request_exchange", "to_bill", null, body);

            return Ok();
        }

        [HttpPost("deposit")]
        [Produces("application/json")]
        public async Task<IActionResult> GiveMoneyToBillTransaction(TransactionGiveMoneyDto transactionCreation, [FromHeader] string? requestId = null)
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
            if (profileWithPrivileges.Privileges.Contains(Privileges.TRANSACTION_WRITE) == false)
            {
                return StatusCode(StatusCodes.Status403Forbidden);
            }
            var IsBillBelongToUser = await ControllersUtils.IsBillBelongToUser(userId, transactionCreation.BillId, _configUrls, _httpClientFactory.CreateClient(), requestId, _circuitBreakerService);
            if (IsBillBelongToUser == false)
            {
                return StatusCode(StatusCodes.Status403Forbidden);
            }

            using var connection = CommonUtils.CreateConnection(_configuration);
            using var channel = connection.CreateModel();
            channel.QueueDeclare("deposit_request_queue", true, false, false, new Dictionary<string, object>() { { "x-queue-type", "quorum" } });

            var json = JsonSerializer.Serialize(transactionCreation, ControllersUtils.jsonOptions);
            var body = Encoding.UTF8.GetBytes(json);

            channel.BasicPublish("transfer_request_exchange", "deposit", null, body);

            return Ok();
        }

        [HttpPost("withdrawal")]
        [Produces("application/json")]
        public async Task<IActionResult> WithdrawMoneyFromBillTransaction(TransactionWithdrawMoneyDto transactionCreation, [FromHeader] string? requestId = null)
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
            if (profileWithPrivileges.Privileges.Contains(Privileges.TRANSACTION_WRITE) == false)
            {
                return StatusCode(StatusCodes.Status403Forbidden);
            }
            var IsBillBelongToUser = await ControllersUtils.IsBillBelongToUser(userId, transactionCreation.BillId, _configUrls, _httpClientFactory.CreateClient(), requestId, _circuitBreakerService);
            if (IsBillBelongToUser == false)
            {
                return StatusCode(StatusCodes.Status403Forbidden);
            }

            using var connection = CommonUtils.CreateConnection(_configuration);
            using var channel = connection.CreateModel();
            channel.QueueDeclare("withdrawal_request_queue", true, false, false, new Dictionary<string, object>() { { "x-queue-type", "quorum" } });

            var json = JsonSerializer.Serialize(transactionCreation, ControllersUtils.jsonOptions);
            var body = Encoding.UTF8.GetBytes(json);

            channel.BasicPublish("transfer_request_exchange", "withdrawal", null, body);

            return Ok();
        }
    }
}
