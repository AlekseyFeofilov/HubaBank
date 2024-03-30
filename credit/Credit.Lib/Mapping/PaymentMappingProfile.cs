using AutoMapper;
using Credit.Data.Responses;

namespace Credit.Lib.Mapping;

public class PaymentMappingProfile : Profile
{
    public PaymentMappingProfile()
    {
        CreateMap<Dal.Models.Payment, PaymentResponse>();
        CreateMap<PaymentResponse, Dal.Models.Payment>();
        
        CreateMap<Data.Requests.Payment.CreateRequest, Dal.Models.Payment>();
        CreateMap<Dal.Models.Payment, Data.Requests.Payment.CreateRequest>();
        
        CreateMap<Data.Requests.Payment.UpdateRequest, Dal.Models.Payment>();
        CreateMap<Dal.Models.Payment, Data.Requests.Payment.UpdateRequest>();
    }
}