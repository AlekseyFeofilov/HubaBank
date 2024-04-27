using System.Diagnostics.CodeAnalysis;
using Credit.Data.Responses;
using MediatR;
using Microsoft.AspNetCore.Mvc;

namespace Credit.Api.Controllers;

[ApiController]
[Route("[controller]")]
[SuppressMessage("ReSharper", "UnusedParameter.Global")]
public class PaymentController : ControllerBase
{
    private readonly IMediator _mediator;

    public PaymentController(IMediator mediator)
    {
        _mediator = mediator;
    }
    
    /// <response code="404">Not Found</response>
    [HttpGet("{creditId:guid}")] 
    public Task<IReadOnlyCollection<PaymentResponse>> FetchOverdidPayments(Guid creditId, 
        [FromHeader] Guid? requestId = null)
    {
        return _mediator.Send(new Lib.Feature.Payment.FetchOverdid.Request(creditId), HttpContext.RequestAborted);
    }
}