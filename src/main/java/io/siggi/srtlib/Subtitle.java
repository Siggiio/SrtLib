package io.siggi.srtlib;

public final class Subtitle {
    private String text;
    private SubtitleTime start;
    private SubtitleTime end;
    private boolean top;
    private boolean forced;

    public Subtitle(String text, SubtitleTime start, SubtitleTime end, boolean top, boolean forced) {
        setText(text);
        setStart(start);
        setEnd(end);
        setTop(top);
        setForced(forced);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        if (text == null) {
            throw new NullPointerException();
        }
        text = text
            .replace("\r\n", "\n")
            .replace("\r", "\n")
            .replaceAll("\n{2,}", "\n")
            .replaceAll("^\n", "")
            .replaceAll("\n$", "");
        this.text = text;
    }

    public SubtitleTime getStart() {
        return start;
    }

    public void setStart(SubtitleTime start) {
        if (start == null) {
            throw new NullPointerException();
        }
        this.start = start;
    }

    public SubtitleTime getEnd() {
        return end;
    }

    public void setEnd(SubtitleTime end) {
        if (end == null) {
            throw new NullPointerException();
        }
        this.end = end;
    }

    public boolean isTop() {
        return top;
    }

    public void setTop(boolean top) {
        this.top = top;
    }

    public boolean isForced() {
        return forced;
    }

    public void setForced(boolean forced) {
        this.forced = forced;
    }
}
