using System.Diagnostics.CodeAnalysis;
using MediatR;
using Microsoft.AspNetCore.Mvc;
using Utils.Http;

namespace Credit.Api.Controllers;

[ApiController]
[Route("[controller]")]
[SuppressMessage("ReSharper", "UnusedParameter.Global")]
public class MasterBillController : ControllerBase
{
    private readonly IMediator _mediator;

    public MasterBillController(IMediator mediator)
    {
        _mediator = mediator;
    }
    
    /// <response code="404">Not Found</response>
    [HttpGet("balance")]
    public Task<long> FetchMasterBalance([FromHeader] Guid? requestId = null)
    {
        return _mediator.Send(new Lib.Feature.MasterBill.Balance.Fetch.Request
        {
            RequestId = HttpContext.GetXRequestId()
        }, HttpContext.RequestAborted);
    }
    
}