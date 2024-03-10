using Credit.Primitives;

namespace Credit.Data.Responses;

public class CreditResponse
{ 
    public Guid Id { get; set; }
    public Guid AccountId { get; set; }
    public DateOnly CompletionDate { get; set; } 
    public float InterestRate { get; set; }
    public int CollectionDay { get; set; }
    public long Debt { get; set; }
    public long AccountsPayable { get; set; }
    public long ArrearsInterest { get; set; }
    public long Arrears { get; set; }
    public long Fine { get; set; }
    public CreditType Type { get; set; }
}