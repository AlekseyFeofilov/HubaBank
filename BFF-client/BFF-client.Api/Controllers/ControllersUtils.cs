using BFF_client.Api.model;
using Microsoft.AspNetCore.Mvc;
using System.IdentityModel.Tokens.Jwt;
using System.Net;
using System.Text.Json;

namespace BFF_client.Api.Controllers
{
    public static class ControllersUtils
    {
        public static readonly JsonSerializerOptions jsonOptions = new JsonSerializerOptions()
        {
            PropertyNamingPolicy = JsonNamingPolicy.CamelCase,
            WriteIndented = true
        };

        public static async Task<ActionResult<T>> GetResultFromResponse<T>(this ControllerBase controllerBase, HttpResponseMessage response)
        {
            if (response.IsSuccessStatusCode)
            {
                var downstreamResponse = await response.Content.ReadAsStringAsync();
                var body = JsonSerializer.Deserialize<T>(downstreamResponse, jsonOptions);
                return controllerBase.Ok(body);
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
    }
}
