using System.Net;

namespace BFF_client.Api.Patterns
{
    public abstract class CircuitBreakerState
    {
        public abstract Task<HttpResponseMessage> TrySend(
             HttpClient httpClient,
             HttpRequestMessage requestMessage);
    }

    public class BreakerClosed : CircuitBreakerState
    {
        public override async Task<HttpResponseMessage> TrySend(
            HttpClient httpClient, 
            HttpRequestMessage requestMessage)
        {
            return await httpClient.SendAsync(requestMessage);
        }
    }

    public class BreakerHalfOpen : CircuitBreakerState
    {
        public DateTime halfOpenInTime;

        public BreakerHalfOpen(DateTime halfOpenInTime)
        {
            this.halfOpenInTime = halfOpenInTime;
        }

        public override async Task<HttpResponseMessage> TrySend(
            HttpClient httpClient,
            HttpRequestMessage requestMessage)
        {
            if (new Random().Next(101) > 85)
            {
                return await httpClient.SendAsync(requestMessage);
            }
            else
            {
                return new HttpResponseMessage(HttpStatusCode.ServiceUnavailable);
            }
        }
    }

    public class BreakerOpen : CircuitBreakerState
    {
        public DateTime openInTime;

        public BreakerOpen(DateTime openInTime)
        {
            this.openInTime = openInTime;
        }

        public override async Task<HttpResponseMessage> TrySend(
            HttpClient httpClient,
            HttpRequestMessage requestMessage)
        {
            return new HttpResponseMessage(HttpStatusCode.ServiceUnavailable);
        }
    }
}
