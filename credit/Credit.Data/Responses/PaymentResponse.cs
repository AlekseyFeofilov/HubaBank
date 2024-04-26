using Credit.Primitives;

namespace Credit.Data.Responses;

public class PaymentResponse
{
    /// <summary>
    /// Id выплаты
    /// </summary>
    public Guid Id { get; set; }
    
    /// <summary>
    /// Id кредита выплаты
    /// </summary>
    public Guid CreditId { get; set; }
    
    /// <summary>
    /// Статус выплаты
    /// </summary>
    public PaymentStatus PaymentStatus { get; set; }
    
    /// <summary>
    /// Дата списания по выплате
    /// </summary>
    public DateOnly PaymentDay { get; set; }
    
    /// <summary>
    /// Размер выплаты в копейках
    /// </summary>
    public long PaymentAmount { get; set; }
    
    /// <summary>
    /// Процентная ставка выплаты
    /// </summary>
    public long Interest { get; set; }
    
    /// <summary>
    /// Задолженность по выплате (без процентов)
    /// </summary>
    public long Arrears { get; set; }
    
    /// <summary>
    /// Задолженность по процентам выплаты
    /// </summary>
    public long ArrearsInterest { get; set; }
}