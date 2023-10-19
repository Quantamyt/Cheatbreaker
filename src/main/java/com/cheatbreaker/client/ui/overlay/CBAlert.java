package com.cheatbreaker.client.ui.overlay;


import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.ui.fading.AbstractFade;
import com.cheatbreaker.client.ui.fading.FloatFade;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

public class CBAlert {
    private final AbstractFade fadeTime = new FloatFade(275L);
    private static final float alertWidth = 140F;
    private static final float alertHeight = 55F;
    private boolean titleBar;
    private float lastHeight;
    private float height;
    private final String title;
    private final String[] message;
    private final long openTime;

    public CBAlert(String string, String[] stringArray, float f2) {
        this.title = string;
        this.message = stringArray;
        this.height = f2;
        this.lastHeight = f2;
        this.openTime = System.currentTimeMillis();
    }

    public void render() {
        float f = this.lastHeight - (this.lastHeight - this.height) * this.fadeTime.getFadeAmount();

        Gui.drawGradientRect(this.lastHeight, f, this.lastHeight + alertWidth, f + alertHeight, -819057106, -822083584);
        if (!this.titleBar) {
            CheatBreaker.getInstance().playRegular14px.drawStringWithShadow(this.title, this.lastHeight + (float) 4, f + (float) 4, -1);
            Gui.drawRect(this.lastHeight + (float) 4, f + 14.5f, this.lastHeight + alertWidth - (float) 5, f + (float) 15, 0x2E5E5E5E);
        }
        for (int i = 0; i < this.message.length && i <= 2; ++i) {
            CheatBreaker.getInstance().playRegular14px.drawString(this.message[i], this.lastHeight + (float) 4, f + (float) 17 + (float) (i * 10), -1);
        }

        if (!(Minecraft.getMinecraft().currentScreen instanceof OverlayGui))
            CheatBreaker.getInstance().playRegular14px.drawString("Press Shift + Tab", this.lastHeight + (float) 4, f + (float) CBAlert.getHeight() - (float) 12, 0x6FFFFFFF);
    }

    public void setMaxHeight(float f) {
        this.lastHeight = this.height;
        this.height = f;
        this.fadeTime.startAnimation();
    }

    public boolean isFading() {
        return !this.fadeTime.isTimeNotAtZero() || this.fadeTime.isOver();
    }

    public boolean shouldDisplay() {
        return System.currentTimeMillis() - this.openTime > 3500L;
    }

    public static void displayMessage(String string, String string2) {
        OverlayGui.getInstance().displayMessage(string, string2);
    }

    public static void displayMessage(String string) {
        OverlayGui.getInstance().displayMessage(string);
    }

    public static int getWidth() {
        return 140;
    }

    public static int getHeight() {
        return 55;
    }

    public void showTitleBar(boolean bl) {
        this.titleBar = bl;
    }

    public float getCurrentHeight() {
        return this.lastHeight;
    }

    public float getLastHeight() {
        return this.lastHeight;
    }

    public float getMaxHeight() {
        return this.height;
    }

    public void setCurrentHeight(float f) {
        this.lastHeight = f;
    }

    public void setWidth(float f) {
    }

    public void setHeight(float f) {
        this.height = f;
    }
}
