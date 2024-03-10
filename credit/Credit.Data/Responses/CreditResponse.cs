using Credit.Primitives;
using NodaMoney;

namespace Credit.Data.Responses;

public class CreditResponse
{ 
    public Guid Id { get; set; }
    public Guid AccountId { get; set; }
    public TimeSpan Term { get; set; } 
    public float InterestRate { get; set; }
    public int CollectionDay { get; set; }
    public Money Debt { get; set; }
    public Money AccountsPayable { get; set; }
    public Money ArrearsInterest { get; set; }
    public Money Arrears { get; set; }
    public Money Fine { get; set; }
    public CreditType Type { get; set; }
}