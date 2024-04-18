using System.ComponentModel.DataAnnotations;

namespace Credit.Data.Requests.CreditTerms;

public class UpdateRequest
{
    /// <summary>
    /// Процентная ставка
    /// </summary>
    [Range(0.1, float.MaxValue)]
    public float InterestRate { get; set; }
    
    /// <summary>
    /// Название кредитных условий
    /// </summary>
    public string Title { get; set; }
}