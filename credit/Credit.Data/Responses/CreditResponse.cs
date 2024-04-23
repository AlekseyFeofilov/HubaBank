using System.Text.Json.Serialization;
using Credit.Primitives;

namespace Credit.Data.Responses;

public class CreditResponse
{ 
    public Guid Id { get; set; }
    public Guid AccountId { get; set; }
    public Guid BillId { get; set; }
    [JsonConverter(typeof(DateOnlyJsonConverter))]
    public DateOnly CompletionDate { get; set; } 
    [JsonConverter(typeof(DateOnlyJsonConverter))]
    public DateOnly LastArrearsUpdate { get; set; } 
    public float InterestRate { get; set; }
    public int CollectionDay { get; set; }
    public long Principal { get; set; }
    public long CurrentAccountsPayable { get; set; }
    public long ArrearsInterest { get; set; }
    public long Arrears { get; set; }
    public long Fine { get; set; }
}