﻿namespace BFF_client.Api.Services.User
{
    public interface IUserService
    {
        Task<bool> GetIsDarkTheme(Guid userId);

        Task SetIsDarkTheme(Guid userId, bool isDarkTheme);

        Task SetMessagingToken(Guid userId, string? token);

        Task<string?> GetMessagingToken(Guid userId);
    }
}
