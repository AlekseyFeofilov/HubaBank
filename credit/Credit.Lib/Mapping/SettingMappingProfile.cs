using AutoMapper;

namespace Credit.Lib.Mapping;

public class SettingMappingProfile : Profile
{
    public SettingMappingProfile()
    {
        CreateMap<Dal.Models.Setting, Data.Responses.SettingResponse>();
        CreateMap<Data.Responses.SettingResponse, Dal.Models.Setting>();
        
        CreateMap<Data.Requests.Setting.CreateRequest, Dal.Models.Setting>();
        CreateMap<Dal.Models.Setting, Data.Requests.Setting.CreateRequest>();
    }
}