package com.cheatbreaker.client.module.impl.normal.hud.keystrokes;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.ui.module.HudLayoutEditorGui;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.inventory.GuiContainer;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * Draws the actual key for the Key Strokes mod.
 * @see ModuleKeyStrokes
 */
public class Key {
    @Getter private final String displayString;
    @Getter private int keyCode;
    @Getter private final float width;
    @Getter private final float height;
    private boolean pressed;
    private long lastPressed;
    private Color backgroundPressedColor;
    private Color backgroundUnpressedColor;
    private Color textPressedColor;
    private Color textUnpressedColor;
    private Color clicksPressedColor;
    private Color clicksUnpressedColor;
    private Color borderPressedColor;
    private Color borderUnpressedColor;

    public Key(String displayString, int keyCode, float width, float height) {
        this.displayString = displayString;
        this.keyCode = keyCode;
        this.width = width;
        this.height = height;
    }

    public void render(float x, float y, int textColor, int textColorPressed, int backgroundColor, int backgroundColorPressed, int borderColor, int borderColorPressed, boolean textShadow) {
        Minecraft mc = Minecraft.getMinecraft();
        int button = (this.keyCode == -99 || this.keyCode == -100) ? ((this.keyCode == -99) ? 1 : 0) : -1;
        boolean pressed = (mc.currentScreen == null || mc.currentScreen instanceof GuiContainer || mc.currentScreen instanceof HudLayoutEditorGui) && ((button != -1) ? Mouse.isButtonDown(button) : Keyboard.isKeyDown(this.keyCode));
        if (pressed && !this.pressed) {
            this.pressed = true;
            this.lastPressed = System.currentTimeMillis();
            this.backgroundPressedColor = new Color(backgroundColor, true);
            this.backgroundUnpressedColor = new Color(backgroundColorPressed, true);
            this.textPressedColor = new Color(textColor, true);
            this.textUnpressedColor = new Color(textColorPressed, true);
            this.clicksPressedColor = new Color((Integer)CheatBreaker.getInstance().getModuleManager().keystrokesMod.clicksColor.getColorValue(), true);
            this.clicksUnpressedColor = new Color((Integer)CheatBreaker.getInstance().getModuleManager().keystrokesMod.clicksColorPressed.getColorValue(), true);
            this.borderPressedColor = new Color(borderColor, true);
            this.borderUnpressedColor = new Color(borderColorPressed, true);
        } else if (this.pressed && !pressed) {
            this.pressed = false;
            this.lastPressed = System.currentTimeMillis();
            this.backgroundPressedColor = new Color(backgroundColorPressed, true);
            this.backgroundUnpressedColor = new Color(backgroundColor, true);
            this.textPressedColor = new Color(textColorPressed, true);
            this.textUnpressedColor = new Color(textColor, true);
            this.clicksPressedColor = new Color((Integer)CheatBreaker.getInstance().getModuleManager().keystrokesMod.clicksColorPressed.getColorValue(), true);
            this.clicksUnpressedColor = new Color((Integer)CheatBreaker.getInstance().getModuleManager().keystrokesMod.clicksColor.getColorValue(), true);
            this.borderPressedColor = new Color(borderColorPressed, true);
            this.borderUnpressedColor = new Color(borderColor, true);
        }
        float backgroundFadeTime = (Float) CheatBreaker.getInstance().getModuleManager().keystrokesMod.backgroundFadeTime.getValue();
        int rgb;
        if (System.currentTimeMillis() - this.lastPressed < backgroundFadeTime) {
            float n9 = (System.currentTimeMillis() - this.lastPressed) / backgroundFadeTime;
            rgb = new Color(
                    (int)Math.abs(n9 * this.backgroundUnpressedColor.getRed() + (1.0f - n9) * this.backgroundPressedColor.getRed()),
                    (int)Math.abs(n9 * this.backgroundUnpressedColor.getGreen() + (1.0f - n9) * this.backgroundPressedColor.getGreen()),
                    (int)Math.abs(n9 * this.backgroundUnpressedColor.getBlue() + (1.0f - n9) * this.backgroundPressedColor.getBlue()),
                    (int)Math.abs(n9 * this.backgroundUnpressedColor.getAlpha() + (1.0f - n9) * this.backgroundPressedColor.getAlpha())
            ).getRGB();
        } else {
            rgb = (pressed ? backgroundColorPressed : backgroundColor);
        }

        float textFadeTime = (Float) CheatBreaker.getInstance().getModuleManager().keystrokesMod.textFadeTime.getValue();
        int getTextColor;
        int getCPSTextColor;
        int getTextShadowColor;
        if ((float)(System.currentTimeMillis() - this.lastPressed) < textFadeTime) {
            float var12 = (float)(System.currentTimeMillis() - this.lastPressed);
            float var13 = var12 / textFadeTime;
            int r = (int)Math.abs(var13 * (float)this.textUnpressedColor.getRed() + (1.0F - var13) * (float)this.textPressedColor.getRed());
            int g = (int)Math.abs(var13 * (float)this.textUnpressedColor.getGreen() + (1.0F - var13) * (float)this.textPressedColor.getGreen());
            int b = (int)Math.abs(var13 * (float)this.textUnpressedColor.getBlue() + (1.0F - var13) * (float)this.textPressedColor.getBlue());
            int a = (int)Math.abs(var13 * (float)this.textUnpressedColor.getAlpha() + (1.0F - var13) * (float)this.textPressedColor.getAlpha());
            int rc = (int)Math.abs(var13 * (float)this.clicksUnpressedColor.getRed() + (1.0F - var13) * (float)this.clicksPressedColor.getRed());
            int gc = (int)Math.abs(var13 * (float)this.clicksUnpressedColor.getGreen() + (1.0F - var13) * (float)this.clicksPressedColor.getGreen());
            int bc = (int)Math.abs(var13 * (float)this.clicksUnpressedColor.getBlue() + (1.0F - var13) * (float)this.clicksPressedColor.getBlue());
            int ac = (int)Math.abs(var13 * (float)this.clicksUnpressedColor.getAlpha() + (1.0F - var13) * (float)this.clicksPressedColor.getAlpha());
            getTextColor = (new Color(r, g, b, a)).getRGB();
            getCPSTextColor = (new Color(rc, gc, bc, ac)).getRGB();
            getTextShadowColor = (new Color(r/4, g/4, b/4, a)).getRGB();
        } else {
            getTextColor = pressed ? textColorPressed : textColor;
            getCPSTextColor = pressed ? (Integer)CheatBreaker.getInstance().getModuleManager().keystrokesMod.clicksColorPressed.getColorValue() : (Integer)CheatBreaker.getInstance().getModuleManager().keystrokesMod.clicksColor.getColorValue();
            int rgbp = textColorPressed;
            int textrgb = textColor;
            int rp = rgbp >> 16 & 255;
            int gp = rgbp >> 8 & 255;
            int bp = rgbp & 255;
            int r = textrgb >> 16 & 255;
            int g = textrgb >> 8 & 255;
            int b = textrgb & 255;
            getTextShadowColor = pressed ? (new Color(rp/4, gp/4, bp/4)).getRGB() : (new Color(r/4, g/4, b/4)).getRGB();
        }

        float borderFadeTime = (Float) CheatBreaker.getInstance().getModuleManager().keystrokesMod.borderFadeTime.getValue();
        int borderRGB;
        if (System.currentTimeMillis() - this.lastPressed < borderFadeTime) {
            float n9 = (System.currentTimeMillis() - this.lastPressed) / borderFadeTime;
            borderRGB = new Color(
                    (int)Math.abs(n9 * this.borderUnpressedColor.getRed() + (1.0f - n9) * this.borderPressedColor.getRed()),
                    (int)Math.abs(n9 * this.borderUnpressedColor.getGreen() + (1.0f - n9) * this.borderPressedColor.getGreen()),
                    (int)Math.abs(n9 * this.borderUnpressedColor.getBlue() + (1.0f - n9) * this.borderPressedColor.getBlue()),
                    (int)Math.abs(n9 * this.borderUnpressedColor.getAlpha() + (1.0f - n9) * this.borderPressedColor.getAlpha())
            ).getRGB();
        } else {
            borderRGB = (pressed ? borderColorPressed : borderColor);
        }
        if ((Boolean) CheatBreaker.getInstance().getModuleManager().keystrokesMod.background.getValue()) {
            Gui.drawRect(x, y, x + this.width, y + this.height, rgb);
        }
        if ((Boolean) CheatBreaker.getInstance().getModuleManager().keystrokesMod.border.getValue()) {
            float borderThickness = (Float) CheatBreaker.getInstance().getModuleManager().keystrokesMod.borderThickness.getValue();
            Gui.drawOutline(x - borderThickness, y - borderThickness, x + this.width + borderThickness, y + this.height + borderThickness, borderThickness, borderRGB);
        }
        if (this.keyCode == mc.gameSettings.keyBindJump.getKeyCode() && (Boolean) CheatBreaker.getInstance().getModuleManager().keystrokesMod.spacebarLine.getValue()) {
            float spaceBarPos = (Boolean) CheatBreaker.getInstance().getModuleManager().keystrokesMod.centerSpacebarLine.getValue() ? this.height / 2 - (float)CheatBreaker.getInstance().getModuleManager().keystrokesMod.spacebarLineHeight.getValue() / 2 : 3;
            if (textShadow) Gui.drawRect((x + this.width / 2.0f - this.width / (12.0F - (float)CheatBreaker.getInstance().getModuleManager().keystrokesMod.spacebarLineWidth.getValue())) + 1, y + spaceBarPos + 1.0f, (x + this.width / 2.0f + this.width / (12.0F - (float)CheatBreaker.getInstance().getModuleManager().keystrokesMod.spacebarLineWidth.getValue())) + 1, y + spaceBarPos + 1 + (float)CheatBreaker.getInstance().getModuleManager().keystrokesMod.spacebarLineHeight.getValue(), getTextShadowColor);
            Gui.drawRect(x + this.width / 2 - this.width / (12.0F - (float)CheatBreaker.getInstance().getModuleManager().keystrokesMod.spacebarLineWidth.getValue()), y + spaceBarPos, x + this.width / 2.0f + this.width / (12.0F - (float)CheatBreaker.getInstance().getModuleManager().keystrokesMod.spacebarLineWidth.getValue()), y + spaceBarPos + (float)CheatBreaker.getInstance().getModuleManager().keystrokesMod.spacebarLineHeight.getValue(), getTextColor);
        } else {
            GL11.glEnable(GL11.GL_BLEND);
            float xPos = (x + this.width / 2.0F) - (float) (mc.fontRenderer.getStringWidth(this.displayString) / 2) + 0.26F;
            float yPos = y + this.height / 2.0F - (float)(mc.fontRenderer.FONT_HEIGHT / 2) + 0.65F;
            if ((Boolean) CheatBreaker.getInstance().getModuleManager().keystrokesMod.legacyPosition.getValue()) {
                xPos = (x + this.width / 2.0F) - (float) (mc.fontRenderer.getStringWidth(this.displayString) / 2);
                yPos = y + this.height / 2.0F - (float)(mc.fontRenderer.FONT_HEIGHT / 2) + 1.0f;
            }
            float boxSize = (Float) CheatBreaker.getInstance().getModuleManager().keystrokesMod.boxSize.getValue();
            boolean attackKeyCode = this.keyCode == mc.gameSettings.keyBindAttack.getKeyCode();
            boolean useItemKeyCode = this.keyCode == mc.gameSettings.keyBindUseItem.getKeyCode();
            String clicksOption = attackKeyCode ? (String) CheatBreaker.getInstance().getModuleManager().keystrokesMod.leftClicks.getValue() : useItemKeyCode ? (String) CheatBreaker.getInstance().getModuleManager().keystrokesMod.rightClicks.getValue() : "";
            if (attackKeyCode || useItemKeyCode) {
                if (boxSize > 14.0F && clicksOption.equals("With Clicks")) {

                    GL11.glPushMatrix();
                    float cpsScale = 0.6666F;
                    GL11.glScalef(cpsScale, cpsScale, 0.0F);
                    yPos = y + this.height / 2.0F - (float)(mc.fontRenderer.FONT_HEIGHT / 2) + 6.0F;
                    String clicks = CheatBreaker.getInstance().getModuleManager().keystrokesMod.leftCPS.size() + " CPS";
                    if (this.displayString.equals("RMB")) {
                        clicks = CheatBreaker.getInstance().getModuleManager().keystrokesMod.rightCPS.size() + " CPS";
                    }
                    float xClicksPos = (x + this.width / 2.0F) - (mc.fontRenderer.getStringWidth(clicks) / 2.0F) * cpsScale;
                    mc.fontRenderer.drawString(clicks, xClicksPos / cpsScale, yPos / cpsScale, getCPSTextColor, textShadow);
                    GL11.glScalef(1.0F, 1.0F, 0.0F);
                    GL11.glPopMatrix();
                    yPos = y + this.height / 2.0F - (float)(mc.fontRenderer.FONT_HEIGHT / 2) - 3.0F;
                } else if (clicksOption.equals("Replace Clicks")) {
                    GL11.glPushMatrix();
                    float cpsScale = 1.0F;
                    GL11.glScalef(cpsScale, cpsScale, 0.0F);
                    String clicks = this.displayString;
                    String cpsText = boxSize < 15.0F ? "" : " CPS";
                    if (attackKeyCode) {
                        if (CheatBreaker.getInstance().getModuleManager().keystrokesMod.leftCPS.size() > 0) {
                            clicks = CheatBreaker.getInstance().getModuleManager().keystrokesMod.leftCPS.size() + cpsText;
                            if (boxSize > 15.0F) {
                                cpsScale = 0.6666F;
                                GL11.glScalef(cpsScale, cpsScale, 0.0F);
                            }
                        }
                    }
                    if (useItemKeyCode) {
                        if (CheatBreaker.getInstance().getModuleManager().keystrokesMod.rightCPS.size() > 0) {
                            clicks = CheatBreaker.getInstance().getModuleManager().keystrokesMod.rightCPS.size() + cpsText;
                            if (boxSize > 15.0F) {
                                cpsScale = 0.6666F;
                                GL11.glScalef(cpsScale, cpsScale, 0.0F);
                            }
                        }
                    }
                    xPos = x + this.width / 2.0F - (mc.fontRenderer.getStringWidth(clicks) / 2.0F) * cpsScale + 0.26F;
                    yPos = y + this.height / 2.0F - (float)(mc.fontRenderer.FONT_HEIGHT / 2) + (boxSize > 15.0F && !clicks.equals(this.displayString) ? 1.65F : 0.65F);
                    if ((Boolean) CheatBreaker.getInstance().getModuleManager().keystrokesMod.legacyPosition.getValue()) {
                        xPos = x + this.width / 2.0F - (mc.fontRenderer.getStringWidth(clicks) / 2.0F) * cpsScale;
                        yPos = y + this.height / 2.0F - (float)(mc.fontRenderer.FONT_HEIGHT / 2) + (boxSize > 15.0F && !clicks.equals(this.displayString) ? 2.0F : 1.0F);
                    }
                    mc.fontRenderer.drawString(clicks, xPos / cpsScale, yPos / cpsScale, getTextColor, textShadow);
                    GL11.glScalef(1.0F, 1.0F, 0.0F);
                    GL11.glPopMatrix();
                }
            }

            if (!clicksOption.equals("Replace Clicks")) {
                mc.fontRenderer.drawString(this.displayString, xPos, yPos, getTextColor, textShadow);
            }
            GL11.glDisable(GL11.GL_BLEND);
        }
    }
}
