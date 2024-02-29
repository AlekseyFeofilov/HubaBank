using Microsoft.AspNetCore.Mvc;

namespace Credit.Controllers;

[ApiController]
[Route("[controller]")]
public class CreditController : ControllerBase
{
    [HttpGet("{id:guid}")]
    public IActionResult Get(Guid id)
    {
        return Ok();
    }
}