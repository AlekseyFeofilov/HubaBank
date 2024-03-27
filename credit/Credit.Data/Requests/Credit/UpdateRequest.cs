using System.Text.Json.Serialization;

namespace Credit.Data.Requests.Credit;

public class UpdateRequest
{
    /// <summary>
    /// Срок кредита
    /// </summary>
    [JsonConverter(typeof(DateOnlyJsonConverter))]
    public DateOnly? CompletionDate { get; set; } 
    
    /// <summary>
    /// Процентная ставка по кредиту
    /// </summary>
    public float? InterestRate { get; set; }
    
    /// <summary>
    /// Id предсозданных кредитных условий (если указан, то InterestRate будет проигнорирован) 
    /// </summary>
    public Guid? CreditTermsId { get; set; }
}