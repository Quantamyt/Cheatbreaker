package com.cheatbreaker.client.module.impl.normal.hypixel;

import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.CustomizationLevel;
import com.cheatbreaker.client.module.data.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderTNTPrimed;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.text.DecimalFormat;

public class ModuleTNTTimer extends AbstractModule {

    private boolean showHypixelTime;

    public Setting dynamicTextColor;
    public Setting staticTextColor;
    public Setting textShadow;

    public Setting showBackground;
    public Setting backgroundColor;

    public ModuleTNTTimer() {
        super("TNT Timer");
        this.setDefaultState(false);
        this.setDescription("Shows a timer above TNT.");
        this.setCreators("Sk1er");
        this.setAliases("TNT Countdown");
        this.setPreviewIcon(new ResourceLocation("client/icons/mods/tnt.png"), 36, 40);

        new Setting(this, "label").setValue("Text Options");
        this.textShadow = new Setting(this, "Text Shadow").setValue(false).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.dynamicTextColor = new Setting(this, "Dynamic Text Color").setValue(true).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.staticTextColor = new Setting(this, "Static Text Color").setValue(-1).setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> !this.dynamicTextColor.getBooleanValue());

        new Setting(this, "label").setValue("Background Options");
        this.showBackground = new Setting(this, "Show Background").setValue(true).setCustomizationLevel(CustomizationLevel.SIMPLE);
        this.backgroundColor = new Setting(this, "Background Color").setValue(1862270976).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCustomizationLevel(CustomizationLevel.SIMPLE).setCondition(() -> this.showBackground.getBooleanValue());
    }

    public void drawTimer(RenderTNTPrimed tntRenderer, EntityTNTPrimed block, double x, double y, double z, float partialTicks) {

        if (Minecraft.getMinecraft().currentServerData != null && Minecraft.getMinecraft().theWorld != null) {
            this.showHypixelTime = Minecraft.getMinecraft().getCurrentServerData().serverIP.toLowerCase().contains("hypixel");
        }

        float someScaleInRenderLivingEntity = 0.02666667F;
        int timeLeft = this.showHypixelTime ? block.fuse - 28 : block.fuse;

        if (timeLeft >= 1) {
            if (block.getDistanceSqToEntity(tntRenderer.getRenderManager().livingPlayer) <= 4095.0D) {
                FontRenderer fontRenderer = tntRenderer.getFontRendererFromRenderManager();

                GlStateManager.pushMatrix();
                GlStateManager.translate((float) x + 0.0F, (float) y + block.height + 0.5F, (float) z);
                GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(-tntRenderer.getRenderManager().playerViewY, 0.0F, 1.0F, 0.0F);

                GlStateManager.rotate(tntRenderer.getRenderManager().playerViewX * (Minecraft.getMinecraft().gameSettings.thirdPersonView == 2 ? -1F : 1F), 1.0F, 0.0F, 0.0F);

                GlStateManager.scale(-someScaleInRenderLivingEntity, -someScaleInRenderLivingEntity, someScaleInRenderLivingEntity);
                GlStateManager.disableLighting();
                GlStateManager.depthMask(false);
                GlStateManager.disableDepth();
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);

                Tessellator tessellator = Tessellator.getInstance();
                WorldRenderer worldrenderer = tessellator.getWorldRenderer();


                GlStateManager.enableDepth();
                GlStateManager.depthMask(true);
                GlStateManager.disableTexture2D();

                int stringWidth = fontRenderer.getStringWidth(this.getFormattedTime(timeLeft, partialTicks)) >> 1;
                if (this.showBackground.getBooleanValue()) {
                    worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
                    worldrenderer.pos((-stringWidth - 1), -1.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    worldrenderer.pos((-stringWidth - 1), 8.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    worldrenderer.pos((stringWidth + 1), 8.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    worldrenderer.pos((stringWidth + 1), -1.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    tessellator.draw();
                }

                GlStateManager.enableTexture2D();

                fontRenderer.drawString(this.getFormattedTime(timeLeft, partialTicks),
                        -fontRenderer.getStringWidth(this.getFormattedTime(timeLeft, partialTicks)) >> 1,
                        0, this.getTextColor(timeLeft).getRGB(), this.textShadow.getBooleanValue());

                GlStateManager.enableLighting();
                GlStateManager.disableBlend();
                GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                GlStateManager.popMatrix();
            }
        }
    }

    private String getFormattedTime(int timeLeft, float partialTicks) {
        return new DecimalFormat("0.00").format(((float) timeLeft - partialTicks) / 20.0F);
    }

    private Color getTextColor(int timeLeft) {

        if (this.dynamicTextColor.getBooleanValue()) {
            return new Color(1.0F - Math.min((float) timeLeft / (this.showHypixelTime ? 52.0F : 80.0F), 1.0F),
                    Math.min((float) timeLeft / (this.showHypixelTime ? 52.0F : 80.0F), 1.0F), 0.0F);
        }
        return this.staticTextColor.getColorFromColorValue(this.staticTextColor.getColorValue());
    }

}
