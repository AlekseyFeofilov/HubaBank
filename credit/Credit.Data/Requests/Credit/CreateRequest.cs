using System.ComponentModel.DataAnnotations;

namespace Credit.Data.Requests.Credit;

public class CreateRequest
{
    /// <summary>
    /// Id пользователя, на которого оформляется кредит
    /// </summary>
    [Required]
    public Guid AccountId { get; set; }
    
    /// <summary>
    /// Размер кредита в копейках
    /// </summary>
    [Required]
    public long Principal { get; set; }
    
    /// <summary>
    /// Дата окончания кредита
    /// </summary>
    [Required]
    public DateOnly CompletionDate { get; set; } 
    
    /// <summary>
    /// Процентная ставка по кредиту
    /// </summary>
    [Range(0.01f, 100)]
    public float? InterestRate { get; set; }
    
    /// <summary>
    /// Id предсозданных кредитных условий (если указан, то InterestRate будет проигнорирован) 
    /// </summary>
    public Guid? CreditTermsId { get; set; }
    
    // public CreditType Type { get; set; }
}