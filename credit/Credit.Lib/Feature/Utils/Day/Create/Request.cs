namespace Credit.Lib.Feature.Utils.Day.Create;

public class Request : Base.Add.Request<Dal.Models.Day, Data.Requests.Day.CreateRequest>
{
    public Request(Data.Requests.Day.CreateRequest entity) : base(entity)
    {
    }
}