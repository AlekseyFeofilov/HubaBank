using System.ComponentModel.DataAnnotations;

namespace Credit.Data.Requests.CreditTerms;

public class CreateRequest
{
    /// <summary>
    /// Id кредитного тарифа
    /// </summary>
    public Guid Id { get; set; }
    
    /// <summary>
    /// Процентная ставка
    /// </summary>
    [Required]
    [Range(0.1, float.MaxValue)]
    public float InterestRate { get; set; }
}