namespace Credit.Dal.Models;

public class Credit : IIdentity<Guid>, IDeletable
{
    public Guid Id { get; set; }
    public Guid AccountId { get; set; }
    public Guid BillId { get; set; }
    public DateOnly CompletionDate { get; set; } 
    public DateOnly LastArrearsUpdate { get; set; } 
    public float InterestRate { get; set; }
    public int CollectionDay { get; set; }
    public long Principal { get; set; }
    public long CurrentAccountsPayable { get; set; }
    public long ArrearsInterest { get; set; }
    public long Arrears { get; set; }
    public long Fine { get; set; }
    public Guid? CreditTermsId { get; set; }
    public CreditTerms? CreditTerms { get; set; }
    public bool IsDeleted { get; set; }
}