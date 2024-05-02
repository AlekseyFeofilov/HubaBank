using System.Text.Json.Serialization;

namespace EmployeeGateway.Common.DTO.Bill;

[JsonConverter(typeof(JsonStringEnumConverter))]
public enum BillTypeDto
{
    USER,
    TERMINAL,
    LOAN,
    TRANSFER // FOR OLD TRANSACTION
}
