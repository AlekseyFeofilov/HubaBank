// using Credit.Dal.Specifications;
//
// namespace Credit.Lib.Feature.CircuitBreaker.ResetStatistic;
//
// public class Request : Base.Update.Request<Dal.Models.CircuitBreaker, Data.Responses.CircuitBreakerResponse>
// {
//     public Request(Guid id)
//         : base(new IdentitySpecification<Dal.Models.CircuitBreaker, Guid>(id))
//     {
//         var lastUpdate = DateTime.Now;
//         
//         Expression = circuitBreaker =>
//         {
//             circuitBreaker.SuccessCount = 0;
//             circuitBreaker.ErrorCount = 0;
//             circuitBreaker.LastUpdate = lastUpdate;
//         };
//     }
// }