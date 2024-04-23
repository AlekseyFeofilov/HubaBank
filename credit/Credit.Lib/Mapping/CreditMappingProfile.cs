using AutoMapper;
using Credit.Dal.Models;
using Credit.Data.Responses;

namespace Credit.Lib.Mapping;

public class CreditMappingProfile : Profile
{
    public CreditMappingProfile()
    {
        CreateMap<Dal.Models.Credit, CreditResponse>();
        CreateMap<CreditResponse, Dal.Models.Credit>();
        
        CreateMap<Data.Requests.Credit.CreateRequest, Dal.Models.Credit>();
        CreateMap<Dal.Models.Credit, Data.Requests.Credit.CreateRequest>();
        
        CreateMap<Data.Requests.Credit.UpdateRequest, Dal.Models.Credit>();
        CreateMap<Dal.Models.Credit, Data.Requests.Credit.UpdateRequest>();
        
        CreateMap<Data.Requests.Credit.UpdateRequest, CreditResponse>();
        CreateMap<CreditResponse, Data.Requests.Credit.UpdateRequest>();
    }
}