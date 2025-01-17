using System.ComponentModel.DataAnnotations;
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
    /// Id счёта пользователя, с которого будут списываться средтсва
    /// </summary>
    [Required]
    public Guid BillId { get; set; }
    
    /// <summary>
    /// Процентная ставка по кредиту
    /// </summary>
    [Range(0.1, float.MaxValue)]
    public float? InterestRate { get; set; }
    
    /// <summary>
    /// Id предсозданных кредитных условий (если указан, то InterestRate будет проигнорирован) 
    /// </summary>
    public Guid? CreditTermsId { get; set; }
    
    [JsonIgnore]
    public long? ArrearsInterest { get; set; }
    
    [JsonIgnore]
    public long? Arrears { get; set; }
    
    [JsonIgnore]
    public long? Fine { get; set; }
    
    [JsonIgnore]
    public long? CurrentAccountPayable { get; set; }
}