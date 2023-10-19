package com.cheatbreaker.client.module.impl.normal.hud;


import com.cheatbreaker.client.event.impl.GuiDrawEvent;
import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.CustomizationLevel;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.ui.module.GuiAnchor;
import com.cheatbreaker.client.ui.module.HudLayoutEditorGui;
import com.cheatbreaker.client.ui.util.HudUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 * Displays your cardinal direction.
 * @see AbstractModule
 */
public class ModuleDirectionHUD extends AbstractModule {
    private final Setting markerColor;
    private final Setting directionColor;
    private final Setting highlightColor;
    private final Setting backgroundColor;
    private final Setting borderColor;
    private final Setting showWhileTyping;
    private final Setting background;
    private Setting border;
    private Setting borderThickness;
    private final Setting highlightNorth;
    private final ResourceLocation texture = new ResourceLocation("textures/gui/compass.png");

    public ModuleDirectionHUD() {
        super("Direction HUD");
        this.setDefaultAnchor(GuiAnchor.MIDDLE_TOP);
        this.setState(false);
        this.showWhileTyping = new Setting(this, "Show While Typing", "Show the mod when opening chat.").setValue(true).setCustomizationLevel(CustomizationLevel.ADVANCED);
        this.highlightNorth = new Setting(this, "Highlight North", "Make North highlight a different color.").setValue(false).setCustomizationLevel(CustomizationLevel.MEDIUM);
        this.background = new Setting(this, "Show Background", "Draw a background.").setValue(true).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.border = new Setting(this, "Show Border", "Draw a border around the background.").setValue(false).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.borderThickness = new Setting(this, "Border Thickness", "Change the thickness of the border.").setValue(1.0F).setMinMax(0.25F, 3.0F).setUnit("px").setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> (Boolean) this.border.getValue());
        this.markerColor = new Setting(this, "Marker Color", "Change the marker color.").setValue(-43691).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.directionColor = new Setting(this, "Direction Color", "Change the direction color.").setValue(-1).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.highlightColor = new Setting(this, "Highlight Color", "Change the highlight color.").setValue(-43691).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.MEDIUM).setCondition(() -> (Boolean) this.highlightNorth.getValue());
        this.backgroundColor = new Setting(this, "Background Color", "Change the background color.").setValue(0xFF212121).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> (Boolean) this.background.getValue());
        this.borderColor = new Setting(this, "Border Color", "Sets the color for the border.").setValue(-1627389952).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> (Boolean) this.border.getValue());
        this.setPreviewIcon(new ResourceLocation("client/icons/mods/dirhud.png"), 65, 12);
        this.setDescription("Displays your cardinal direction.");
        this.setCreators("bspkrs", "jadedcat");
        this.addEvent(GuiDrawEvent.class, this::onGuiDraw);
    }

    private void onGuiDraw(GuiDrawEvent event) {
        if (!this.isRenderHud() || (this.hiddenFromHud && !(this.mc.currentScreen instanceof HudLayoutEditorGui))) {
            return;
        }
        GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        this.scaleAndTranslate(event.getScaledResolution());
        this.setDimensions(66, 12);
        if (!this.mc.ingameGUI.getChatGUI().getChatOpen() || (Boolean) this.showWhileTyping.getValue()) {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.drawCompass();
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        }
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glPopMatrix();
    }

    private void drawCompass() {
        int n = MathHelper.floor_double((double)(this.mc.thePlayer.rotationYaw * (float)256 / (float)360) + 0.3450704167414649 * 1.4489796161651611) & 0xFF;
        int n2 = 0;
        int n3 = 0;
        if ((Integer)this.directionColor.getValue() != 4095) {
            int directionColor = this.directionColor.getColorValue();
            int backgroundColor = this.backgroundColor.getColorValue();
            int highlightColor = this.highlightColor.getColorValue();
            this.mc.getTextureManager().bindTexture(this.texture);
            if ((Boolean) this.background.getValue()) {
                GL11.glColor4f((float)(backgroundColor >> 16 & 0xFF) / (float)255, (float)(backgroundColor >> 8 & 0xFF) / (float)255, (float)(backgroundColor & 0xFF) / (float)255, (float)(backgroundColor >> 24 & 255) / (float)255);
                if (n < 128) {
                    HudUtil.drawTexturedModalRect(n3, n2, n, 0, 66, 12, -100);
                } else {
                    HudUtil.drawTexturedModalRect(n3, n2, n - 128, 12, 66, 12, -100);
                }
            }

            GL11.glColor4f((float)(directionColor >> 16 & 0xFF) / (float)255, (float)(directionColor >> 8 & 0xFF) / (float)255, (float)(directionColor & 0xFF) / (float)255, (float)(directionColor >> 24 & 255) / (float)255);
            if (n < 128) {
                HudUtil.drawTexturedModalRect(n3, n2, n, 24, 66, 12, -100);
            } else {
                HudUtil.drawTexturedModalRect(n3, n2, n - 128, 36, 66, 12, -100);
            }
            if ((Boolean) this.highlightNorth.getValue()) {
                GL11.glColor4f((float)(highlightColor >> 16 & 255) / (float)255, (float)(highlightColor >> 8 & 255) / (float)255, (float)(highlightColor & 255) / (float)255, (float)(highlightColor >> 24 & 255) / (float)255);
                if (n < 128) {
                    HudUtil.drawTexturedModalRect(n3, n2, n, 72, 66, 12, -100);
                } else {
                    HudUtil.drawTexturedModalRect(n3, n2, n - 128, 84, 66, 12, -100);
                }
            }
        } else {
            this.mc.getTextureManager().bindTexture(this.texture);
            if (n < 128) {
                HudUtil.drawTexturedModalRect(n3, n2, n, 0, 66, 12, -100);
            } else {
                HudUtil.drawTexturedModalRect(n3, n2, n - 128, 12, 66, 12, -100);
            }
        }
        this.mc.fontRenderer.drawString("|", n3 + 32, n2 + 1, this.markerColor.getColorValue());
        this.mc.fontRenderer.drawString("|§r", n3 + 32, n2 + 5, this.markerColor.getColorValue());
        if ((Boolean) this.border.getValue()) {
            float borderThickness = (Float) this.borderThickness.getValue();
            Gui.drawOutline(-borderThickness, -borderThickness, 66 + borderThickness, 12 + borderThickness, borderThickness, this.borderColor.getColorValue());
        }
    }
}
