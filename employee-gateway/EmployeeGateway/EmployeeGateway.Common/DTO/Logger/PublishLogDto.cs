namespace EmployeeGateway.Common.DTO.Logger;

public class PublishLogDto
{
    public string RequestId { get; set; }

    public string PublishService { get; set; }

    public RequestDto Request { get; set; }

    public ResponseDto Response { get; set; }

    public string otherInfo { get; set; }
}