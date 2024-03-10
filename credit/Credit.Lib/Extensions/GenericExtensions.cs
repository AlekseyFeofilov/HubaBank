using MediatR;

namespace Credit.Lib.Extensions;

public static class GenericExtensions
{
    public static bool HasGenericBase(this Type type)
    {
        return type.BaseType?.IsGenericType ?? false;
    }

    public static bool IsGenericAssignableFrom(this Type type, Type parent)
    {
        return type.GetGenericTypeDefinition().IsAssignableFrom(parent);
    }

    public static Type GetMediatorResponseType(this Type type)
    {
        return type.GetInterfaces()
            .First(x => x.IsGenericType && x.GetGenericTypeDefinition() == typeof(IRequest<>))
            .GenericTypeArguments
            .First();
    }
}