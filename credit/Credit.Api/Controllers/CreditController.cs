using Credit.Data;
using Credit.Data.Responses;
using Credit.Lib.Exceptions;
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
    [HttpGet("{id:guid}")] //todo возвращает 204 при not found 
    public async Task<CreditResponse> FetchCredit(Guid id)
    {
        var credit = await _mediator.Send(new Lib.Feature.Credit.FetchById.Request(id), HttpContext.RequestAborted); //временный костыль
        return credit ?? throw new CreditNotFoundException(id);
    }
    
    [HttpGet("users/{userid:guid}")]
    public async Task<IReadOnlyCollection<CreditResponse>> FetchCreditByUser(Guid userid, [FromQuery] PageFilter pageFilter)
    {
        return await _mediator.Send(new Lib.Feature.Credit.FetchByUserId.Request(userid, pageFilter), HttpContext.RequestAborted);
    }
    
    /// <response code="400">BadRequest</response>
    [HttpPost]
    public async Task CreateCredit(Data.Requests.Credit.CreateRequest data)
    {
        await _mediator.Send(new Lib.Feature.Credit.Create.Request(data), HttpContext.RequestAborted);
    }
    
    /// <desctipion>
    /// Дабавил, чтобы было, но без надобности не реализуйте
    /// </desctipion>
    /// <response code="404">Not Found</response>
    [HttpPut("{id:guid}")]
    public async Task UpdateCredit(Guid id, Data.Requests.Credit.UpdateRequest data) //todo добавить такие же проверки, как у Create
    //todo падает при несуществующем челе
    {
        await _mediator.Send(new Lib.Feature.Credit.Update.Request(id, data), HttpContext.RequestAborted);
    }
    
    /// <response code="404">Not Found</response>
    [HttpDelete("{id:guid}")]
    public async Task DeleteCredit(Guid id)
    {
        await _mediator.Send(new Lib.Feature.Credit.Delete.Request(id), HttpContext.RequestAborted);
    }
}