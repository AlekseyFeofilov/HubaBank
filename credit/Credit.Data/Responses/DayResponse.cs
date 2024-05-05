using System.Text.Json.Serialization;

namespace Credit.Data.Responses;

public class DayResponse
{
    [JsonIgnore]
    public Guid Id { get; set; }
    public DateTime Now { get; set; }
}