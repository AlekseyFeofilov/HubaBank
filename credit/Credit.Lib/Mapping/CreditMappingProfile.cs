using AutoMapper;
using Credit.Data.Responses;

namespace Credit.Lib.Mapping;

public class CreditMappingProfile : Profile
{
    public CreditMappingProfile()
    {
        CreateMap<Dal.Models.Credit, CreditResponse>();
        CreateMap<CreditResponse, Dal.Models.Credit>();
    }
}