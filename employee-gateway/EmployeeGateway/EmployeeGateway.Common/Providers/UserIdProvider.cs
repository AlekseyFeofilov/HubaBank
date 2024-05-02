using EmployeeGateway.Common.System;
using Microsoft.AspNetCore.SignalR;

namespace EmployeeGateway.Common.Providers;

public class UserIdProvider
{
    public string? GetUserId(HubConnectionContext connection)
    {
        var authHeader = connection.GetHttpContext()?.Request.Headers.Authorization.FirstOrDefault();
        if (authHeader == null) { return null; }
        return UtilsService.GetUserIdByHeader(authHeader);
    }

}