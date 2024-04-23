using Credit.Dal.Specifications;
using Credit.Data.Requests.Payment;
using Credit.Primitives;
using MediatR;
using Microsoft.Extensions.Logging;

namespace Credit.Lib.Feature.Credit.Arrears.Handle;

public class Handler : IRequestHandler<Request>
{
    private readonly IMediator _mediator;
    private readonly ILogger<Handler> _logger;

    public Handler(IMediator mediator, ILogger<Handler> logger)
    {
        _mediator = mediator;
        _logger = logger;
    }

    public async Task Handle(Request request, CancellationToken cancellationToken)
    {
        var credit = await _mediator.Send(new Credit.Fetch.ById.Request(request.CreditId), cancellationToken);
        var billBalance = await _mediator.Send(new Bill.FetchBalance.Request(credit.BillId), cancellationToken);
        var overdidPayments =
            (await _mediator.Send(new Payment.Fetch.All.Request(new OverdidPaymentSpecification()), cancellationToken))
            .OrderBy(x => x.PaymentDay);
        
        var creditUpdateRequest = new Data.Requests.Credit.UpdateRequest();
        var paymentAmount = 0L;

        await TryPayOffArrearsInterest();
        await TryPayOffOverdidPayments();
        await TryPayOffFine();
        await _mediator.Send(new Credit.Update.Request(credit.Id, creditUpdateRequest), cancellationToken);
        
        _logger.LogWarning("Credit with id {creditId} has arrears interest equal to {arrears} " +
                           "and fine equal to {fine}",
            credit.Id, creditUpdateRequest.ArrearsInterest, creditUpdateRequest.Fine);
        return;

        async Task PayOff(long wantedPayment)
        {
            paymentAmount = Math.Min(wantedPayment, billBalance);
            await _mediator.Send(new MasterBill.Deposit.Request(credit.BillId, paymentAmount), cancellationToken);
            billBalance -= paymentAmount;
        }

        async Task TryPayOffArrearsInterest()
        {
            foreach (var payment in overdidPayments)
            {
                if (billBalance == 0) return;
                if (payment.ArrearsInterest == 0) continue;

                await PayOff(payment.ArrearsInterest);
                payment.ArrearsInterest -= paymentAmount;
                
                var paymentUpdateRequest = new UpdateRequest
                {
                    ArrearsInterest = payment.ArrearsInterest
                };

                await _mediator.Send(new Payment.Update.Request(payment.Id, paymentUpdateRequest), cancellationToken);
                paymentUpdateRequest.Arrears = credit.ArrearsInterest - paymentAmount;

                _logger.LogWarning("Payment with id {paymentId} of credit with id {creditId} were paid off in " +
                                   "the amount off {paymentAmount}. Now arrears for the payment equal to {arrears} and their " +
                                   "status is {status}",
                    payment.Id, credit.Id, paymentAmount, paymentUpdateRequest.Arrears,
                    paymentUpdateRequest.PaymentStatus == PaymentStatus.PaidLate ? "Paid Late" : "Overdue");
            }
            
            if (billBalance > 0 && credit.ArrearsInterest > 0)
            {
                await PayOff(credit.ArrearsInterest);
                creditUpdateRequest.ArrearsInterest = credit.ArrearsInterest - paymentAmount;
            
                _logger.LogWarning(
                    "Arrears interest of credit with id {creditId} were paid off in the amount off {paymentAmount}",
                    credit.Id, paymentAmount);
            }
        }
        
        async Task TryPayOffOverdidPayments()
        {
            var payments =
                (await _mediator.Send(new Payment.Fetch.All.Request(new OverdidPaymentSpecification()), cancellationToken))
                .OrderBy(x => x.PaymentDay);
        
            foreach (var payment in payments)
            {
                if (billBalance == 0 && payment.PaymentAmount > 0) continue;

                await PayOff(payment.Arrears);
                
                var paymentUpdateRequest = new UpdateRequest
                {
                    Arrears = payment.Arrears - paymentAmount,
                    PaymentStatus = payment.Arrears - paymentAmount == 0
                        ? PaymentStatus.PaidLate
                        : PaymentStatus.Overdue
                };

                await _mediator.Send(new Payment.Update.Request(payment.Id, paymentUpdateRequest), cancellationToken);
                paymentUpdateRequest.Arrears = credit.ArrearsInterest - paymentAmount;

                _logger.LogWarning("Payment with id {paymentId} of credit with id {creditId} were paid off in " +
                                   "the amount off {paymentAmount}. Now arrears for the payment equal to {arrears} and their " +
                                   "status is {status}",
                    payment.Id, credit.Id, paymentAmount, paymentUpdateRequest.Arrears,
                    paymentUpdateRequest.PaymentStatus == PaymentStatus.PaidLate ? "Paid Late" : "Overdue");
            }
        }

        async Task TryPayOffFine()
        {
            if (billBalance > 0 && credit.Fine > 0)
            {
                await PayOff(credit.Fine);
                creditUpdateRequest.Fine = credit.Fine - paymentAmount;
            
                _logger.LogWarning("Fine of credit with id {creditId} were paid off in the amount off {paymentAmount}",
                    credit.Id, paymentAmount);
            }
        }
    }
}