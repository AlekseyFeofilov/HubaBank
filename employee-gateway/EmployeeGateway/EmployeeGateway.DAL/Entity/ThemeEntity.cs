using System.ComponentModel.DataAnnotations;
using EmployeeGateway.Common.DTO;

namespace EmployeeGateway.DAL.Entity;

public class ThemeEntity
{
    [Key]
    public Guid Id { get; set; }
    [Required]
    public Guid UserId { get; set; }
    [Required]
    public ThemeSystem ThemeSystem { get; set; }
}