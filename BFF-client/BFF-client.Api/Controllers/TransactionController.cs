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

        public TransactionController(IHttpClientFactory httpClientFactory, IOptions<ConfigUrls> options, IConfiguration configuration)
        {
            _httpClient = httpClientFactory.CreateClient();
            _configUrls = options.Value;
            _configuration = configuration;
            _httpClientFactory = httpClientFactory;
        }

        /*[HttpGet("{billId:guid}/transactions")]
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
            var profileWithPrivileges = await ControllersUtils.GetProfileWithPrivileges(authHeader, _configUrls, _httpClientFactory.CreateClient());
            if (profileWithPrivileges == null)
            {
                return Unauthorized();
            }
            if (profileWithPrivileges.Privileges.Contains(Privileges.TRANSACTION_READ) == false)
            {
                return Forbid();
            }

            string downstreamUrl = _configUrls.core + "users/" + userId + "/bills/" + billId + "/transactions";
            var message = new HttpRequestMessage(new HttpMethod(Request.Method), downstreamUrl);
            message.Headers.Authorization = new AuthenticationHeaderValue(
                "Bearer", authHeader.Substring(6)
                );
            var response = await _httpClient.SendAsync(message);

            return await this.GetResultFromResponse<List<TransactionDto>>(response);
        }*/

        [HttpPost("tobill")]
        [Produces("application/json")]
        public async Task<IActionResult> MakeToBillTransaction(TransactionToBillDto transactionCreation)
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
            var profileWithPrivileges = await ControllersUtils.GetProfileWithPrivileges(authHeader, _configUrls, _httpClientFactory.CreateClient());
            if (profileWithPrivileges == null)
            {
                return Unauthorized();
            }
            if (profileWithPrivileges.Privileges.Contains(Privileges.TRANSACTION_WRITE) == false)
            {
                return StatusCode(StatusCodes.Status403Forbidden);
            }
            var IsBillBelongToUser = await ControllersUtils.IsBillBelongToUser(userId, transactionCreation.SourceBillId, _configUrls, _httpClientFactory.CreateClient());
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
        public async Task<IActionResult> GiveMoneyToBillTransaction(TransactionGiveMoneyDto transactionCreation)
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
            var profileWithPrivileges = await ControllersUtils.GetProfileWithPrivileges(authHeader, _configUrls, _httpClientFactory.CreateClient());
            if (profileWithPrivileges == null)
            {
                return Unauthorized();
            }
            if (profileWithPrivileges.Privileges.Contains(Privileges.TRANSACTION_WRITE) == false)
            {
                return StatusCode(StatusCodes.Status403Forbidden);
            }
            var IsBillBelongToUser = await ControllersUtils.IsBillBelongToUser(userId, transactionCreation.BillId, _configUrls, _httpClientFactory.CreateClient());
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
        public async Task<IActionResult> WithdrawMoneyFromBillTransaction(TransactionWithdrawMoneyDto transactionCreation)
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
            var profileWithPrivileges = await ControllersUtils.GetProfileWithPrivileges(authHeader, _configUrls, _httpClientFactory.CreateClient());
            if (profileWithPrivileges == null)
            {
                return Unauthorized();
            }
            if (profileWithPrivileges.Privileges.Contains(Privileges.TRANSACTION_WRITE) == false)
            {
                return StatusCode(StatusCodes.Status403Forbidden);
            }
            var IsBillBelongToUser = await ControllersUtils.IsBillBelongToUser(userId, transactionCreation.BillId, _configUrls, _httpClientFactory.CreateClient());
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
