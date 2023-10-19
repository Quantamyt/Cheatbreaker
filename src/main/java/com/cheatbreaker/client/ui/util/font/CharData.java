package com.cheatbreaker.client.ui.util.font;

public class CharData {
    public int width;
    public int height;
    public int storedX;
    public int storedY;
    final CBFont font;

    protected CharData(CBFont cBFont) {
        this.font = cBFont;
    }
}
