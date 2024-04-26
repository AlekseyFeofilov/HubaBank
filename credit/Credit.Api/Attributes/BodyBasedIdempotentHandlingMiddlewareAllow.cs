namespace Credit.Api.Attributes;

[AttributeUsage(AttributeTargets.Method)]
public class BodyBasedIdempotentHandlingMiddlewareAllow : Attribute
{
    public BodyBasedIdempotentHandlingMiddlewareAllow()
    {
        
    }
}