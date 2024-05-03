namespace Utils.DateTime;

public static class DateDifferenceExtension
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
    
    public static int GetDifferenceInMinutes(this System.DateTime date1, System.DateTime date2)
    {
        return Math.Abs((int)(date2 - date1).TotalMinutes);
    }
    
    public static int GetDifferenceInSeconds(this System.DateTime date1, System.DateTime date2)
    {
        return Math.Abs((int)(date2 - date1).TotalSeconds);
    }
    
    public static int GetDifferenceInDays(this System.DateTime date1, System.DateTime date2)
    {
        return Math.Abs((date1.Year - date2.Year) * 12 + date1.DayOfYear - date2.DayOfYear);
    }
    
    public static int GetDifferenceInDays(this DateOnly date1, System.DateTime date2)
    {
        return Math.Abs((date1.Year - date2.Year) * 12 + date1.DayOfYear - date2.DayOfYear);
    }
    
    public static int GetDifferenceInDays(this System.DateTime date1, DateOnly date2)
    {
        return Math.Abs((date1.Year - date2.Year) * 12 + date1.DayOfYear - date2.DayOfYear);
    }
    
    public static int GetDifferenceInDays(this DateOnly date1, DateOnly date2)
    {
        return Math.Abs((date1.Year - date2.Year) * 12 + date1.DayOfYear - date2.DayOfYear);
    }
}