package com.cheatbreaker.client.util.render.title;

public class CBTitle {
    private final TitleType titletype;
    private final String message;
    private final float scale;
    private final long displayTimeMs;
    private final long fadeInTimeMs;
    private final long fadeOutTimeMs;
    protected final long currentTimeMillis = System.currentTimeMillis();

    public CBTitle(String message, TitleType titleType, float scale, long displayTimeMs, long fadeInTimeMs, long fadeOutTimeMs) {
        this.message = message;
        this.scale = scale;
        this.titletype = titleType;
        this.displayTimeMs = displayTimeMs == 0L ? 5000L : displayTimeMs;
        this.fadeInTimeMs = fadeInTimeMs;
        this.fadeOutTimeMs = fadeOutTimeMs;
    }

    public boolean isFading() {
        return System.currentTimeMillis() < this.currentTimeMillis + this.fadeInTimeMs;
    }

    public boolean shouldDisplay() {
        return System.currentTimeMillis() > this.currentTimeMillis + this.displayTimeMs - this.fadeOutTimeMs;
    }

    public TitleType getTitleType() {
        return this.titletype;
    }

    public String getMessage() {
        return this.message;
    }

    public float getScale() {
        return this.scale;
    }

    public long getDisplayTimeMs() {
        return this.displayTimeMs;
    }

    public long getFadeInTimeMs() {
        return this.fadeInTimeMs;
    }

    public long getFadeOutTimeMs() {
        return this.fadeOutTimeMs;
    }

    static TitleType getCBTitleType(CBTitle title) {
        return title.titletype;
    }

    static float getTitleScale(CBTitle title) {
        return title.scale;
    }

    static long getFadeInTimeMS(CBTitle title) {
        return title.fadeInTimeMs;
    }

    static long getDisplayTimeMs(CBTitle title) {
        return title.displayTimeMs;
    }

    static long getFadeOutTimeMs(CBTitle title) {
        return title.fadeOutTimeMs;
    }

    static String getTitleMessage(CBTitle title) {
        return title.message;
    }
}
