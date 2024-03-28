using System.Globalization;
using System.Text.Json;
using Credit.Data.Requests.CreditTerms;


namespace Credit.IntegrationTests.Fixtures;

public static class CreditTermsFixture
{
    public static Task<HttpResponseMessage> CreateValidCreditTerms(Guid id)
    {
        var request = new CreateRequest
        {
            Id = id,
            InterestRate = 1,
        };
        
        return CreateCreditTerms(JsonSerializer.Serialize(request));
    }

    public static Task<HttpResponseMessage> FetchCreditTerms(Guid id)
    {
        return HttpClientFixture.HttpClient.GetAsync($"/creditTerms/{id}");
    }

    public static async Task<HttpResponseMessage> UpdateValidCreditTerms(Guid id)
    {
        var request = new UpdateRequest
        {
            InterestRate = 2,
        };

        return await UpdateCreditTerms(JsonSerializer.Serialize(request), id);
    }

    public static Task<HttpResponseMessage> DeleteCreditTerms(Guid id)
    {
        return HttpClientFixture.HttpClient.DeleteAsync($"/creditTerms/{id}");
    }

    private static async Task<HttpResponseMessage> CreateCreditTerms(string content)
    {
        var httpContent = HttpClientFixture.MakeHttpContent(content);
        return await HttpClientFixture.HttpClient.PostAsync("/creditTerms", httpContent);
    }

    private static async Task<HttpResponseMessage> UpdateCreditTerms(string content, Guid id)
    {
        var httpContent = HttpClientFixture.MakeHttpContent(content);
        return await HttpClientFixture.HttpClient.PutAsync($"/creditTerms/{id}", httpContent);
    }
}