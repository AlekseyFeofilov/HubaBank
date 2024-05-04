namespace BFF_client.Api.Models.Logger
{
    public class ResponseDto
    {
        public int Status { get; set; }

        public Dictionary<string, string> Headers { get; set; }

        public string Body { get; set; }
    }
}
