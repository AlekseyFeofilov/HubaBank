using Credit.Lib;
using MediatR;

namespace Credit.Api.Middlewares;

public class RandomFaultMiddleware : IMiddleware
{
    private readonly ILogger<RandomFaultMiddleware> _logger;
    private readonly bool _randomFaultEnable;

    public RandomFaultMiddleware(ILogger<RandomFaultMiddleware> logger, IMediator mediator, AppSettings appSettings)
    {
        _logger = logger ?? throw new ArgumentNullException(nameof(logger));

        var randomFaultEnableSettingId = appSettings.RandomFaultEnableSettingId;
        var randomFaultEnableSetting = mediator.Send(new Lib.Feature.Utils.Setting.Fetch.Request(randomFaultEnableSettingId)).Result;
        _randomFaultEnable = randomFaultEnableSetting.Value == "true";
    }

    public async Task InvokeAsync(HttpContext context, RequestDelegate next)
    {
        if (context.Request.Path.StartsWithSegments("/swagger"))
        {
            await next(context);
            return;
        }

        ThrowIfUnstable(0.29f, 0.68f);
        await next(context);
        ThrowIfUnstable(0.29f, 0.68f, true);
    }

    private void ThrowIfUnstable(float minFaultProbability, 
        float maxFaultProbability, 
        bool afterRequestExecution = false)
    {
        if (!_randomFaultEnable)
        {
            return;
        }
        
        var faultProbability = minFaultProbability;
        
        if (DateTime.Now.Minute % 2 == 0)
        {
            faultProbability = maxFaultProbability;
        }

        var randomValue = 1.0 * new Random().Next(0, 100) / 100;
        if (!(randomValue < faultProbability)) return;
        
        _logger.LogWarning("Fake exception was thrown out {time}. Probability was {probability}", 
            afterRequestExecution ? "after request execution" : "", faultProbability);
        throw new Exception();
    }
}