package io.siggi.srtlib;

import java.io.Closeable;
import java.io.IOException;
import java.io.Writer;

public final class SubtitleWriter implements Closeable {
    private static final String newLine = "\n";
    private final Writer out;
    private int subtitlesWritten = 0;

    public SubtitleWriter(Writer out) {
        if (out == null) {
            throw new NullPointerException();
        }
        this.out = out;
    }

    public void write(Subtitle subtitle) throws IOException {
        out.write(Integer.toString(++subtitlesWritten));
        out.write(newLine);
        out.write(subtitle.getStart() + " --> " + subtitle.getEnd());
        if (subtitle.isTop()) {
            out.write(" X1:0");
        }
        if (subtitle.isForced()) {
            out.write(" !!!");
        }
        out.write(newLine);
        out.write(subtitle.getText());
        out.write(newLine);
        out.write(newLine);
    }

    @Override
    public void close() throws IOException {
        out.close();
    }
}
