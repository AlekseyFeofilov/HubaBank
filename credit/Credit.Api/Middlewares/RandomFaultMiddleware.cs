namespace Credit.Api.Middlewares;

public class RandomFaultMiddleware
{
    private readonly RequestDelegate _next;
    private readonly ILogger<RandomFaultMiddleware> _logger;

    public RandomFaultMiddleware(RequestDelegate next, ILogger<RandomFaultMiddleware> logger)
    {
        _next = next;
        _logger = logger ?? throw new ArgumentNullException(nameof(logger));
    }

    public async Task Invoke(HttpContext context)
    {
        if (context.Request.Path.StartsWithSegments("/swagger"))
        {
            await _next(context);
            return;
        }

        ThrowIfUnstable(0.29f, 0.68f);
        await _next(context);
        ThrowIfUnstable(0.29f, 0.68f);
    }

    private void ThrowIfUnstable(float minFaultProbability, float maxFaultProbability)
    {
        var faultProbability = minFaultProbability;
        
        if (DateTime.Now.Minute % 2 == 0)
        {
            faultProbability = maxFaultProbability;
        }

        var randomValue = 1.0 * new Random().Next(0, 100) / 100;
        if (!(randomValue < faultProbability)) return;
        
        _logger.LogWarning("Fake exception was thrown out. Probability was {probability}", faultProbability);
        throw new Exception();
    }
}