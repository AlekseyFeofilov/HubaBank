namespace Utils.DateTime;

public static class DateConvertingExtension
{
    public static DateOnly ToDateOnly(this System.DateTime dateTime)
    {
        return DateOnly.FromDateTime(dateTime);
    }

    public static string ToDateOnlyString(this System.DateTime dateTime)
    {
        return DateOnly.FromDateTime(dateTime).ToString();
    }
}