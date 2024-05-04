using System.ComponentModel.DataAnnotations;
using System.Diagnostics.CodeAnalysis;

namespace EmployeeGateway.Models;

public class RegisterCredential
{
    [Required]
    [MinLength(1)]
    public FullName FullName { get; set; }
    
    [Required]
    [MinLength(6)]
    public string Password { get; set; }
    
    [Required]
    [Phone]
    public string Phone { get; set; }
    
    [Required]
    public string MessagingToken { get; set; }
}

public class FullName
{
    [Required]
    [MinLength(1)]
    public string FirstName { get; set; }
    [Required]
    [MinLength(1)]
    public string SecondName { get; set; }
    [Required]
    [MaybeNull]
    public string ThirdName { get; set; }
}