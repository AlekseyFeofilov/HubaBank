using System.ComponentModel.DataAnnotations;

namespace EmployeeGateway.Common.DTO;

public class LoginSsoCredentials
{
    [Required]
    public string JwtSso { get; set; }
    
    [Required]
    public string MessagingToken { get; set; }
}