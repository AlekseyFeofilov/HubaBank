using MediatR;
using MediatR.Pipeline;
using Microsoft.Extensions.Logging;

namespace Credit.Lib.Feature.Payment.Handle;

public class PostProcessor : IRequestPostProcessor<Request, Unit>
{
    private readonly ILogger<PreProcessor> _logger;

    public PostProcessor(ILogger<PreProcessor> logger)
    {
        _logger = logger;
    }
    
    public Task Process(Request request, Unit response, CancellationToken cancellationToken)
    {
        _logger.LogWarning("Payment with id {paymentId} was handled", request.PaymentId);
        return Task.CompletedTask;
    }
}