using System.ComponentModel.DataAnnotations;

namespace EmployeeGateway.Models;

public class LoginCredentials
{
    [Required]
    public string JwtSso { get; set; }
}