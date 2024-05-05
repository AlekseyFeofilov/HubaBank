using System.Net;

namespace BFF_client.Api.Patterns
{
    public interface ICircuitBreakerService
    {
        public void NewUsersPercent(float percent);
        public void NewCorePercent(float percent);
        public void NewCreditPercent(float percent);

        public Task<HttpResponseMessage> SendWithBreaker(
             HttpClient httpClient,
             HttpRequestMessage requestMessage,
             UnstableService unstableService);
    }

    public class CircuitBreakerService : ICircuitBreakerService
    {
        private readonly ILogger<CircuitBreakerService> _logger;

        private CircuitBreakerState usersState = new BreakerClosed();
        private CircuitBreakerState coreState = new BreakerClosed();
        private CircuitBreakerState creditState = new BreakerClosed();

        public CircuitBreakerService(ILogger<CircuitBreakerService> logger)
        {
            _logger = logger;
        }

        public void NewUsersPercent(float percent)
        {
            if (percent > 0.75 && usersState is BreakerHalfOpen halfOpen && (DateTime.Now - halfOpen.halfOpenInTime).TotalSeconds > 8)
            {
                usersState = new BreakerOpen(DateTime.Now);
                _logger.LogWarning("Проблемы с UserService. BreakerOpen");
            }
            else if (percent > 0.6)
            {
                usersState = new BreakerOpen(DateTime.Now);
                _logger.LogWarning("Проблемы с UserService. BreakerOpen");
            }
        }

        public void NewCorePercent(float percent)
        {
            if (percent > 0.95 && coreState is BreakerHalfOpen halfOpen && (DateTime.Now - halfOpen.halfOpenInTime).TotalSeconds > 8)
            {
                coreState = new BreakerOpen(DateTime.Now);
                _logger.LogWarning("Проблемы с CoreService. BreakerOpen");
            }
            else if (percent > 0.7)
            {
                coreState = new BreakerOpen(DateTime.Now);
                _logger.LogWarning("Проблемы с CoreService. BreakerOpen");
            }
        }

        public void NewCreditPercent(float percent)
        {
            if (percent > 0.95 && creditState is BreakerHalfOpen halfOpen && (DateTime.Now - halfOpen.halfOpenInTime).TotalSeconds > 8)
            {
                creditState = new BreakerOpen(DateTime.Now);
                _logger.LogWarning("Проблемы с CreditService. BreakerOpen");
            }
            else if (percent > 0.7)
            {
                creditState = new BreakerOpen(DateTime.Now);
                _logger.LogWarning("Проблемы с CreditService. BreakerOpen");
            }
        }

        public async Task<HttpResponseMessage> SendWithBreaker(
             HttpClient httpClient,
             HttpRequestMessage requestMessage,
             UnstableService unstableService)
        {
            switch (unstableService)
            {
                case UnstableService.USERS:
                    if (ReadyToHalfOpen(usersState))
                    {
                        usersState = new BreakerHalfOpen(DateTime.Now);
                        _logger.LogWarning("BreakerHalfOpen для UsersService");
                    }
                    else if (ReadyToClosed(usersState))
                    {
                        usersState = new BreakerClosed();
                        _logger.LogWarning("BreakerClosed для UsersService");
                    }
                    return await usersState.TrySend(httpClient, requestMessage);
                    break;
                case UnstableService.CORE:
                    if (ReadyToHalfOpen(coreState))
                    {
                        coreState = new BreakerHalfOpen(DateTime.Now);
                        _logger.LogWarning("BreakerHalfOpen для CoreService");
                    }
                    else if (ReadyToClosed(coreState))
                    {
                        coreState = new BreakerClosed();
                        _logger.LogWarning("BreakerClosed для CoreService");
                    }
                    return await coreState.TrySend(httpClient, requestMessage);
                    break;
                case UnstableService.CREDIT:
                    if (ReadyToHalfOpen(creditState))
                    {
                        creditState = new BreakerHalfOpen(DateTime.Now);
                        _logger.LogWarning("BreakerHalfOpen для CreditService");
                    }
                    else if (ReadyToClosed(creditState))
                    {
                        creditState = new BreakerClosed();
                        _logger.LogWarning("BreakerClosed для CreditService");
                    }
                    return await creditState.TrySend(httpClient, requestMessage);
                    break;
            }

            return new HttpResponseMessage(HttpStatusCode.InternalServerError);
        }

        private static bool ReadyToHalfOpen(CircuitBreakerState state)
        {
            if (state is BreakerOpen open && (DateTime.Now - open.openInTime).TotalSeconds > 10)
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        private static bool ReadyToClosed(CircuitBreakerState state)
        {
            if (state is BreakerHalfOpen halfOpen && (DateTime.Now - halfOpen.halfOpenInTime).TotalSeconds > 20)
            {
                return true;
            }
            else
            {
                return false;
            }
        }
    }
}
