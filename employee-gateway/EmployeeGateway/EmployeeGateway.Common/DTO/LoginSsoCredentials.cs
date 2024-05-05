using System.ComponentModel.DataAnnotations;

namespace EmployeeGateway.Common.DTO;

public class LoginSsoCredentials
{
    [Required]
    public string JwtSso { get; set; }
    
    [Required]
    public Guid MessagingToken { get; set; }
}