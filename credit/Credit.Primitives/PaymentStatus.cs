using System.Text.Json.Serialization;

namespace Credit.Primitives;

[JsonConverter(typeof(JsonStringEnumConverter))]
public enum PaymentStatus
{
    Paid,
    PaidLate,
    Overdue,
    Scheduled
}