package com.cheatbreaker.client.ui.overlay;


import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.ui.fading.AbstractFade;
import com.cheatbreaker.client.ui.fading.FloatFade;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;

public class CBAlert {
    private final AbstractFade fadeTime = new FloatFade(275L);
    private boolean titleBar;
    private float lastHeight;
    private float height;
    private final String title;
    private final String[] message;
    private final long openTime;

    public CBAlert(String title, String[] message, float height) {
        this.title = title;
        this.message = message;
        this.height = height;
        this.lastHeight = height;
        this.openTime = System.currentTimeMillis();
    }

    public void render() {
        float width = (float) ((new ScaledResolution(Minecraft.getMinecraft())).getScaledWidth() - 140);
        float height = (new ScaledResolution(Minecraft.getMinecraft())).getScaledHeight() + this.lastHeight - (this.lastHeight - this.height) * this.fadeTime.getFadeAmount();
        if (this.titleBar) {
            Minecraft.getMinecraft().ingameGUI.drawGradientRect(width, height, width + 140.0F, height + 55.0F, -819057106, -822083584);
            for (int i = 0; i < this.message.length && i <= 3; ++i) {
                CheatBreaker.getInstance().playRegular14px.drawString(this.message[i], width + 4.0F, height + 4.0F + (float) (i * 10), -1);
            }
        } else {
            Minecraft.getMinecraft().ingameGUI.drawGradientRect(width, height, width + 140.0F, height + 55.0F, -819057106, -822083584);
            CheatBreaker.getInstance().playRegular14px.drawStringWithShadow(this.title, width + 4.0F, height + 4.0F, -1);
            Gui.drawRect(width + 4.0F, height + 14.5f, width + 140.0F - 5.0F, height + 15.0F, 0x2E5E5E5E);
            for (int i = 0; i < this.message.length && i <= 2; ++i) {
                CheatBreaker.getInstance().playRegular14px.drawString(this.message[i], width + 4.0F, height + 17.0F + (float) (i * 10), -1);
            }
        }
        if (!(Minecraft.getMinecraft().currentScreen instanceof OverlayGui)) {
            CheatBreaker.getInstance().playRegular14px.drawString("Press Shift + Tab", width + 4.0F, height + (float) CBAlert.getHeight() - 12.0F, 0x6FFFFFFF);
        }
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

    public static void displayMessage(String title, String message) {
        OverlayGui.getInstance().displayMessage(title, message);
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

    public float getMaxHeight() {
        return this.height;
    }

    public void setWidth(float f) {
    }

    public void setCurrentHeight(float f) {
        this.lastHeight = f;
    }

    public void setHeight(float f) {
        this.height = f;
    }
}
