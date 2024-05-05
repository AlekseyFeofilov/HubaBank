using AutoMapper;

namespace Credit.Lib.Mapping;

public class DayProfileMapping : Profile
{
    public DayProfileMapping()
    {
        CreateMap<Dal.Models.Day, Data.Responses.DayResponse>();
        CreateMap<Data.Responses.DayResponse, Dal.Models.Day>();
        
        CreateMap<Data.Requests.Day.CreateRequest, Dal.Models.Day>();
        CreateMap<Dal.Models.Day, Data.Requests.Day.CreateRequest>();
    }
}