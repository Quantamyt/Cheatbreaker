package com.cheatbreaker.client.module.impl.normal.hud;

import com.cheatbreaker.client.event.impl.mouse.ClickEvent;
import com.cheatbreaker.client.event.impl.render.GuiDrawEvent;
import com.cheatbreaker.client.event.impl.tick.TickEvent;
import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.CustomizationLevel;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.ui.module.GuiAnchor;
import com.cheatbreaker.client.ui.module.HudLayoutEditorGui;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

public class ModuleCoordinates extends AbstractModule {
    private final Setting generalOptions;
    private final Setting showWhileTyping;
    private final Setting mode;
    private final Setting coords;
    private final Setting hideYCoord;
    private final Setting direction;

    public Setting customLine;

    private final Setting colorSettings;
    private final Setting coordsColor;
    private final Setting directionColor;
    private final List<Long> clicks = new ArrayList<>();

    public ModuleCoordinates() {
        super("Coordinates");

        this.setDefaultAnchor(GuiAnchor.LEFT_TOP);
        this.setDefaultTranslations(-1, 0.0f);
        this.setDefaultState(false);

        this.generalOptions = new Setting(this, "label").setValue("General Options");
        this.showWhileTyping = new Setting(this, "Show While Typing", "Show the mod when opening chat.").setValue(true).setCustomizationLevel(CustomizationLevel.ADVANCED);
        this.mode = new Setting(this, "Mode", "Layout the mod should display.").setValue("Horizontal").acceptedStringValues("Horizontal", "Vertical").setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.coords = new Setting(this, "Coordinates", "Show the coordiantes.").setValue(true).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.hideYCoord = new Setting(this, "Hide Y Coordinate", "Hide the Y coordinate.").setValue(false).setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> (Boolean) this.coords.getValue());
        this.direction = new Setting(this, "Direction", "Show the direction the player is facing.").setValue(true).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.customLine = new Setting(this, "Custom Line").setValue("").setCustomizationLevel(CustomizationLevel.ADVANCED);
        this.colorSettings = new Setting(this, "label").setValue("Color Options").setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> (Boolean) this.coords.getValue() || (Boolean) this.direction.getValue());
        this.coordsColor = new Setting(this, "Coordinates Color", "Change the coordinates text color.").setValue(-1).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> (Boolean) this.coords.getValue());
        this.directionColor = new Setting(this, "Direction Color", "Change the direction text color.").setValue(-1).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> (Boolean) this.direction.getValue());

        this.setPreviewLabel("(16, 65, 120) NW", 1.0f);
        this.setDescription("Shows your X, Y, and Z coordinates as well as your direction.");

        this.addEvent(GuiDrawEvent.class, this::onGuiDraw);
        this.addEvent(TickEvent.class, this::onTick);
        this.addEvent(ClickEvent.class, this::onOldClickEvent);
    }

    private void onGuiDraw(GuiDrawEvent event) {
        if (!this.isRenderHud() || (this.hiddenFromHud && !(this.mc.currentScreen instanceof HudLayoutEditorGui))) {
            return;
        }
        GL11.glPushMatrix();
        GlStateManager.enableBlend();
        this.scaleAndTranslate(event.getScaledResolution());
        int n = MathHelper.floor_double(this.mc.thePlayer.posX);
        int n2 = (int) this.mc.thePlayer.boundingBox.minY;
        int n3 = MathHelper.floor_double(this.mc.thePlayer.posZ);
        if (!this.mc.ingameGUI.getChatGUI().getChatOpen() || (Boolean) this.showWhileTyping.getValue()) {
            if (this.customLine.getValue().equals("")) {
                int n4 = 0;
                float f = 4;
                if ((Boolean) this.coords.getValue()) {
                    if (this.mode.getValue().equals("Horizontal")) {
                        String horizontalString = ((Boolean) this.hideYCoord.getValue() ? String.format("(%1$d, %2$d)", n, n3) : String.format("(%1$d, %2$d, %3$d)", n, n2, n3)) + ((Boolean) this.direction.getValue() ? " " : "");
                        n4 = this.mc.fontRendererObj.drawStringWithShadow(horizontalString, 0.0f, 0.0f, this.coordsColor.getColorValue());
                    } else {
                        n4 = 50;
                        f = (Boolean) this.hideYCoord.getValue() ? 9.5F : 16.0F;
                        this.mc.fontRendererObj.drawStringWithShadow("X: " + n, 0.0f, 0.0f, this.coordsColor.getColorValue());
                        if (!(Boolean) this.hideYCoord.getValue()) {
                            this.mc.fontRendererObj.drawStringWithShadow("Y: " + n2, 0.0f, 12.0F, this.coordsColor.getColorValue());
                        }
                        this.mc.fontRendererObj.drawStringWithShadow("Z: " + n3, 0.0f, (Boolean) this.hideYCoord.getValue() ? 12.0F : 24.0F, this.coordsColor.getColorValue());
                    }
                }
                if ((Boolean) this.direction.getValue()) {
                    String[] directions = new String[]{"N", "NE", "E", "SE", "S", "SW", "W", "NW"};
                    double d = (double) MathHelper.wrapAngleTo180_float(this.mc.thePlayer.rotationYaw) + 180.0;
                    d += 22.5D;
                    d %= 360.0;
                    String string = directions[MathHelper.floor_double(d / 45)];
                    this.mc.fontRendererObj.drawStringWithShadow(string, (float) n4, f - (float) 4, this.directionColor.getColorValue());
                    n4 += this.mc.fontRendererObj.getStringWidth(string);
                }
                this.setDimensions(n4, Math.max(f + (!this.mode.getValue().equals("Horizontal") && (Boolean) this.coords.getValue() ? 18.0F : 0.0F), this.mc.fontRendererObj.FONT_HEIGHT));
            } else {
                String[] arrstring = ((String) this.customLine.getValue()).split("%NL%");
                float f = -1;
                float f2 = arrstring.length * (this.mc.fontRendererObj.FONT_HEIGHT + 1);
                int n5 = 0;
                for (String string : arrstring) {
                    float f3 = this.mc.fontRendererObj.drawStringWithShadow(string = this.customText(string), 0.0f, (float) ((this.mc.fontRendererObj.FONT_HEIGHT + 1) * n5), -1);
                    if (f3 > f) {
                        f = f3;
                    }
                    this.setDimensions((int) f, (int) (Math.max(f2, this.mc.fontRendererObj.FONT_HEIGHT)));
                    ++n5;
                }
            }
        }
        GlStateManager.disableBlend();
        GL11.glPopMatrix();
    }

    private String customText(String string) {
        String[] arrstring = new String[]{"N", "NE", "E", "SE", "S", "SW", "W", "NW"};
        double d = (double) MathHelper.wrapAngleTo180_float(this.mc.thePlayer.rotationYaw) + 180.0;
        d += 22.5D;
        d %= 360.0;
        String string2 = arrstring[MathHelper.floor_double(d /= 45.0)];
        int n = MathHelper.floor_double(this.mc.thePlayer.posX);
        int n2 = (int) this.mc.thePlayer.boundingBox.minY;
        int n3 = MathHelper.floor_double(this.mc.thePlayer.posZ);
        string = !this.mc.isIntegratedServerRunning() && this.mc.theWorld != null ? string.replaceAll("%IP%", this.mc.currentServerData.serverIP) : string.replaceAll("%IP%", "?");
        return string.replaceAll("%FPS%", Minecraft.debugFPS + "").replaceAll("%DIR%", string2).replaceAll("%CPS%", this.clicks.size() + "").replaceAll("%COORDS%", String.format("%1$d, %2$d, %3$d", n, n2, n3)).replaceAll("%X%", n + "").replaceAll("%Y%", n2 + "").replaceAll("%Z%", n3 + "");
    }

    private void onTick(TickEvent event) {
        if (((String) this.customLine.getValue()).contains("%CPS%")) {
            this.clicks.removeIf(l -> l < System.currentTimeMillis() - 1000L);
        }
    }

    private void onOldClickEvent(ClickEvent event) {
        if (event.getMouseButton() == 0 && ((String) this.customLine.getValue()).contains("%CPS%")) {
            this.clicks.add(System.currentTimeMillis());
        }
    }
}
