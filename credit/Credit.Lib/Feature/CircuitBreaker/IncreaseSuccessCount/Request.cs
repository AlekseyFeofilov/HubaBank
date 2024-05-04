// using Credit.Dal.Specifications;
//
// namespace Credit.Lib.Feature.CircuitBreaker.IncreaseSuccessCount;
//
// public class Request : Base.Update.Request<Dal.Models.CircuitBreaker, Data.Responses.CircuitBreakerResponse>
// {
//     public Request(Guid id, int increaseValue = 1)
//         : base(new IdentitySpecification<Dal.Models.CircuitBreaker, Guid>(id))
//     {
//         Expression = circuitBreaker => { circuitBreaker.SuccessCount += increaseValue; };
//     }
// }