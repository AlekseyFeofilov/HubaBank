using Credit.Data;
using Credit.Data.Responses;
using Credit.Lib.Feature.Credit.Fetch.ById;
using MediatR;
using Microsoft.AspNetCore.Mvc;

namespace Credit.Api.Controllers;

[ApiController]
[Route("[controller]")]
public class CreditController : ControllerBase
{
    private readonly IMediator _mediator;

    public CreditController(IMediator mediator)
    {
        _mediator = mediator;
    }

    /// <response code="404">Not Found</response>
    [HttpGet("{id:guid}")] 
    public Task<CreditResponse> FetchCredit(Guid id)
    {
        return _mediator.Send(new Request(id), HttpContext.RequestAborted);
    }
    
    [HttpGet("users/{userid:guid}")]
    public async Task<IReadOnlyCollection<CreditResponse>> FetchCreditByUser(Guid userid, [FromQuery] PageFilter pageFilter)
    {
        return await _mediator.Send(new Lib.Feature.Credit.Fetch.ByUserId.Request(userid, pageFilter), HttpContext.RequestAborted);
    }
    
    //todo проверить с несуществующим bill id
    /// <response code="400">BadRequest</response>
    [HttpPost]
    public async Task<Guid> CreateCredit(Data.Requests.Credit.CreateRequest data)
    {
        var credit = await _mediator.Send(new Lib.Feature.Credit.Create.Request(data), HttpContext.RequestAborted);
        return credit.First().Id;
    }
    
    /// <desctipion>
    /// Дабавил, чтобы было, но без надобности не реализуйте
    /// </desctipion>
    /// <response code="404">Not Found</response>
    [HttpPut("{id:guid}")]
    public async Task<CreditResponse> UpdateCredit(Guid id, Data.Requests.Credit.UpdateRequest data)
    {
        return (await _mediator.Send(new Lib.Feature.Credit.Update.Request(id, data), HttpContext.RequestAborted))!;
    }
    
    /// <response code="404">Not Found</response>
    [HttpDelete("{id:guid}")]
    public async Task DeleteCredit(Guid id)
    {
        await _mediator.Send(new Lib.Feature.Credit.Delete.Request(id), HttpContext.RequestAborted);
    }
}