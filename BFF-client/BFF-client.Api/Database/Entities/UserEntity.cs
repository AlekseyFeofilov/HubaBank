namespace BFF_client.Api.Database.Entities
{
    public class UserEntity
    {
        public Guid Id { get; set; }

        public bool IsDarkTheme { get; set; }

        public string? MessagingToken { get; set; } = null;
    }
}
