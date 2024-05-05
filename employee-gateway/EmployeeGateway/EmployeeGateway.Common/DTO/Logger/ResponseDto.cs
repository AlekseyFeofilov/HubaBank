namespace EmployeeGateway.Common.DTO.Logger;

public class ResponseDto
{
    public int Status { get; set; }

    public Dictionary<string, string> Headers { get; set; }

    public string Body { get; set; }
}