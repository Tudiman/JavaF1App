package F1BasePack.Utility;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Time {

    private int m;
    private int s;
    private int ms;

    public Time(int ms) {

        if(ms < 0)
            throw new Error("bad ms input for Time, ms can't be negative");
        m = ms / 60000;
        ms %= 60000;
        s = ms / 1000;
        this.ms = ms % 1000;
    }

    public Time(int m, int s, int ms) {

        if(m < 0)
            throw new Error("bad m input for Time,m can't be negative");
        this.m = m;
        if(s < 0)
            throw new Error("bad m input for Time,m can't be negative");
        if(s > 59)
            throw new Error("bad m input for Time,m can't be higher than 59");
        this.s = s;
        if(ms < 0)
            throw new Error("bad ms input for Time, ms can't be negative");
        if(ms > 999)
            throw new Error("bad ms input for Time, ms can't be higher than 999");
        this.ms = ms;
    }

    public int getM() {
        return m;
    }

    public int getS() {
        return s;
    }

    public int getMs() {
        return ms;
    }

    @Override
    public String toString() {
        String formattedMs = (ms < 10 ? "0" : "") + (ms < 100 ? "0" : "") + ms;
        String formattedS = "" + (s < 10 ? "0" + s : s);
        return "" + (m > 0 ? m + "." : "") + (m > 0 ? formattedS : s) + (m > 0 ? ":" : ".") +
                formattedMs + (m > 0 ? "" : "s");
    }

    public static Time parseTime(String time) {

        String regex1 = "^\\d+$";
        String regex2 = "^(\\d+)\\.(\\d+):(\\d+)$";
        Pattern pattern = Pattern.compile(regex1);
        Matcher matcher = pattern.matcher(time);
        if(matcher.find())
            return new Time(Integer.parseInt(time));
        pattern = Pattern.compile(regex2);
        matcher = pattern.matcher(time);
        if(!matcher.find())
            throw new Error("bad time input");
            return new Time(Integer.parseInt(matcher.group(1)),
                    Integer.parseInt(matcher.group(2)),Integer.parseInt(matcher.group(3)));
    }
}
