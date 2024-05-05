using MediatR;
using Microsoft.AspNetCore.Mvc;

namespace Credit.Api.Controllers;


[ApiController]
[Route("[controller]/now")]
public class DayController : ControllerBase
{
    private readonly IMediator _mediator;

    public DayController(IMediator mediator)
    {
        _mediator = mediator;
    }
    
    [HttpGet]
    public Task<Data.Responses.DayResponse> FetchSystemNow()
    {
        return _mediator.Send(new Lib.Feature.Utils.Day.Fetch.Request(), HttpContext.RequestAborted);
    }
    
    [HttpPut("day")]
    public Task<Data.Responses.DayResponse> ChangeSystemDay(int addDays = 1)
    {
        return _mediator.Send(new Lib.Feature.Utils.Day.Update.Request(addDays), HttpContext.RequestAborted)!;
    }
    
    [HttpPut("month")]
    public Task<Data.Responses.DayResponse> ChangeSystemMonth(int addMoths = 1)
    {
        return _mediator.Send(new Lib.Feature.Utils.Day.Update.Request(null, addMoths),
            HttpContext.RequestAborted)!;
    }
    
    [HttpPut("reset")]
    public Task<Data.Responses.DayResponse> ResetSystemNow()
    {
        return _mediator.Send(new Lib.Feature.Utils.Day.Reset.Request(), HttpContext.RequestAborted)!;
    }
}