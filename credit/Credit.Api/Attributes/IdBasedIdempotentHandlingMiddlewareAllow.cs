namespace Credit.Api.Attributes;

[AttributeUsage(AttributeTargets.Method)]
public class IdBasedIdempotentHandlingMiddlewareAllow : Attribute
{
    public IdBasedIdempotentHandlingMiddlewareAllow()
    {
        
    }
}