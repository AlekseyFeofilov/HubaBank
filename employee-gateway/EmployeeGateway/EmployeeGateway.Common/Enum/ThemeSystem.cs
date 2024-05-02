using System.Text.Json.Serialization;

namespace EmployeeGateway.Common.DTO;

[JsonConverter(typeof(JsonStringEnumConverter))]
public enum ThemeSystem
{
    Dark,
    Light
}