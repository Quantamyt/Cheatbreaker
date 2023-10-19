package com.cheatbreaker.client.module.impl.normal.hud;

import com.cheatbreaker.client.event.impl.render.GuiDrawEvent;
import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.ui.module.GuiAnchor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import org.lwjgl.opengl.GL11;

/**
 * @Module - FPS
 * @see AbstractModule
 *
 * This mod is the Vanilla CheatBreaker FPS Module, likely will be used for reference later on.
 * NOTE: This will be removed towards the end of the Recode Branch.
 */
@Deprecated
public class FPS extends AbstractModule {
    private final Setting background;
    private final Setting textColor;
    private final Setting backgroundColor;

    public FPS() {
        super("FPS");
        this.setDefaultAnchor(GuiAnchor.RIGHT_TOP);
        this.setDefaultTranslations(0.0f, 0.0f);
        this.setState(false);
        this.background = new Setting(this, "Show Background").setValue(true);
        this.textColor = new Setting(this, "Text Color").setValue(-1).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE);
        this.backgroundColor = new Setting(this, "Background Color").setValue(0x6F000000).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE);
        this.setPreviewLabel("[144 FPS]", 1.4f);
        this.addEvent(GuiDrawEvent.class, this::onGuiDraw);
    }

    private void onGuiDraw(GuiDrawEvent event) {
        if (!this.isRenderHud()) {
            return;
        }
        GL11.glPushMatrix();
        this.scaleAndTranslate(event.getScaledResolution());
        if ((Boolean) this.background.getValue()) {
            this.setDimensions(56, 18);
            Gui.drawRect(0.0f, 0.0f, 56, 13, this.backgroundColor.getColorValue());
            String string = Minecraft.debugFPS + " FPS";
            this.mc.fontRendererObj.drawString(string, (int) (this.width / 2.0f - (float) (this.mc.fontRendererObj.getStringWidth(string) / 2)), 3, this.textColor.getColorValue());
        } else {
            String string = "[" + Minecraft.debugFPS + " FPS]";
            this.setDimensions(this.mc.fontRendererObj.drawString(string, this.width / 2.0f - (float) (this.mc.fontRendererObj.getStringWidth(string) / 2), 0.0f, this.textColor.getColorValue(), true), 18);
        }
        GL11.glPopMatrix();
    }
}
