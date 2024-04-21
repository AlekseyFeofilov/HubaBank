using System.Net;
using Credit.Data.Requests.Credit;
using Credit.Data.Responses;
using Credit.IntegrationTests.Fixtures;
using Xunit;

namespace Credit.IntegrationTests.TestModules;

public class CreditTestModule : IIntegrationTestModule
{
    private Guid CreditId { get; } = Guid.NewGuid();

    public async Task Test()
    {
        await TestCreditCreation();
        Console.WriteLine("TestCreditCreation succeeded");

        try
        {
            await TestCreditFetching();
            Console.WriteLine("TestCreditFetching succeeded");

            await TestCreditUpdating();
            Console.WriteLine("TestCreditUpdating succeeded");

            await TestCreditDeletion();
            Console.WriteLine("TestCreditDeletion succeeded");
        }
        catch (Exception e)
        {
            await CreditFixture.DeleteCredit(CreditId);
            Console.WriteLine(e);
            throw;
        }
    }
    
    private async Task TestCreditCreation()
    {
        var respond = await CreditFixture.CreateCreditWithoutInterestAndCreditTerms();
        Assert.Equal("\"Credit interest or credit terms must be specified\"", await respond.Content.ReadAsStringAsync());

        respond = await CreditFixture.CreateValidCredit(CreditId);
        Assert.Equal(HttpStatusCode.OK, respond.StatusCode);
    }

    private async Task TestCreditFetching()
    {
        var nonExistingId = Guid.NewGuid();

        var respond = await CreditFixture.FetchCredit(nonExistingId);
        Assert.Equal(HttpStatusCode.NotFound, respond.StatusCode);
    
        respond = await CreditFixture.FetchCredit(CreditId);
        Assert.Equal($@"{{""id"":""{CreditId}"",""accountId"":""3fa85f64-5717-4562-b3fc-2c963f66afa6"",""completionDate"":""2022-01-01"",""interestRate"":1,""collectionDay"":0,""debt"":0,""accountsPayable"":0,""arrearsInterest"":0,""arrears"":0,""fine"":0,""type"":0}}", await respond.Content.ReadAsStringAsync());

        var creditIdForDeletion = Guid.NewGuid();
        await CreditFixture.CreateValidCredit(creditIdForDeletion);
        await CreditFixture.DeleteCredit(creditIdForDeletion);
        respond = await CreditFixture.FetchCredit(creditIdForDeletion);
        Assert.Equal(HttpStatusCode.NotFound, respond.StatusCode);
    }

    private async Task TestCreditUpdating()
    {
        //todo make test with creditTermsId
        var respond = await CreditFixture.UpdateCreditWithoutInterestAndCreditTerms(CreditId);
        Assert.Equal("\"Credit interest or credit terms must be specified\"", await respond.Content.ReadAsStringAsync());
        
        var oldCredit = await HttpClientFixture.DeserializeResponseContent<CreditResponse>(await CreditFixture.FetchCredit(CreditId));
        
        var responseMessage = await CreditFixture.UpdateValidCredit(CreditId);
        var request = await HttpClientFixture.DeserializeRequestContent<UpdateRequest>(responseMessage);
        var response = await HttpClientFixture.DeserializeResponseContent<CreditResponse>(responseMessage);
        
        Assert.NotEqual(oldCredit.CompletionDate, response.CompletionDate);
        Assert.NotEqual(oldCredit.InterestRate, response.InterestRate);
        
        Assert.Equal(request.CompletionDate, response.CompletionDate);
        Assert.Equal(request.InterestRate, response.InterestRate);
    }

    private async Task TestCreditDeletion()
    {
        var nonExistingId = Guid.NewGuid();

        var respond = await CreditFixture.DeleteCredit(nonExistingId);
        Assert.Equal(HttpStatusCode.NotFound, respond.StatusCode);
    
        respond = await CreditFixture.DeleteCredit(CreditId);
        Assert.Equal(HttpStatusCode.OK, respond.StatusCode);
    
        respond = await CreditFixture.DeleteCredit(CreditId);
        Assert.Equal(HttpStatusCode.NotFound, respond.StatusCode);
    }
}