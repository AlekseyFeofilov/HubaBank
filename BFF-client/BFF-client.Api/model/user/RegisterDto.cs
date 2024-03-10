namespace BFF_client.Api.model.user
{
    public class RegisterDto
    {
        public FullNameDto FullName { get; set; }

        public string password { get; set; }

        public string phone { get; set; }
    }
}
