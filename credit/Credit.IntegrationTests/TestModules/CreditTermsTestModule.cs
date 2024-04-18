using System.Net;
using Credit.Data.Requests.CreditTerms;
using Credit.Data.Responses;
using Credit.IntegrationTests.Fixtures;
using Xunit;

namespace Credit.IntegrationTests.TestModules;

public class CreditTermsTestModule : IIntegrationTestModule
{
    private Guid CreditTermsId { get; } = Guid.NewGuid();

    public async Task Test()
    {
        await TestCreditTermsCreation();
        Console.WriteLine("TestCreditTermsCreation succeeded");

        try
        {
            await TestCreditTermsFetching();
            Console.WriteLine("TestCreditTermsFetching succeeded");

            await TestCreditTermsUpdating();
            Console.WriteLine("TestCreditTermsUpdating succeeded");

            await TestCreditTermsDeletion();
            Console.WriteLine("TestCreditTermsDeletion succeeded");
        }
        catch (Exception e)
        {
            await CreditTermsFixture.DeleteCreditTerms(CreditTermsId);
            Console.WriteLine(e);
            throw;
        }
    }
    
    private async Task TestCreditTermsCreation()
    {
        var respond = await CreditTermsFixture.CreateValidCreditTerms(CreditTermsId);
        Assert.Equal(HttpStatusCode.OK, respond.StatusCode);
    }

    private async Task TestCreditTermsFetching()
    {
        var nonExistingId = Guid.NewGuid();

        var respond = await CreditTermsFixture.FetchCreditTerms(nonExistingId);
        Assert.Equal(HttpStatusCode.NotFound, respond.StatusCode);
    
        respond = await CreditTermsFixture.FetchCreditTerms(CreditTermsId);
        var response = await HttpClientFixture.DeserializeResponseContent<CreditTermsResponse>(respond);
        Assert.Equal(CreditTermsId, response.Id);

        var creditIdForDeletion = Guid.NewGuid();
        await CreditTermsFixture.CreateValidCreditTerms(creditIdForDeletion);
        await CreditTermsFixture.DeleteCreditTerms(creditIdForDeletion);
        respond = await CreditTermsFixture.FetchCreditTerms(creditIdForDeletion);
        Assert.Equal(HttpStatusCode.NotFound, respond.StatusCode);
    }

    private async Task TestCreditTermsUpdating()
    {
        var oldCreditTerms = await HttpClientFixture.DeserializeResponseContent<CreditTermsResponse>(await CreditTermsFixture.FetchCreditTerms(CreditTermsId));
        
        var responseMessage = await CreditTermsFixture.UpdateValidCreditTerms(CreditTermsId);
        var request = await HttpClientFixture.DeserializeRequestContent<UpdateRequest>(responseMessage);
        var response = await HttpClientFixture.DeserializeResponseContent<CreditTermsResponse>(responseMessage);
        
        Assert.NotEqual(oldCreditTerms.InterestRate, response.InterestRate);
        Assert.Equal(request.InterestRate, response.InterestRate);
    }
    
    private async Task TestCreditTermsDeletion()
    {
        var nonExistingId = Guid.NewGuid();

        var respond = await CreditTermsFixture.DeleteCreditTerms(nonExistingId);
        Assert.Equal(HttpStatusCode.NotFound, respond.StatusCode);
    
        respond = await CreditTermsFixture.DeleteCreditTerms(CreditTermsId);
        Assert.Equal(HttpStatusCode.OK, respond.StatusCode);
    
        respond = await CreditTermsFixture.DeleteCreditTerms(CreditTermsId);
        Assert.Equal(HttpStatusCode.NotFound, respond.StatusCode);
    }
}