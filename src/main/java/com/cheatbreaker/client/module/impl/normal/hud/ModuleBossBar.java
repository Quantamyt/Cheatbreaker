package com.cheatbreaker.client.module.impl.normal.hud;


import com.cheatbreaker.client.event.impl.render.GuiDrawEvent;
import com.cheatbreaker.client.event.impl.render.PreviewDrawEvent;
import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.CustomizationLevel;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.ui.module.GuiAnchor;
import com.cheatbreaker.client.ui.module.HudLayoutEditorGui;
import com.cheatbreaker.client.ui.util.RenderUtil;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.boss.BossStatus;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * @Module - ModuleBossBar
 * @see AbstractModule
 *
 * This module displays the current Minecraft Boss' health.
 */
public class ModuleBossBar extends AbstractModule {
    private final Setting textColor;
    private final Setting renderHealth;
    private final Setting renderText;
    private final Setting textShadow;
    private final Setting textPosition;
    private final Setting barWidth;
    private final Setting barHeight;
    private final Setting bossTexture;
    private final Setting bossBarColor;
    private final Setting topBossBarColor;
    private final Setting bottomBossBarColor;
    private final Setting outlineBossBarColor;

    public ModuleBossBar() {
        super("Boss Bar");
        this.setDefaultAnchor(GuiAnchor.MIDDLE_TOP);
        this.notRenderHUD = false;
        this.renderText = new Setting(this, "Render Text").setValue(true).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.renderHealth = new Setting(this, "Render Health").setValue(true).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.textShadow = new Setting(this, "Text Shadow").setValue(true).setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> (Boolean) this.renderText.getValue());
        this.bossTexture = new Setting(this, "Boss Bar Texture").setValue("Texture Pack").acceptedStringValues("Texture Pack", "Default", "Blurred", "Custom").setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> (Boolean) this.renderHealth.getValue());
        this.textPosition = new Setting(this, "Text Position").setValue(50.0f).setMinMax(0.0f, 100.0f).setUnit("%").setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> (Boolean) this.renderHealth.getValue() && (Boolean) this.renderText.getValue());
        this.barWidth = new Setting(this, "Boss Bar Width").setValue(182.0F).setMinMax(100.0F, 200.0F).setUnit("px").setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> (Boolean) this.renderHealth.getValue() && this.bossTexture.getValue().equals("Custom"));
        this.barHeight = new Setting(this, "Boss Bar Height").setValue(5.0F).setMinMax(3.0f, 24.0f).setUnit("px").setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> (Boolean) this.renderHealth.getValue() && this.bossTexture.getValue().equals("Custom"));
        this.textColor = new Setting(this, "Text Color").setValue(-1).setMinMax(-2147483648, 2147483647).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> (Boolean) this.renderText.getValue());
        this.bossBarColor = new Setting(this, "Boss Bar Color").setValue(-65337).setMinMax(-2147483648, 2147483647).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> (Boolean) this.renderHealth.getValue() && !this.bossTexture.getValue().equals("Texture Pack") && !this.bossTexture.getValue().equals("Custom"));
        this.topBossBarColor = new Setting(this, "Top Boss Bar Color").setValue(0xFFEC00B8).setMinMax(-2147483648, 2147483647).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> (Boolean) this.renderHealth.getValue() && this.bossTexture.getValue().equals("Custom"));
        this.bottomBossBarColor = new Setting(this, "Bottom Boss Bar Color").setValue(0xFF8C006D).setMinMax(-2147483648, 2147483647).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> (Boolean) this.renderHealth.getValue() && this.bossTexture.getValue().equals("Custom"));
        this.outlineBossBarColor = new Setting(this, "Outline Boss Bar Color").setValue(0xFF4A003A).setMinMax(-2147483648, 2147483647).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> (Boolean) this.renderHealth.getValue() && this.bossTexture.getValue().equals("Custom"));
        this.setPreviewIcon(new ResourceLocation("client/icons/mods/bossbar.png"), 106, 5);
        this.setDescription("Displays the current boss and health.");
        this.addEvent(GuiDrawEvent.class, this::onGuiDraw);
        this.addEvent(PreviewDrawEvent.class, this::onPreviewDraw);
        this.setDefaultState(true);
    }

    public void onPreviewDraw(PreviewDrawEvent event) {
        if (!this.isRenderHud() || (this.hiddenFromHud && !(this.mc.currentScreen instanceof HudLayoutEditorGui))) return;
        GL11.glPushMatrix();
        this.scaleAndTranslate(event.getScaledResolution());
        if (BossStatus.bossName == null || BossStatus.statusBarTime <= 0) {
            this.render("Wither", 1.0F, 0, (Boolean) renderText.getValue() ? 10 : 0);
        }
        GL11.glPopMatrix();
    }

    public void onGuiDraw(GuiDrawEvent event) {
        if (!this.isRenderHud()) return;
        GL11.glPushMatrix();
        this.scaleAndTranslate(event.getScaledResolution());
        if (BossStatus.bossName != null && BossStatus.statusBarTime > 0) {
            --BossStatus.statusBarTime;
            this.render(BossStatus.bossName, BossStatus.healthScale, 0, (Boolean) renderText.getValue() ? 10 : 0);
        }
        GL11.glPopMatrix();
    }

    public void render(String bossName, float healthScale, int x, int y) {
        GlStateManager.enableBlend();
        int barColor = this.bossBarColor.getColorValue();
        if (this.bossTexture.getValue().equals("Texture Pack")) {
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            this.mc.getTextureManager().bindTexture(Gui.icons);
        } else {
            GL11.glColor4f((barColor >> 16 & 255) / 255.0F, (barColor >> 8 & 255) / 255.0F, (barColor & 255) / 255.0F, (barColor >> 24 & 255) / 255.0F);
            this.mc.getTextureManager().bindTexture(new ResourceLocation("client/icons/bossbar/" + this.bossTexture.getValue().toString().toLowerCase() + ".png"));
        }

        FontRenderer font = this.mc.fontRendererObj;
        float width = this.bossTexture.getValue().equals("Custom") ? this.barWidth.getFloatValue() : 182;
        float x2 = !(Boolean) renderText.getValue() || font.getStringWidth(bossName) < width ? x : x + (font.getStringWidth(bossName) - width) * this.textPosition.getFloatValue() / 100.0F;
        int y2 = y;
        float remainingHealth = healthScale * width;
        if ((Boolean) renderHealth.getValue()) {
            if (this.bossTexture.getValue().equals("Custom")) {
                int top = new Color((this.topBossBarColor.getColorValue() >> 16 & 255) / 4, (this.topBossBarColor.getColorValue() >> 8 & 255) / 4, (this.topBossBarColor.getColorValue() & 255) / 4, this.topBossBarColor.getColorValue() >> 24 & 255).getRGB();
                int bottom = new Color((this.bottomBossBarColor.getColorValue() >> 16 & 255) / 4, (this.bottomBossBarColor.getColorValue() >> 8 & 255) / 4, (this.bottomBossBarColor.getColorValue() & 255) / 4, this.bottomBossBarColor.getColorValue() >> 24 & 255).getRGB();
                RenderUtil.drawGradientRectWithOutline(x2, y2, x2 + width, y2 + this.barHeight.getFloatValue(), this.outlineBossBarColor.getColorValue(), top, bottom);
                if (remainingHealth > 0) {
                    RenderUtil.drawGradientRectWithOutline(x2, y2, x2 + remainingHealth, y2 + this.barHeight.getFloatValue(), 0, this.topBossBarColor.getColorValue(), this.bottomBossBarColor.getColorValue());
                }
                RenderUtil.drawGradientRectWithOutline(x2, y2, x2 + width, y2 + this.barHeight.getFloatValue(), this.outlineBossBarColor.getColorValue(), 0, 0);
            } else {
                this.mc.ingameGUI.drawTexturedModalRect(x2, y2, 0, 74, (int) width, 5);
                this.mc.ingameGUI.drawTexturedModalRect(x2, y2, 0, 74, (int) width, 5);
                if (remainingHealth > 0) {
                    this.mc.ingameGUI.drawTexturedModalRect(x2, y2, 0, 79, (int) remainingHealth, 5);
                }
            }
        }
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        if ((Boolean) renderText.getValue())
            font.drawString(bossName, x + this.width / 2 - (float) (font.getStringWidth(bossName) / 2), y - 10, this.textColor.getColorValue(), (Boolean) this.textShadow.getValue());
        float barHeight = (Boolean) renderHealth.getValue() ? this.bossTexture.getValue().equals("Custom") ? this.barHeight.getFloatValue() : 5.0F : 0.0F;
        float textHeight = (Boolean) renderText.getValue() ? 9.0F : 0.0F;
        float extraHeight = (Boolean) renderText.getValue() && (Boolean) renderHealth.getValue() ? 1.0F : 0.0F;
        this.setDimensions((Boolean) renderHealth.getValue() ? (Boolean) renderText.getValue() ? Math.max(font.getStringWidth(bossName), width) : width : (float) (font.getStringWidth(bossName)), barHeight + textHeight + extraHeight);
        GlStateManager.disableBlend();
    }
}
