using System.IdentityModel.Tokens.Jwt;
using System.Net;
using System.Net.Http.Headers;
using System.Text.Json;
using EmployeeGateway.BL;
using EmployeeGateway.Common.DTO;
using EmployeeGateway.Common.DTO.Bill;
using Microsoft.AspNetCore.Mvc;

namespace EmployeeGateway.Common.System;

public static class UtilsService
{
    public static readonly JsonSerializerOptions jsonOptions = new JsonSerializerOptions
    {
        PropertyNamingPolicy = JsonNamingPolicy.CamelCase,
        WriteIndented = true
    };

    public static async Task<ActionResult<T>> GetResultFromResponse<T>(this ControllerBase controllerBase,
        HttpResponseMessage response)
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
    
    public static async Task<bool> IsBillBelongToUser(string userId, Guid billId, UrlsMicroserviceOptions configUrls, HttpClient client)
    {
        string downstreamUrl = configUrls.CoreUrl + "bills/" + billId.ToString();

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
    
    public static async Task<ProfileWithPrivileges?> GetProfileWithPrivileges(
        string authHeader,
        UrlsMicroserviceOptions configUrls, 
        HttpClient client
    )
    {
        string profileUrl = configUrls.AuthUrl + "users/my";

        var message = new HttpRequestMessage(HttpMethod.Get, profileUrl);
        var userId = GetUserIdByHeader(authHeader);
        if (userId == null)
        {
            return null;
        }
        message.Headers.Authorization = new AuthenticationHeaderValue(
            "Bearer", authHeader.Substring(6)
        );
        var profileResponse = await client.SendAsync(message);
        if (profileResponse.IsSuccessStatusCode)
        {
            var downstreamResponse = await profileResponse.Content.ReadAsStringAsync();
            var body = JsonSerializer.Deserialize<ProfileWithPrivileges>(downstreamResponse, jsonOptions);
            return body;
        }
        else { return null; }
    }



}