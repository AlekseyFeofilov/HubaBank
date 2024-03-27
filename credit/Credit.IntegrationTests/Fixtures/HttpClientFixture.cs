using System.Globalization;
using System.Text;
using System.Text.Json;

namespace Credit.IntegrationTests.Fixtures;

public static class HttpClientFixture
{
    public static HttpClient HttpClient { get; }
    
    private static JsonSerializerOptions _options = new()
    {
        PropertyNameCaseInsensitive = true
    };
    
    static HttpClientFixture()
    {
        var clientHandler = new HttpClientHandler();
        clientHandler.ServerCertificateCustomValidationCallback = (_, _, _, _) => true;

        HttpClient = new HttpClient(clientHandler);
        HttpClient.BaseAddress = new Uri("http://localhost:5135");
    }
    
    public static HttpContent MakeHttpContent(string content)
    {
        return new StringContent(content, Encoding.UTF8, "application/json");
    }
    
    public static async Task<T> DeserializeResponseContent<T>(HttpResponseMessage message)
    {
        return JsonSerializer.Deserialize<T>(await message.Content.ReadAsStringAsync(), _options)!;
    }
    
    public static async Task<T> DeserializeRequestContent<T>(HttpResponseMessage message)
    {
        return JsonSerializer.Deserialize<T>(await message.RequestMessage!.Content!.ReadAsStringAsync(), _options)!;
    }

    public static DateOnly ToDateOnly(this string str)
    {
        return DateOnly.FromDateTime(DateTime.ParseExact(str, "yyyy-mm-dd", CultureInfo.InvariantCulture));
    }
    
    public static DateTime ToDateTime(this string str)
    {
        return DateTime.ParseExact(str, "yyyy-mm-dd", CultureInfo.InvariantCulture);
    }
}