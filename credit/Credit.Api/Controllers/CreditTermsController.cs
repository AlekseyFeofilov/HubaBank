using System.Diagnostics.CodeAnalysis;
using Credit.Api.Attributes;
using Credit.Data.Responses;
using MediatR;
using Microsoft.AspNetCore.Mvc;

namespace Credit.Api.Controllers;

[ApiController]
[Route("[controller]")]
[SuppressMessage("ReSharper", "UnusedParameter.Global")]
public class CreditTermsController : ControllerBase
{
    private readonly IMediator _mediator;

    public CreditTermsController(IMediator mediator)
    {
        _mediator = mediator;
    }

    /// <response code="404">Not Found</response>
    [HttpGet("{id:guid}")] 
    public Task<CreditTermsResponse> FetchCreditTerms(Guid id, [FromHeader] Guid? requestId = null)
    {
        return _mediator.Send(new Lib.Feature.CreditTerms.FetchById.Request(id), HttpContext.RequestAborted);
    }
    
    [HttpGet]
    public Task<IReadOnlyCollection<CreditTermsResponse>> FetchAllCreditTerms([FromHeader] Guid? requestId = null)
    {
        return _mediator.Send(new Lib.Feature.CreditTerms.FetchAll.Request(), HttpContext.RequestAborted);
    }
    
    /// <response code="400">BadRequest</response>
    [HttpPost]
    [BodyBasedIdempotentHandlingMiddlewareAllow]
    public async Task<Guid> CreateCreditTerms(Data.Requests.CreditTerms.CreateRequest data, 
        [FromHeader] Guid? requestId = null, 
        [FromHeader] Guid? idempotentKey = null)
    {
        var credit = await _mediator.Send(new Lib.Feature.CreditTerms.Create.Request(data), HttpContext.RequestAborted);
        return credit.First().Id;
    }
    
    /// <response code="404">Not Found</response>
    [HttpDelete("{id:guid}")]
    [IdBasedIdempotentHandlingMiddlewareAllow]
    public async Task DeleteCreditTerms(Guid id, 
        [FromHeader] Guid? requestId = null, 
        [FromHeader] Guid? idempotentKey = null)
    {
        await _mediator.Send(new Lib.Feature.CreditTerms.Delete.Request(id), HttpContext.RequestAborted);
    }
}