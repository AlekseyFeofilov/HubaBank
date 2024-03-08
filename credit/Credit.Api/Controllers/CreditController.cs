using System;
using System.Threading.Tasks;
using Credit.Data.Dtos;
using Credit.Lib.Feature.Ping;
using MediatR;
using Microsoft.AspNetCore.Mvc;

namespace Credit.Api.Controllers;

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