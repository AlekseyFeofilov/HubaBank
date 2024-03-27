using Extensions.Hosting.AsyncInitialization;
using MediatR;

namespace Credit.Api;

public class JobInitializer : IAsyncInitializer
{
    private readonly IMediator _mediator;

    public JobInitializer(IMediator mediator)
    {
        _mediator = mediator;
    }

    public Task InitializeAsync(CancellationToken cancellationToken)
    {
        return Debug(cancellationToken);
    }

    public async Task Debug(CancellationToken cancellationToken)
    {
        await _mediator.Send(new Lib.Feature.Ping.Request(), cancellationToken);
        // var oldAccountId = Guid.NewGuid();
        // var newAccountId = Guid.NewGuid();
        // var creditData = new CreditResponse { AccountId = oldAccountId };
        //
        // var addedCredit =
        //     (await _mediator.Send(new Request(creditData), cancellationToken))
        //     .First();
        // var fetchedByIdCredit = await _mediator.Send(
        //     new Credit.Lib.Feature.Credit.FetchById.Request(addedCredit.Id),
        //     cancellationToken);
        // var fetchedUserByIdCredit =
        //     await _mediator.Send(new Credit.Lib.Feature.Credit.FetchByUserId.Request(addedCredit.Id, null),
        //         cancellationToken);
        // var allFetchedCredits =
        //     await _mediator.Send(new Credit.Lib.Feature.Credit.FetchAll.Request(null, null), cancellationToken);
        // var updatedCredit = await _mediator.Send(new Credit.Lib.Feature.Credit.Update.Request(
        //     addedCredit, new CreditIdentitySpecification(addedCredit.Id))
        // {
        //     Expression = expression => { expression.AccountId = newAccountId; }
        // }, cancellationToken);


        Console.WriteLine("Pong");
    }
}