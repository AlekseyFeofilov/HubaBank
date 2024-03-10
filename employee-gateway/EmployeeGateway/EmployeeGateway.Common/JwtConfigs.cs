using System.Text;
using Microsoft.IdentityModel.Tokens;

namespace EmployeeGateway.Common;

public class JwtConfigs
{
    public string Issuer { get; set; }
    public string Audience { get; set; }
    public string Key { get; set; }
    public int JwtLifeTimeMin { get; set; }
    public int AccessLifeTimDay { get; set; }
    public int RefreshLifeTimDay { get; set; }

    public SymmetricSecurityKey GetSymmetricSecurityKey()
    {
        return new SymmetricSecurityKey(Encoding.ASCII.GetBytes(Key));
    }
}