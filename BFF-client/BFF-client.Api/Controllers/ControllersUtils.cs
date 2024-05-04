using BFF_client.Api.model;
using BFF_client.Api.Models.user;
using Microsoft.AspNetCore.Mvc;
using System.IdentityModel.Tokens.Jwt;
using System.Net;
using System.Net.Http.Headers;
using System.Net.Http;
using System.Text.Json;
using static BFF_client.Api.Controllers.ControllersUtils;
using BFF_client.Api.model.bill;

namespace BFF_client.Api.Controllers
{
    public static class ControllersUtils
    {
        public static readonly JsonSerializerOptions jsonOptions = new JsonSerializerOptions()
        {
            PropertyNamingPolicy = JsonNamingPolicy.CamelCase,
            WriteIndented = true
        };

        public delegate void Mapping<T>(T entity);

        public static async Task<ActionResult<T>> GetResultFromResponse<T>(this ControllerBase controllerBase, HttpResponseMessage response, Mapping<T>? mapping = null)
        {
            if (response.IsSuccessStatusCode)
            {
                try
                {
                    var downstreamResponse = await response.Content.ReadAsStringAsync();
                    var body = JsonSerializer.Deserialize<T>(downstreamResponse, jsonOptions);
                    if (mapping != null && body != null)
                    {
                        mapping(body);
                    }
                    return controllerBase.Ok(body);
                } catch (Exception ex)
                {
                    Console.WriteLine(ex);
                    return controllerBase.StatusCode(500);
                }
            }
            else
            {
                if (response.StatusCode == HttpStatusCode.BadRequest)
                {
                    var errorResponse = await response.Content.ReadAsStringAsync();
                    return controllerBase.BadRequest(errorResponse);
                }
                else if (response.StatusCode == HttpStatusCode.Unauthorized)
                {
                    return controllerBase.Unauthorized();
                }
                else if (response.StatusCode == HttpStatusCode.NotFound)
                {
                    return controllerBase.NotFound();
                }
                else
                {
                    return controllerBase.StatusCode((int)response.StatusCode);
                }
            }
        }

        public static async Task<IActionResult> GetResult(this ControllerBase controllerBase, HttpResponseMessage response)
        {
            if (response.IsSuccessStatusCode)
            {
                return controllerBase.Ok();
            }
            else
            {
                if (response.StatusCode == HttpStatusCode.BadRequest)
                {
                    var errorResponse = await response.Content.ReadAsStringAsync();
                    return controllerBase.BadRequest(errorResponse);
                }
                else if (response.StatusCode == HttpStatusCode.Unauthorized)
                {
                    return controllerBase.Unauthorized();
                }
                else if (response.StatusCode == HttpStatusCode.NotFound)
                {
                    return controllerBase.NotFound();
                }
                else
                {
                    return controllerBase.StatusCode((int)response.StatusCode);
                }
            }
        }

        public static string? GetUserIdByHeader(string fullAuthHeader)
        {
            var token = fullAuthHeader.Substring(7);
            var handler = new JwtSecurityTokenHandler();
            var jwtSecurityToken = handler.ReadJwtToken(token);
            return jwtSecurityToken.Claims.FirstOrDefault(c => c.Type == "id")?.Value;
        }

        public static async Task<ProfileWithPrivileges?> GetProfileWithPrivileges(
            string authHeader,
            ConfigUrls configUrls, 
            HttpClient client,
            string? requestId
            )
        {
            string profileUrl = configUrls.users + "users/my";

            var message = new HttpRequestMessage(HttpMethod.Get, profileUrl);
            var userId = GetUserIdByHeader(authHeader);
            if (userId == null)
            {
                return null;
            }
            message.Headers.Authorization = new AuthenticationHeaderValue(
            "Bearer", authHeader.Substring(6)
                );
            message.Headers.Add("requestId", requestId);
            var profileResponse = await client.SendAsync(message);
            if (profileResponse.IsSuccessStatusCode)
            {
                var downstreamResponse = await profileResponse.Content.ReadAsStringAsync();
                var body = JsonSerializer.Deserialize<ProfileWithPrivileges>(downstreamResponse, jsonOptions);
                return body;
            }
            else { return null; }
        }

        public static async Task<bool> IsBillBelongToUser(string userId, Guid billId, ConfigUrls configUrls, HttpClient client)
        {
            string downstreamUrl = configUrls.core + "bills/" + billId.ToString();

            var message = new HttpRequestMessage(HttpMethod.Get, downstreamUrl);
            var response = await client.SendAsync(message);
            if (response.IsSuccessStatusCode)
            {
                var downstreamResponse = await response.Content.ReadAsStringAsync();
                var body = JsonSerializer.Deserialize<BillDto>(downstreamResponse, jsonOptions);
                if (body != null)
                {
                    return userId == body.UserId;
                }
                return false;
            } 
            else 
            { 
                return false; 
            }
        }
    }
}
