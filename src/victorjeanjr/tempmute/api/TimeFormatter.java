package victorjeanjr.tempmute.api;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author Victor Jean (VictorJeanJr)
 * @version 1.0
 * @since 05-05-2020
 */

public enum TimeFormatter {

    YEAR(30*60*24*12, "y", "year"),
    MONTH(30*60*24, "mo", "month"),
    WEEK(60*24*7, "w", "week"),
    DAY(60*24,"d", "day", "days"),
    HOUR(60,"h", "hour"),
    MINUTE(1, "mi", "min", "minute"),
    SECOND(1/60, "s", "sec", "second");

    private int calc;
    private String[] formats;

    TimeFormatter(int calc, String... formats) {
        this.calc = calc;
        this.formats = formats;
    }

    public int getCalc() {
        return calc;
    }

    public String[] getFormats() {
        return formats;
    }

    /// Statics

    public static long getFormat(int time, String formatString) {
        long calc; TimeFormatter timeFormatter = null;
        for(TimeFormatter format : TimeFormatter.values()) {
            if(Arrays.stream(format.getFormats()).anyMatch(e -> formatString.contains(e))) {
                timeFormatter = format; break;
            }
        }
        if(Objects.isNull(timeFormatter)) return -1;
        try { calc = time * 60; } catch(NumberFormatException ex) { return -1; }
        return (calc *= timeFormatter.getCalc())*1000;
    }

    public static String format(long millis) {
        StringBuilder stringBuilder = new StringBuilder();
        int day = (int) TimeUnit.MILLISECONDS.toDays(millis);
        long hours = TimeUnit.MILLISECONDS.toHours(millis) - (day * 24);
        long minute = TimeUnit.MILLISECONDS.toMinutes(millis) - (TimeUnit.MILLISECONDS.toHours(millis) * 60);
        long second = TimeUnit.MILLISECONDS.toSeconds(millis) - (TimeUnit.MILLISECONDS.toMinutes(millis) * 60);
        if(day > 0) {
            stringBuilder.append(String.format("%s day%s", day, (day > 1) ? "s" : ""));
        } else if(hours > 0) {
            stringBuilder.append(String.format("%s hour%s", hours, (hours > 1) ? "s" : ""));
        } else if(minute > 0) {
            stringBuilder.append(String.format("%s minute%s", minute, (minute > 1) ? "s" : ""));
        } else if(second > 0) {
            stringBuilder.append(String.format("%s second%s", second, (second > 1) ? "s" : ""));
        }
        return stringBuilder.toString();
    }

}
