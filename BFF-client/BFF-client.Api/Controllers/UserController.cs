﻿using BFF_client.Api.model;
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

namespace BFF_client.Api.Controllers
{
    [Route("api/users")]
    [ApiController]
    public class UserController : ControllerBase
    {
        private readonly HttpClient _httpClient;
        private readonly ConfigUrls _configUrls;

        public UserController(IHttpClientFactory httpClientFactory, IOptions<ConfigUrls> options)
        {
            _httpClient = httpClientFactory.CreateClient();
            _configUrls = options.Value;
        }

        [HttpPost("login")]
        [Produces("application/json")]
        public async Task<ActionResult<TokensDto>> PostLogin(CredentialsDto credentials)
        {
            string downstreamUrl = _configUrls.users + "login";

            var json = JsonSerializer.Serialize(credentials, ControllersUtils.jsonOptions);
            var content = new StringContent(json, Encoding.UTF8, "application/json");

            var response = await _httpClient.PostAsync(downstreamUrl, content);

            return await this.GetResultFromResponse<TokensDto>(response);
        }

        [HttpPost("register")]
        [Produces("application/json")]
        public async Task<ActionResult<TokensDto>> PostRegister(RegisterDto register)
        {
            string downstreamUrl = _configUrls.users + "register";
            var json = JsonSerializer.Serialize(register, ControllersUtils.jsonOptions);
            var content = new StringContent(json, Encoding.UTF8, "application/json");

            var response = await _httpClient.PostAsync(downstreamUrl, content);

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

            var responseRole = await _httpClient.PostAsync(downstreamUrlRole, contentRole);

            if (responseRole.IsSuccessStatusCode)
            {
                return tokens;
            }
            return StatusCode(StatusCodes.Status500InternalServerError);
        }

        [HttpPost("refresh")]
        [Produces("application/json")]
        public async Task<ActionResult<TokensDto>> PostRefresh(RefreshTokenDto refreshToken)
        {
            string downstreamUrl = _configUrls.users + "refresh";
            var json = JsonSerializer.Serialize(refreshToken, ControllersUtils.jsonOptions);
            var content = new StringContent(json, Encoding.UTF8, "application/json");

            var response = await _httpClient.PostAsync(downstreamUrl, content);

            return await this.GetResultFromResponse<TokensDto>(response);
        }

        [HttpGet("my")]
        [Produces("application/json")]
        public async Task<ActionResult<ProfileDto>> GetProfile()
        {
            string downstreamUrl = _configUrls.users + "users/my";

            var message = new HttpRequestMessage(new HttpMethod(Request.Method), downstreamUrl);
            var authHeader = Request.Headers.Authorization.FirstOrDefault();
            if (authHeader == null)
            {
                return Unauthorized();
            }
            message.Headers.Authorization = new AuthenticationHeaderValue(
                "Bearer", authHeader.Substring(6)
                );
            var response = await _httpClient.SendAsync(message);

            return await this.GetResultFromResponse<ProfileDto>(response);
        }
    }
}