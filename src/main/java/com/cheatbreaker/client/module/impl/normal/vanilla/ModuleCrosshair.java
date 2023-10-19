package com.cheatbreaker.client.module.impl.normal.vanilla;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.ui.util.RenderUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.OpenGlHelper;
import org.lwjgl.opengl.GL11;

public class ModuleCrosshair extends AbstractModule {
    public Setting crosshairPreviewLabel;
    public Setting previewBackground;
    public Setting generalOptionsLabel;

    public Setting usePackCrosshair;
    public Setting outline;
    public Setting dot;
    public Setting thickness;
    public Setting outlineThickness;
    public Setting size;
    public Setting gap;
    public Setting dotSize;

    public Setting colorOptionsLabel;
    public Setting vanillaBlending;
    public Setting crosshairColor;
    public Setting dotColor;
    public Setting outlineColor;

    public ModuleCrosshair() {
        super("Crosshair", "Normal");
        this.setDefaultState(false);
        this.crosshairPreviewLabel = new Setting(this, "label").setValue("Crosshair Preview");
        this.previewBackground = new Setting(this, "Preview Background", "Change the preview background to see what the crosshair can look like in multiple environments.").setValue("Birch").acceptedStringValues("Birch", "Roofed", "Swamp", "Hills", "Desert", "Mesa", "Nether", "Sky");

        this.generalOptionsLabel = new Setting(this, "label").setValue("General Options");
        this.usePackCrosshair = new Setting(this, "Use Pack Crosshair").setValue(false);
        this.outline = new Setting(this, "Outline", "Add an outline around the crosshair.").setValue(false).setCondition(() -> !(Boolean) this.usePackCrosshair.getValue());
        this.dot = new Setting(this, "Dot").setValue(false).setCondition(() -> !(Boolean) this.usePackCrosshair.getValue());
        this.thickness = new Setting(this, "Thickness", "Change the thickness of the crosshair.").setValue(2.0F).setMinMax(0.5F, 3.0F).setUnit("px").setCondition(() -> !(Boolean) this.usePackCrosshair.getValue());
        this.outlineThickness = new Setting(this, "Outline Thickness", "Change the outline thickness of the crosshair.").setValue(0.5F).setMinMax(0.5F, 3.0F).setUnit("px").setCondition(() -> !(Boolean) this.usePackCrosshair.getValue() && (Boolean) this.outline.getValue());
        this.size = new Setting(this, "Size", "Change the size of the crosshair.").setValue(4.0F).setMinMax(1.0F, 10.0F).setUnit("px").setCondition(() -> !(Boolean) this.usePackCrosshair.getValue());
        this.gap = new Setting(this, "Gap", "Change how distant the cross is from the center.").setValue(2.0F).setMinMax(1.0F, 7.5F).setUnit("px").setCondition(() -> !(Boolean) this.usePackCrosshair.getValue());
        this.dotSize = new Setting(this, "Dot Size", "Change the size of the dot.").setValue(1.0F).setMinMax(0.5F, 3.0F).setUnit("px").setCondition(() -> !(Boolean) this.usePackCrosshair.getValue() && (Boolean) this.dot.getValue());

        this.colorOptionsLabel = new Setting(this, "label").setValue("Color Options");
        this.vanillaBlending = new Setting(this, "Vanilla Blending", "Make the crosshair's color invert depending on what you are looking at.").setValue(true);
        this.crosshairColor = new Setting(this, "Crosshair Color", "Change the color of the crosshair.").setValue(-1).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE);
        this.dotColor = new Setting(this, "Dot Color", "Change the color of the crosshair dot.").setValue(-1).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCondition(() -> !(Boolean) this.usePackCrosshair.getValue() && (Boolean) this.dot.getValue());
        this.outlineColor = new Setting(this, "Outline Color", "Change the color of the crosshair outline.").setValue(0xAF000000).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCondition(() -> !(Boolean) this.usePackCrosshair.getValue() && (Boolean) this.outline.getValue());
        this.setDescription("Replace the Vanilla crosshair with your own custom crosshair.");
    }

    public void drawCrosshair(float x, float y, boolean scale) {
        GL11.glPushMatrix();
        float var15 = (float) this.scale.getValue();
        if (scale) {
            if (!this.guiScale.getValue().equals("Global")) {
                switch ((String) this.guiScale.getValue()) {
                    case "Small":
                        var15 = var15 * 0.5F / CheatBreaker.getScaleFactor();
                        break;
                    case "Normal":
                        var15 = var15 / CheatBreaker.getScaleFactor();
                        break;
                    case "Large":
                        var15 = var15 * 1.5F / CheatBreaker.getScaleFactor();
                        break;
                    case "Auto":
                        var15 = var15 * 2.0F / CheatBreaker.getScaleFactor();
                }
            } else {
                var15 = var15 * this.masterScale();
            }
        }
        float x2 = x / var15;
        float y2 = y / var15;
        GL11.glScalef(var15, var15, var15);
        float size = this.size.getFloatValue();
        float gap = this.gap.getFloatValue();
        float thickness = this.thickness.getFloatValue();
        float outlineThickness = this.outlineThickness.getFloatValue();
        float dotSize = this.dotSize.getFloatValue();
        int color = this.crosshairColor.getColorValue();
        boolean outlineEnabled = this.outline.getBooleanValue();
        GL11.glColor4f((color >> 16 & 255) / 255.0f, (color >> 8 & 255) / 255.0f, (color & 255) / 255.0f, (color >> 24 & 255) / 255.0f);
        this.mc.getTextureManager().bindTexture(Gui.icons);
//        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        if ((boolean) this.usePackCrosshair.getValue()) {
            if ((boolean) this.vanillaBlending.getValue()) {
                OpenGlHelper.glBlendFunc(GL11.GL_ONE_MINUS_DST_COLOR, GL11.GL_ONE_MINUS_SRC_COLOR, 1, 0);
            } else {
                OpenGlHelper.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE_MINUS_SRC_COLOR, 1, 0);
            }
            Gui.drawTexturedModalRect(x2 - 7, y2 - 7, 0, 0, 16, 16);
        } else {
            if ((boolean) this.vanillaBlending.getValue()) {
                OpenGlHelper.glBlendFunc(GL11.GL_ONE_MINUS_DST_COLOR, GL11.GL_ONE_MINUS_SRC_COLOR, 1, 0);
            }
            if ((Boolean) this.dot.getValue()) {
                RenderUtil.drawRoundedRect(x2 - dotSize, y2 - dotSize, x2 + dotSize, y2 + dotSize, 0.0, color);
            }
            RenderUtil.drawRoundedRect(x2 - gap - size, y2 - thickness / 2.0f, x2 - gap, y2 + thickness / 2.0f, 0.0, color);
            RenderUtil.drawRoundedRect(x2 + gap, y2 - thickness / 2.0f, x2 + gap + size, y2 + thickness / 2.0f, 0.0, color);
            RenderUtil.drawRoundedRect(x2 - thickness / 2.0f, y2 - gap - size, x2 + thickness / 2.0f, y2 - gap, 0.0, color);
            RenderUtil.drawRoundedRect(x2 - thickness / 2.0f, y2 + gap, x2 + thickness / 2.0f, y2 + gap + size, 0.0, color);
            if (outlineEnabled) {
                Gui.drawBoxWithOutLine(x2 - gap - size, y2 - thickness / 2.0f, x2 - gap, y2 + thickness / 2.0f, outlineThickness, this.outlineColor.getColorValue(), 0);
                Gui.drawBoxWithOutLine(x2 + gap, y2 - thickness / 2.0f, x2 + gap + size, y2 + thickness / 2.0f, outlineThickness, this.outlineColor.getColorValue(), 0);
                Gui.drawBoxWithOutLine(x2 - thickness / 2.0f, y2 - gap - size, x2 + thickness / 2.0f, y2 - gap, outlineThickness, this.outlineColor.getColorValue(), 0);
                Gui.drawBoxWithOutLine(x2 - thickness / 2.0f, y2 + gap, x2 + thickness / 2.0f, y2 + gap + size, outlineThickness, this.outlineColor.getColorValue(), 0);
                if ((Boolean) this.dot.getValue()) {
                    Gui.drawBoxWithOutLine(x2 - dotSize, y2 - dotSize, x2 + dotSize, y2 + dotSize, outlineThickness, this.outlineColor.getColorValue(), 0);
                }
            }
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        OpenGlHelper.glBlendFunc(770, 771, 1, 0);
        GL11.glPopMatrix();
    }
}
