namespace EmployeeGateway.Common.DTO.Logger;

public class RequestDto
{
    public string Url { get; set; }

    public string Method { get; set; }

    public Dictionary<string, string> Headers { get; set; }

    public string Body { get; set; }
}