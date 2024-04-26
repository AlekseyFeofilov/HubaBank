namespace Credit.Data.Responses;

public class CreditTermsResponse
{
    /// <summary>
    /// Id условий кредита
    /// </summary>
    public Guid Id { get; set; }
    
    /// <summary>
    /// Процентная ставка по кредиту
    /// </summary>
    public float InterestRate { get; set; }
    
    /// <summary>
    /// Название условий кредита
    /// </summary>
    public string Title { get; set; }
}