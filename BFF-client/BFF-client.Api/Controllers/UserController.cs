using BFF_client.Api.model;
using Microsoft.AspNetCore.Http;
using Microsoft.AspNetCore.Mvc;
using Microsoft.OpenApi.Extensions;
using System.Net.Http;
using System.Net;
using System.Text.Json;
using System.Text;
using Microsoft.Win32;
using Microsoft.AspNetCore.Authorization;
using Microsoft.Extensions.Options;
using System.Net.Http.Headers;
using BFF_client.Api.model.user;
using Microsoft.AspNetCore.Http.Json;
using BFF_client.Api.Services.User;
using static BFF_client.Api.Controllers.ControllersUtils;
using Google.Apis.Auth.OAuth2;
using FirebaseAdmin.Messaging;
using BFF_client.Api.Patterns;

namespace BFF_client.Api.Controllers
{
    [Route("api/users")]
    [ApiController]
    public class UserController : ControllerBase
    {
        private readonly HttpClient _httpClient;
        private readonly ConfigUrls _configUrls;
        private readonly IUserService _userService;
        private readonly IHttpClientFactory _httpClientFactory;
        private readonly ICircuitBreakerService _circuitBreakerService;

        public UserController(
            IHttpClientFactory httpClientFactory, 
            IOptions<ConfigUrls> options, 
            IUserService userService,
            ICircuitBreakerService circuitBreaker)
        {
            _httpClient = httpClientFactory.CreateClient();
            _configUrls = options.Value;
            _userService = userService;
            _httpClientFactory = httpClientFactory;
            _circuitBreakerService = circuitBreaker;
        }

        [HttpPost("login")]
        [Produces("application/json")]
        public async Task<ActionResult<TokensDto>> PostLogin(CredentialsDto credentials, [FromHeader] string? requestId = null)
        {
            string downstreamUrl = _configUrls.users + "jwt";

            var content = new StringContent(credentials.JwtSOO, Encoding.UTF8, "application/json");

            var message = new HttpRequestMessage(new HttpMethod(Request.Method), downstreamUrl);
            message.Headers.Add("requestId", requestId);
            message.Headers.Add("idempotentKey", Guid.NewGuid().ToString());
            message.Content = content;
            var response = await _httpClient.SendWithRetryAsync(message, _circuitBreakerService, UnstableService.USERS);

            if (response.IsSuccessStatusCode)
            {
                var downstreamResponse = await response.Content.ReadAsStringAsync();
                var body = JsonSerializer.Deserialize<TokensDto>(downstreamResponse, jsonOptions);
                var userId = ControllersUtils.GetUserIdByHeader(body.accessToken);
                await _userService.SetMessagingToken(Guid.Parse(userId), credentials.MessagingToken);
            }

            return await this.GetResultFromResponse<TokensDto>(response);
        }

        [HttpPost("register")]
        [Produces("application/json")]
        public async Task<ActionResult<TokensDto>> PostRegister(RegisterDto register, [FromHeader] string? requestId = null)
        {
            string downstreamUrl = _configUrls.users + "register";
            var json = JsonSerializer.Serialize(register, ControllersUtils.jsonOptions);
            var content = new StringContent(json, Encoding.UTF8, "application/json");

            var message = new HttpRequestMessage(new HttpMethod(Request.Method), downstreamUrl);
            message.Headers.Add("requestId", requestId);
            message.Headers.Add("idempotentKey", Guid.NewGuid().ToString());
            message.Content = content;
            var response = await _httpClient.SendWithRetryAsync(message, _circuitBreakerService, UnstableService.USERS);

            var tokens = await this.GetResultFromResponse<TokensDto>(response);

            if (!response.IsSuccessStatusCode)
            {
                return tokens;
            }

            var downstreamResponse = await response.Content.ReadAsStringAsync();
            var body = JsonSerializer.Deserialize<TokensDto>(downstreamResponse, ControllersUtils.jsonOptions);
            var userId = ControllersUtils.GetUserIdByHeader(body.accessToken);

            string downstreamUrlRole = _configUrls.users + "user/" + userId + "/roles";
            var clientRole = new EditUserRoleDto { names = new List<string>() { "CLIENT" } };
            var jsonRole = JsonSerializer.Serialize(clientRole, ControllersUtils.jsonOptions);
            var contentRole = new StringContent(jsonRole, Encoding.UTF8, "application/json");

            var messageRole = new HttpRequestMessage(new HttpMethod(Request.Method), downstreamUrlRole);
            messageRole.Headers.Add("requestId", requestId);
            messageRole.Headers.Add("idempotentKey", Guid.NewGuid().ToString());
            messageRole.Content = contentRole;
            var responseRole = await _httpClient.SendWithRetryAsync(messageRole, _circuitBreakerService, UnstableService.USERS);

            if (responseRole.IsSuccessStatusCode)
            {
                await _userService.SetMessagingToken(Guid.Parse(userId), register.MessagingToken);
                return tokens;
            }
            return StatusCode(StatusCodes.Status500InternalServerError);
        }

        [HttpPost("refresh")]
        [Produces("application/json")]
        public async Task<ActionResult<TokensDto>> PostRefresh(RefreshTokenDto refreshToken, [FromHeader] string? requestId = null)
        {
            string downstreamUrl = _configUrls.users + "refresh";
            var json = JsonSerializer.Serialize(refreshToken, ControllersUtils.jsonOptions);
            var content = new StringContent(json, Encoding.UTF8, "application/json");

            var message = new HttpRequestMessage(new HttpMethod(Request.Method), downstreamUrl);
            message.Headers.Add("requestId", requestId);
            message.Headers.Add("idempotentKey", Guid.NewGuid().ToString());
            message.Content = content;
            var response = await _httpClient.SendWithRetryAsync(message, _circuitBreakerService, UnstableService.USERS);

            return await this.GetResultFromResponse<TokensDto>(response);
        }

        [HttpGet("my")]
        [Produces("application/json")]
        public async Task<ActionResult<ProfileDto>> GetProfile([FromHeader] string? requestId = null)
        {
            string downstreamUrl = _configUrls.users + "users/my";

            var message = new HttpRequestMessage(new HttpMethod(Request.Method), downstreamUrl);
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
            message.Headers.Authorization = new AuthenticationHeaderValue(
                "Bearer", authHeader.Substring(6)
                );
            message.Headers.Add("requestId", requestId);
            var response = await _httpClient.SendWithRetryAsync(message, _circuitBreakerService, UnstableService.USERS);

            var isDarkTheme = await _userService.GetIsDarkTheme(Guid.Parse(userId));

            return await this.GetResultFromResponse<ProfileDto>(response, p => p.IsDarkTheme = isDarkTheme);
        }

        [HttpPost("theme")]
        [Produces("application/json")]
        public async Task<IActionResult> ChangeTheme([FromBody] bool isDarkTheme, [FromHeader] string? requestId = null)
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

            await _userService.SetIsDarkTheme(Guid.Parse(userId), isDarkTheme);

            return Ok();
        }

        [HttpPost("logout")]
        [Produces("application/json")]
        public async Task<IActionResult> logout()
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

            await _userService.SetMessagingToken(Guid.Parse(userId), null);

            return Ok();
        }
    }
}
