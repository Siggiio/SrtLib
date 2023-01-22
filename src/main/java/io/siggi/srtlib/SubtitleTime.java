package io.siggi.srtlib;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class SubtitleTime {
    private static final Pattern timePattern = Pattern.compile("(\\d+):(\\d+):(\\d+),(\\d+)");
    private final long time;
    private final String timeString;

    public SubtitleTime(long time) {
        this.time = time;
        this.timeString = asString(time);
    }

    public SubtitleTime(String time) {
        this(parse(time));
    }

    private static long parse(String time) {
        Matcher matcher = timePattern.matcher(time);
        if (!matcher.matches()) {
            throw new IllegalArgumentException("Input is not a time string");
        }
        long hours = Long.parseLong(matcher.group(1));
        long minutes = Long.parseLong(matcher.group(2));
        long seconds = Long.parseLong(matcher.group(3));
        long ms = Long.parseLong(matcher.group(4));
        return (hours * 3600000L) + (minutes * 60000L) + (seconds * 1000L) + ms;
    }

    private static String asString(long time) {
        long hours = time / 3600000L;
        long minutes = (time / 60000L) % 60L;
        long seconds = (time / 1000L) % 60L;
        long ms = time % 1000L;
        return pad(Long.toString(hours), "0", 2)
            + ":" + pad(Long.toString(minutes), "0", 2)
            + ":" + pad(Long.toString(seconds), "0", 2)
            + "," + pad(Long.toString(ms), "0", 3);
    }

    private static String pad(String string, String prefix, int minimumLength) {
        int amountToAdd = minimumLength - string.length();
        if (amountToAdd <= 0) return string;
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < amountToAdd; i++) {
            sb.append(prefix);
        }
        sb.append(string);
        return sb.toString();
    }

    @Override
    public String toString() {
        return timeString;
    }
}
