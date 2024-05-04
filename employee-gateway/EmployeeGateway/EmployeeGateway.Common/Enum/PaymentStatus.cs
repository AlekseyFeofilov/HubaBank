using System.Text.Json.Serialization;

namespace EmployeeGateway.Common.Enum;

[JsonConverter(typeof(JsonStringEnumConverter))]
public enum PaymentStatus
{
    Paid,
    PaidLate, 
    Overdue, 
    Scheduled
}