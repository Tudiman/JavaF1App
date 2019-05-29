package F1BasePack.Utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Date {

    private int day;
    private int month;
    private int year;

    public Date(int year) {
        if (year < 1920)
            throw new Error("bad year input for date, min 1950");
        this.year = year;

        this.month = 1;
        this.day = 1;
    }

    public Date(int day, int month, int year) {
        if (year < 1950)
            throw new Error("bad year input for date, min 1950");
        this.year = year;

        if(month < 1)
            throw new Error("bad month input for date, min 1");
        if(month > 12)
            throw new Error("bad month input for date, max 12");
        this.month = month;

        if(day < 1)
            throw new Error("bad day input for date, min 1");

        int maxday = 31;
        int[] shortMonths = {4,6,9,11};
        if(month == 2 && year % 4 != 0)
            maxday = 28;
        else if(month == 2)
            maxday = 29;
        else {
            for (int element : shortMonths) {
                if (month == element) {
                    maxday = 30;
                    break;
                }
            }
        }
        if(day > maxday)
            throw new Error("bad day input for date, max " + maxday);
        this.day = day;
    }

    public int getDay() {
        return day;
    }

    public int getMonth() {
        return month;
    }

    public int getYear() {
        return year;
    }

    @Override
    public String toString() {
        String formattedDay = "" + day;
        if(day < 10)
            formattedDay = "0" + day;

        String formattedMonth = "" + month;
        if(month < 10)
            formattedMonth = "0" + month;

        return formattedDay + "/" + formattedMonth + "/" + year;
    }

    public int compareTo(Date date) {

        if(year != date.year)
            return year - date.year;
        if(month != date.month)
            return month - date.month;
        return day - date.day;
    }

    public static Date parseDate(String date) {

        String regex1 = "^(\\d{4})$";
        String regex2 = "^(\\d{1,2})/(\\d{1,2})/(\\d{4})$";
        Pattern pattern = Pattern.compile(regex1);
        Matcher matcher = pattern.matcher(date);
        if(matcher.find())
            return new Date(1,1,Integer.parseInt(matcher.group(1)));
        pattern = Pattern.compile(regex2);
        matcher = pattern.matcher(date);
        if(!matcher.find())
            throw new Error("bad date format,date should be d[d]/m[m]/yyyy or yyyy," +
                    " letters in brackets means optional");
        return new Date(Integer.parseInt(matcher.group(1)),
                Integer.parseInt(matcher.group(2)),
                Integer.parseInt(matcher.group(3)));
    }

}