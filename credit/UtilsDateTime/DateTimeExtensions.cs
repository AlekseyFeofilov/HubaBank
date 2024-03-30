namespace Utils.DateTime;

public static class DateTimeExtensions
{
    public static int GetDifferenceInMonths(this System.DateTime date1, System.DateTime date2)
    {
        return Math.Abs((date1.Year - date2.Year) * 12 + date1.Month - date2.Month);
    }
    
    public static int GetDifferenceInMonths(this DateOnly date1, System.DateTime date2)
    {
        return Math.Abs((date1.Year - date2.Year) * 12 + date1.Month - date2.Month);
    }
    
    public static int GetDifferenceInMonths(this System.DateTime date1, DateOnly date2)
    {
        return Math.Abs((date1.Year - date2.Year) * 12 + date1.Month - date2.Month);
    }
    
    public static int GetDifferenceInMonths(this DateOnly date1, DateOnly date2)
    {
        return Math.Abs((date1.Year - date2.Year) * 12 + date1.Month - date2.Month);
    }

    public static DateOnly ToDateOnly(this System.DateTime dateTime)
    {
        return DateOnly.FromDateTime(dateTime);
    }

    public static string ToDateOnlyString(this System.DateTime dateTime)
    {
        return DateOnly.FromDateTime(dateTime).ToString();
    }
}