using AutoMapper;
using Credit.Data.Responses;

namespace Credit.Lib.Mapping;

public class CreditTermsMappingProfile : Profile
{
    public CreditTermsMappingProfile()
    {
        CreateMap<Dal.Models.CreditTerms, CreditTermsResponse>();
        CreateMap<CreditTermsResponse, Dal.Models.CreditTerms>();
        
        CreateMap<Data.Requests.CreditTerms.CreateRequest, Dal.Models.CreditTerms>();
        CreateMap<Dal.Models.CreditTerms, Data.Requests.CreditTerms.CreateRequest>();
        
        CreateMap<Data.Requests.CreditTerms.UpdateRequest, Dal.Models.CreditTerms>();
        CreateMap<Dal.Models.CreditTerms, Data.Requests.CreditTerms.UpdateRequest>();
    }
}