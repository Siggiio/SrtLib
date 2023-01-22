package io.siggi.srtlib;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;

public final class SubtitleReader implements Closeable {
    private final BufferedReader in;
    private int lineNumber = 0;

    public SubtitleReader(Reader in) {
        if (in == null) {
            throw new NullPointerException();
        }
        if (in instanceof BufferedReader) {
            this.in = (BufferedReader) in;
        } else {
            this.in = new BufferedReader(in);
        }
    }

    private String readLine() throws IOException {
        String line = in.readLine();
        if (line != null) lineNumber += 1;
        return line;
    }

    public Subtitle read() throws IOException {
        String number;
        do {
            number = readLine();
            if (number == null) return null;
        } while (number.isEmpty());
        int subtitleNumber;
        try {
            subtitleNumber = Integer.parseInt(number);
        } catch (NumberFormatException nfe) {
            throw new SubtitleIOException("Line " + lineNumber + ": Expected a number, got " + number + ".");
        }
        String timeLine = readLine();
        if (timeLine == null) {
            throw new SubtitleIOException("Line " + lineNumber + ": Expected time line to follow, found end of file.");
        }
        timeLine = timeLine.replaceFirst("-->", "").replaceAll(" {2,}", " ");
        String[] timeLineParts = timeLine.split(" ");
        SubtitleTime start, end;
        try {
            start = new SubtitleTime(timeLineParts[0]);
            end = new SubtitleTime(timeLineParts[1]);
        } catch (IllegalArgumentException iae) {
            throw new SubtitleIOException("Line " + lineNumber + ": Improperly formatted subtitle time.");
        }
        boolean top = false;
        boolean forced = false;
        for (int i = 2; i < timeLineParts.length; i++) {
            switch (timeLineParts[i]) {
                case "X1:0":
                    top = true;
                    break;
                case "!!!":
                    forced = true;
                    break;
            }
        }
        String line;
        StringBuilder textBuilder = new StringBuilder();
        while ((line = readLine()) != null) {
            if (line.isEmpty()) break;
            if (textBuilder.length() > 0) textBuilder.append("\n");
            textBuilder.append(line);
        }
        return new Subtitle(textBuilder.toString(), start, end, top, forced);
    }

    @Override
    public void close() throws IOException {
        in.close();
    }
}
