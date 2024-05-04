using System.ComponentModel.DataAnnotations;

namespace EmployeeGateway.Common.DTO.Credit;

public class CreditDto
{
    public Guid Id { get; set; }
    
    public Guid AccountId { get; set; }
    
    public Guid BillId { get; set; }
    
    // Дата окончания кредита
    [DataType(DataType.Date)]
    public DateTime CompletionDate { get; set; }
    
    // Дата последнего обновления суммы задолженности
    [DataType(DataType.Date)]
    public DateTime LastArrearsUpdate { get; set; }
    
    // Процентная ставка по кредиту
    public float InterestRate { get; set; }
    
    // День месяца, в который списывается ежемесячный платёж
    public int CollectionDay { get; set; }
    
    // Размер кредита в копейках
    public int Principal { get; set; }
    
    // Текущий размер кредита в копейках
    public int CurrentAccountsPayable { get; set; }
    
    // Задолженность по процентам
    public int ArrearsInterest { get; set; }
    
    // Задолженность по выплатам
    public int Arrears { get; set; }
    
    // Пеня
    public int Fine { get; set; }
}