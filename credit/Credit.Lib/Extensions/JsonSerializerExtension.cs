using System.Text;
using System.Text.Json;

namespace Credit.Lib.Extensions;

public static class JsonSerializerExtension
{
    public static byte[] ToJsonByteArray<T>(this T source)
    {
        var jsonSerializerOptions = new JsonSerializerOptions
        {
            PropertyNamingPolicy = JsonNamingPolicy.CamelCase,
            WriteIndented = true
        };
        
        var json = JsonSerializer.Serialize(source, jsonSerializerOptions);
        return Encoding.UTF8.GetBytes(json);
    }
}