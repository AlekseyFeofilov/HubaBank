using AutoMapper;
using MediatR;
using MediatR.Pipeline;

namespace Credit.Lib.Feature.Payment.Update;

public class PreProcessor : IRequestPostProcessor<Request, Data.Requests.Payment.UpdateRequest>
{
    private readonly IMediator _mediator;
    private readonly IMapper _mapper;

    public PreProcessor(IMediator mediator, IMapper mapper)
    {
        _mediator = mediator;
        _mapper = mapper;
    }

    //todo проверить, что работает
    public async Task Process(Request request, Data.Requests.Payment.UpdateRequest response, CancellationToken cancellationToken)
    {
        if (!IsCreditAffected(request.UpdateRequest))
        {
            return;
        }

        var payment = await _mediator.Send(new Payment.Fetch.ById.Request(request.PaymentId), cancellationToken);
        var credit = await _mediator.Send(new Credit.Fetch.ById.Request(payment.CreditId), cancellationToken);
        var creditUpdateRequest = _mapper.Map<Data.Requests.Credit.UpdateRequest>(credit);
        
        if (request.UpdateRequest.PaymentAmount.HasValue)
        {
            creditUpdateRequest.CurrentAccountPayable!.Value
                .ConsistDiff(payment.PaymentAmount, request.UpdateRequest.PaymentAmount.Value);
        }
            
        if (request.UpdateRequest.Interest.HasValue)
        {
            creditUpdateRequest.CurrentAccountPayable!.Value
                .ConsistDiff(payment.Interest, request.UpdateRequest.Interest.Value); 
        }
            
        if (request.UpdateRequest.Arrears.HasValue)
        {
            creditUpdateRequest.Arrears!.Value
                .ConsistDiff(payment.Arrears, request.UpdateRequest.Arrears.Value); 
        }

        if (request.UpdateRequest.ArrearsInterest.HasValue)
        {
            creditUpdateRequest.ArrearsInterest!.Value
                .ConsistDiff(payment.ArrearsInterest, request.UpdateRequest.ArrearsInterest.Value); 
        }

        await _mediator.Send(new Credit.Update.Request(credit.Id, creditUpdateRequest), cancellationToken);
    }

    private static bool IsCreditAffected(Data.Requests.Payment.UpdateRequest request)
    {
        return request.PaymentAmount.HasValue ||
               request.Interest.HasValue ||
               request.Arrears.HasValue ||
               request.ArrearsInterest.HasValue;
    }
}

internal static class Extension
{
    public static long ConsistDiff(this long value, long consistOf, long newConsistOf)
    {
        return value - consistOf + newConsistOf;
    }
}