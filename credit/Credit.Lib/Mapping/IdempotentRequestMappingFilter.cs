using AutoMapper;

namespace Credit.Lib.Mapping;

public class IdempotentRequestMappingFilter : Profile
{
    public IdempotentRequestMappingFilter()
    {
        CreateMap<Dal.Models.IdempotentRequest, Data.Responses.IdempotenceRequestResponse>();
        CreateMap<Data.Responses.IdempotenceRequestResponse, Dal.Models.IdempotentRequest>();
        
        CreateMap<Data.Requests.IdempotentRequest.CreateRequest, Dal.Models.IdempotentRequest>();
        CreateMap<Dal.Models.IdempotentRequest, Data.Requests.IdempotentRequest.CreateRequest>();
    }
}