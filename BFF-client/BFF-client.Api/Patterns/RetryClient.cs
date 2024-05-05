using System.Net;

namespace BFF_client.Api.Patterns
{
    public static class RetryClient
    {
        public static async Task<HttpResponseMessage> SendWithRetryAsync(
            this HttpClient httpClient, 
            HttpRequestMessage requestMessage,
            ICircuitBreakerService breakerService,
            UnstableService unstableService)
        {
            HttpResponseMessage responseMessage;
            var requestMessageCopy = await requestMessage.Clone();
            responseMessage = await breakerService.SendWithBreaker(httpClient, requestMessageCopy, unstableService);
            for (int i = 0; i < 2; i++)
            {
                if (responseMessage.StatusCode != HttpStatusCode.InternalServerError)
                {
                    return responseMessage;
                }
                else
                {
                    requestMessageCopy = await requestMessage.Clone();
                    responseMessage = await breakerService.SendWithBreaker(httpClient, requestMessageCopy, unstableService);
                }
            }
            return responseMessage;
        }

        private static async Task<HttpRequestMessage> Clone(this HttpRequestMessage request)
        {
            var clone = new HttpRequestMessage(request.Method, request.RequestUri)
            {
                Version = request.Version
            };

            if (request.Content != null)
            {
                var ms = new MemoryStream();
                await request.Content.CopyToAsync(ms);
                ms.Position = 0;
                clone.Content = new StreamContent(ms);

                request.Content.Headers.ToList().ForEach(header => clone.Content.Headers.TryAddWithoutValidation(header.Key, header.Value));
            }

            request.Options.ToList().ForEach(option => clone.Options.TryAdd(option.Key, option.Value));
            request.Headers.ToList()
                .ForEach(header => clone.Headers.TryAddWithoutValidation(header.Key, header.Value));

            return clone;
        }
    }
}
