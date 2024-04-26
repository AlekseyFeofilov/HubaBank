namespace Credit.Dal.Models;

public class Setting : IIdentity<Guid>
{
    public Guid Id { get; set; }
    public string SettingName { get; set; }
    public string Value { get; set; }
}