namespace Credit.Lib.Feature.Setting.Create;

public class Request : Base.Add.Request<Dal.Models.Setting, Data.Requests.Setting.CreateRequest>
{
    public Request(Data.Requests.Setting.CreateRequest entity) : base(entity)
    {
    }
}