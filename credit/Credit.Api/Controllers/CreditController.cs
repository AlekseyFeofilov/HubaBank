using System.Diagnostics.CodeAnalysis;
using Credit.Api.Attributes;
using Credit.Data;
using Credit.Data.Responses;
using Credit.Lib.Feature.Credit.Fetch.ById;
using MediatR;
using Microsoft.AspNetCore.Mvc;

namespace Credit.Api.Controllers;

[ApiController]
[Route("[controller]")]
[SuppressMessage("ReSharper", "UnusedParameter.Global")]
public class CreditController : ControllerBase
{
    private readonly IMediator _mediator;

    public CreditController(IMediator mediator)
    {
        _mediator = mediator;
    }

    /// <response code="404">Not Found</response>
    [HttpGet("{id:guid}")] 
    public Task<CreditResponse> FetchCredit(Guid id, [FromHeader] Guid? requestId = null)
    {
        return _mediator.Send(new Request(id), HttpContext.RequestAborted);
    }
    
    [HttpGet("users/{userid:guid}")]
    public async Task<IReadOnlyCollection<CreditResponse>> FetchCreditByUser(Guid userid, 
        [FromQuery] PageFilter pageFilter, 
        [FromHeader] Guid? requestId = null)
    {
        return await _mediator.Send(new Lib.Feature.Credit.Fetch.ByUserId.Request(userid, pageFilter), HttpContext.RequestAborted);
    }
    
    //todo проверить с несуществующим bill id
    /// <response code="400">BadRequest</response>
    [HttpPost]
    [BodyBasedIdempotentHandlingMiddlewareAllow]
    public async Task<Guid> CreateCredit(Data.Requests.Credit.CreateRequest data, 
        [FromHeader] Guid? requestId = null, 
        [FromHeader] Guid? idempotentKey = null)
    {
        var credit = await _mediator.Send(new Lib.Feature.Credit.Create.Request(data), HttpContext.RequestAborted);
        return credit.First().Id;
    }
    
    /// <response code="404">Not Found</response>
    [HttpDelete("{id:guid}")]
    [IdBasedIdempotentHandlingMiddlewareAllow]
    public async Task DeleteCredit(Guid id, 
        [FromHeader] Guid? requestId = null, 
        [FromHeader] Guid? idempotentKey = null)
    {
        await _mediator.Send(new Lib.Feature.Credit.Delete.Request(id), HttpContext.RequestAborted);
    }
}