using System.Text.Json.Serialization;

namespace EmployeeGateway.Models;

public class Transaction
{
    public string Id { get; set; }
    public string BillId { get; set; }
    public int BalanceChange { get; set; }
    public Reason Reason { get; set; }
    public string Instant { get; set; }
}

[JsonConverter(typeof(JsonStringEnumConverter))]
public enum Reason
{
    TERMINAL,
    LOAN
}