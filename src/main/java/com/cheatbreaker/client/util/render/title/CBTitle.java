package com.cheatbreaker.client.util.render.title;

import com.cheatbreaker.client.util.render.title.data.TitleType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter @AllArgsConstructor
public class CBTitle {
    private final String message;
    private final TitleType titletype;
    private final float scale;
    private final long displayTimeMs;
    private final long fadeInTimeMs;
    private final long fadeOutTimeMs;
    protected final long currentTimeMillis = System.currentTimeMillis();

    /**
     * Returns if the title is fading or not.
     */
    public boolean isFading() {
        return System.currentTimeMillis() < this.currentTimeMillis + this.fadeInTimeMs;
    }

    /**
     * Returns if the title should display or not.
     */
    public boolean shouldDisplay() {
        return System.currentTimeMillis() > this.currentTimeMillis + this.displayTimeMs - this.fadeOutTimeMs;
    }
}
