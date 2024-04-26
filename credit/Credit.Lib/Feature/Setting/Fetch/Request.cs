using Credit.Dal.Specifications;

namespace Credit.Lib.Feature.Setting.Fetch;

public class Request : Base.Fetch.Request<Dal.Models.Setting, Data.Responses.SettingResponse>
{
    public Guid Id { get; }

    public Request(Guid id) : base(new IdentitySpecification<Dal.Models.Setting, Guid>(id))
    {
        Id = id;
    }
}