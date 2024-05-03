using AutoMapper;

namespace Credit.Lib.Mapping;

public class CircuitBreakerMappingProfile : Profile
{
    public CircuitBreakerMappingProfile()
    {
        CreateMap<Dal.Models.CircuitBreaker, Data.Responses.CircuitBreakerResponse>();
        CreateMap<Data.Responses.CircuitBreakerResponse, Dal.Models.CircuitBreaker>();
        
        CreateMap<Data.Requests.CircuitBreaker.CreateRequest, Dal.Models.CircuitBreaker>();
        CreateMap<Dal.Models.CircuitBreaker, Data.Requests.CircuitBreaker.CreateRequest>();
    }
}