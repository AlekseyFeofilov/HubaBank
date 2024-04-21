using Credit.Lib.Jobs;
using MediatR;

namespace Credit.Lib.Feature.Payment.Enqueue;

public class Handler : IRequestHandler<Request>
{
    private readonly IJobClient _jobClient;

    public Handler(IJobClient jobClient)
    {
        _jobClient = jobClient;
    }

    public Task Handle(Request request, CancellationToken cancellationToken)
    {
        _jobClient.EnqueuePayment(request.Id);
        return Task.CompletedTask;
    }
}