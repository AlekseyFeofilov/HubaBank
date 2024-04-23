using System.Globalization;
using System.Text.Json;
using Credit.Data.Requests.Credit;

namespace Credit.IntegrationTests.Fixtures;

public static class CreditFixture
{
    public static Task<HttpResponseMessage> CreateValidCredit(Guid id)
    {
        return CreateCredit($@"{{
  ""id"": ""{id}"",
  ""accountId"": ""3fa85f64-5717-4562-b3fc-2c963f66afa6"",
  ""principal"": 0,
  ""completionDate"": ""2022-01-01"",
  ""interestRate"": ""1""
}}");
    }

    public static Task<HttpResponseMessage> CreateCreditWithoutInterestAndCreditTerms()
    {
        return CreateCredit($@"{{
  ""id"": ""{Guid.NewGuid()}"",
  ""accountId"": ""3fa85f64-5717-4562-b3fc-2c963f66afa6"",
  ""principal"": 0,
  ""completionDate"": ""2022-01-01""
}}");
    }

    public static Task<HttpResponseMessage> FetchCredit(Guid id)
    {
        return HttpClientFixture.HttpClient.GetAsync($"/credit/{id}");
    }

    public static async Task<HttpResponseMessage> UpdateValidCredit(Guid id)
    {
        var request = new UpdateRequest
        {
            CompletionDate = "2022-01-02".ToDateOnly(),
            InterestRate = 2,
        };

        return await UpdateCredit(JsonSerializer.Serialize(request), id);
    }

    public static async Task<HttpResponseMessage> UpdateCreditWithoutInterestAndCreditTerms(Guid id)
    {
        var request = new UpdateRequest
        {
            CompletionDate = "2022-01-02".ToDateOnly(),
        };

        return await UpdateCredit(JsonSerializer.Serialize(request), id);
    }

    public static Task<HttpResponseMessage> DeleteCredit(Guid id)
    {
        return HttpClientFixture.HttpClient.DeleteAsync($"/credit/{id}");
    }

    private static async Task<HttpResponseMessage> CreateCredit(string content)
    {
        var httpContent = HttpClientFixture.MakeHttpContent(content);
        return await HttpClientFixture.HttpClient.PostAsync("/credit", httpContent);
    }

    private static async Task<HttpResponseMessage> UpdateCredit(string content, Guid id)
    {
        var httpContent = HttpClientFixture.MakeHttpContent(content);
        return await HttpClientFixture.HttpClient.PutAsync($"/credit/{id}", httpContent);
    }
}