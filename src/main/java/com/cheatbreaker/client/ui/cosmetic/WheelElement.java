package com.cheatbreaker.client.ui.cosmetic;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.ui.mainmenu.AbstractElement;
import com.cheatbreaker.client.ui.util.RenderUtil;
import com.cheatbreaker.client.ui.fading.ColorFade;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.opengl.GL11;

import java.awt.*;

public class WheelElement extends AbstractElement {
    private final WheelGUI wheelgui;
    private final float location;
    private final IconButton iconButton;
    private final Color baseColor = new Color(0.3F, 0.3F, 0.3F, 0.3F);
    private ColorFade radialColorFade;
    private final ColorFade iconColorFade;

    public WheelElement(WheelGUI var1, int var2, IconButton var3) {
        this.wheelgui = var1;
        float var4 = 45.0F;
        this.location = var4 / 2.0F + (float)var2 * var4;
        this.iconButton = var3;
        this.radialColorFade = new ColorFade(500L, (new Color(0.2F, 0.2F, 0.2F, 0.1F)).getRGB(),
                CheatBreaker.getInstance().getGlobalSettings().emoteRadialColor.getColorValue());
        this.iconColorFade = new ColorFade(500L, (new Color(0.0F, 0.0F, 0.0F, 0.9F)).getRGB(), -1);
    }

    @Override
    protected void handleElementDraw(float var1, float var2, boolean var3) {
        this.radialColorFade.setEndColor(CheatBreaker.getInstance().getGlobalSettings().emoteRadialColor.getColorValue());
        boolean var4 = this.iconButton != null && this.isMouseInsideElement(var1, var2) && var3;
        Color var5 = this.radialColorFade.getColor(var4);
        ScaledResolution var6 = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        int var7 = var6.getScaledWidth();
        int var8 = var6.getScaledHeight();
        float var9 = 10.0F;
        float var10 = (float)this.wheelgui.tick >= var9 ? 1.0F : (float)this.wheelgui.tick / var9;
        GL11.glPushMatrix();
        if (this.iconButton == null) {
            GL11.glColor4d((float)this.baseColor.getRed() / 255.0F, (float)this.baseColor.getGreen() / 255.0F, (float)this.baseColor.getBlue() / 255.0F, (float)this.baseColor.getAlpha() / 255.0F * var10);
        } else {
            GL11.glColor4d((float)var5.getRed() / 255.0F, (float)var5.getGreen() / 255.0F, (float)var5.getBlue() / 255.0F, (float)var5.getAlpha() / 255.0F * var10);
        }

        RenderUtil.drawRadial((float)var7 / 2.0F, (float)var8 / 2.0F, 88.0, 20.0, 360.0F - this.location + 90.0F, 360.0F - this.location + 90.0F + 45.0F);
        GL11.glPopMatrix();
        if (this.iconButton != null) {
            var5 = this.iconColorFade.getColor(var4);
            GL11.glPushMatrix();
            float var11 = this.location - 90.0F;
            var11 -= 22.5F;
            double var12 = Math.toRadians(var11);
            byte var14 = 60;
            double var15 = (double)((float)var7 / 2.0F) + (double)var14 * Math.cos(var12);
            double var17 = (double)((float)var8 / 2.0F) + (double)var14 * Math.sin(var12);
            GL11.glColor4d((float)var5.getRed() / 255.0F, (float)var5.getGreen() / 255.0F, (float)var5.getBlue() / 255.0F, (float)var5.getAlpha() / 255.0F * var10);
            RenderUtil.renderIcon(this.iconButton.getImage(), 20.0F, (float)var15 - 20.0F, (float)var17 - 20.0F);
            GL11.glPopMatrix();
        }

    }

    public boolean isMouseInsideElement(float var1, float var2) {
        ScaledResolution var3 = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        int var4 = var3.getScaledWidth();
        int var5 = var3.getScaledHeight();
        int var6 = (int)(var1 - (float)(var4 / 2));
        int var7 = (int)(var2 - (float)(var5 / 2));
        double var8 = Math.sqrt(var6 * var6 + var7 * var7);
        double var10 = Math.toDegrees(Math.atan2(var7, var6)) + 90.0;
        double var12 = this.location - 45.0F;
        if (var12 < 0.0) {
            var12 += 360.0;
        }

        double var14 = this.location;
        boolean var16 = var10 < 0.0;
        if (var12 > var14) {
            var14 += 360.0;
            var16 = true;
        }

        if (var16) {
            var10 += 360.0;
        }

        return var8 >= 20.0 && var8 <= 175.0 && var10 >= var12 && var10 <= var14;
    }
}
