using Credit.Data.Responses;
using MediatR;
using Microsoft.AspNetCore.Mvc;

namespace Credit.Api.Controllers;

[ApiController]
[Route("[controller]")]
public class CreditTermsController : ControllerBase
{
    private readonly IMediator _mediator;

    public CreditTermsController(IMediator mediator)
    {
        _mediator = mediator;
    }

    /// <response code="404">Not Found</response>
    [HttpGet("{id:guid}")]
    public async Task<CreditTermsResponse> GetCreditTerms(Guid id)
    {
        return await _mediator.Send(new Lib.Feature.CreditTerms.FetchById.Request(id), HttpContext.RequestAborted);
    }

    // /// <response code="404">Not Found</response>
    // [HttpDelete("{id:guid}")]
    // public async Task<CreditTermsResponse> CreditTerms(Guid id)
    // {
    //     return await _mediator.Send(new Lib.Feature.CreditTerms.FetchById.Request(id), HttpContext.RequestAborted);
    // }
}