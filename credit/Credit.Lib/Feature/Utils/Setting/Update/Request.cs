using Credit.Dal.Specifications;

namespace Credit.Lib.Feature.Utils.Setting.Update;

public class Request : Base.Update.Request<Dal.Models.Setting, Data.Responses.SettingResponse>
{
    public Guid Id { get; }

    public Request(Guid id, string newValue)
        : base(new IdentitySpecification<Dal.Models.Setting, Guid>(id))
    {
        Id = id;
        Expression = setting =>
        {
            setting.Value = newValue;
        };
    }
}