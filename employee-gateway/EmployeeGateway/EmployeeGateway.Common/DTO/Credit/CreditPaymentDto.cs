using System.ComponentModel.DataAnnotations;
using EmployeeGateway.Common.Enum;

namespace EmployeeGateway.Common.DTO.Credit;

public class CreditPaymentDto
{
    public Guid Id { get; set; }
    
    public Guid CreditId { get; set; }
    
    public PaymentStatus PaymentStatus { get; set; } 
    
    [DataType(DataType.Date)]
    public DateTime PaymentDay { get; set; }
    
    public int PaymentAmount { get; set; }
    
    // процентная ставка
    public int Interest { get; set; }
    
    // Задолженность по выплате (без процентов)
    public int Arrears { get; set; }
    
    // Задолженность по процентам выплаты
    public int ArrearsInterest { get; set; }
}