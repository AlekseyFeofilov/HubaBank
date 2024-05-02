namespace Credit.Data.Requests.Setting;

public class CreateRequest
{
    public Guid Id { get; set; }
    public string SettingName { get; set; }
    public string Value { get; set; }
}