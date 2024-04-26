using System.Text.Json.Serialization;
using Credit.Primitives;

namespace Credit.Data.Responses;

public class CreditResponse
{ 
    /// <summary>
    /// Id кредита
    /// </summary>
    public Guid Id { get; set; }
    
    /// <summary>
    /// Id пользователя, на которого оформляется кредит
    /// </summary>
    public Guid AccountId { get; set; }
    
    /// <summary>
    /// Id счёта пользователя, с которого будут списываться средтсва
    /// </summary>
    public Guid BillId { get; set; }
    
    /// <summary>
    /// Дата окончания кредита
    /// </summary>
    [JsonConverter(typeof(DateOnlyJsonConverter))]
    public DateOnly CompletionDate { get; set; } 
    
    /// <summary>
    /// Дата последнего обновления суммы задолженности
    /// </summary>
    [JsonConverter(typeof(DateOnlyJsonConverter))]
    public DateOnly LastArrearsUpdate { get; set; } 
    
    /// <summary>
    /// Процентная ставка по кредиту
    /// </summary>
    public float InterestRate { get; set; }
    
    /// <summary>
    /// День месяца, в который списывается ежемесячный платёж
    /// </summary>
    public int CollectionDay { get; set; }
    
    /// <summary>
    /// Размер кредита в копейках
    /// </summary>
    public long Principal { get; set; }
    
    /// <summary>
    /// Текущий размер кредита в копейках
    /// </summary>
    public long CurrentAccountsPayable { get; set; }
    
    /// <summary>
    /// Задолженность по процентам
    /// </summary>
    public long ArrearsInterest { get; set; }
    
    /// <summary>
    /// Задолженность по выплатам
    /// </summary>
    public long Arrears { get; set; }
    
    /// <summary>
    /// Пеня
    /// </summary>
    public long Fine { get; set; }
}