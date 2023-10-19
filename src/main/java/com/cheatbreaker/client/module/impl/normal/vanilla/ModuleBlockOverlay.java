package com.cheatbreaker.client.module.impl.normal.vanilla;

import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.Setting;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.Config;
import net.minecraft.src.GlStateManager;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
import shadersmod.client.Shaders;

public class ModuleBlockOverlay extends AbstractModule {
    public Setting outline;
    public Setting overlay;
    public Setting outlineColor;
    public Setting overlayColor;
    public Setting lineWidth;
    public Setting gap;
    public Setting depthless;
    public Setting hidePlants;

    public ModuleBlockOverlay() {
        super("Block Overlay");
        this.setDefaultState(true);
        new Setting(this, "label").setValue("General Options");
        this.outline = new Setting(this, "Outline").setValue("Full").acceptedStringValues("OFF", "Full");
        this.overlay = new Setting(this, "Overlay").setValue("OFF").acceptedStringValues("OFF", "Full");
        this.lineWidth = new Setting(this, "Line Width").setValue(2.0F).setMinMax(1.0F, 10.0F).setCondition(() -> !(Boolean) this.outline.getValue().equals("OFF"));
        this.gap = new Setting(this, "Gap").setValue(1.0F).setMinMax(1.0f, 10.0f);
        this.depthless = new Setting(this, "Render without Depth").setValue(false);
        this.hidePlants = new Setting(this, "Hide Plants").setValue(false);
        new Setting(this, "label").setValue("Color Options").setCondition(() -> !(Boolean) this.outline.getValue().equals("OFF") || !(Boolean) this.overlay.getValue().equals("OFF"));
        this.outlineColor = new Setting(this, "Outline Color").setValue(1711276032).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCondition(() -> !(Boolean) this.outline.getValue().equals("OFF"));
        this.overlayColor = new Setting(this, "Overlay Color").setValue(1056964608).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCondition(() -> !(Boolean) this.overlay.getValue().equals("OFF"));
        this.setPreviewIcon(new ResourceLocation("client/icons/mods/overlay.png"), 36, 40);
        this.setDescription("Replace the Vanilla outline with a customizable outline and overlay.");
        this.setCreators("aycy");
    }

    /**
     * Draws the selection box for the player. Args: entityPlayer, rayTraceHit, i, itemStack, partialTickTime
     * @author  Tellinq
     */
    public void drawSelectionBox(EntityPlayer par1EntityPlayer, MovingObjectPosition par2MovingObjectPosition, int par3, float par4, WorldClient wc) {
        if (par3 == 0 && par2MovingObjectPosition.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            GL11.glEnable(GL11.GL_BLEND);
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);
            GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.4F);
            GL11.glLineWidth((Float)this.lineWidth.getValue());
            GL11.glDisable(GL11.GL_TEXTURE_2D);

            if (Config.isShaders()) {
                Shaders.disableTexture2D();
            }

            GL11.glDepthMask(false);
            if ((Boolean) this.depthless.getValue()) {
                GlStateManager.disableDepth();
            }
            int getOutlineColor = this.outlineColor.getColorValue();
            int getOverlayColor = this.overlayColor.getColorValue();

            float outlineA = (float)(getOutlineColor >> 24 & 255) / 255.0F;
            float outlineR = (float)(getOutlineColor >> 16 & 255) / 255.0F;
            float outlineG = (float)(getOutlineColor >> 8 & 255) / 255.0F;
            float outlineB = (float)(getOutlineColor & 255) / 255.0F;
            float overlayA = (float)(getOverlayColor >> 24 & 255) / 255.0F;
            float overlayR = (float)(getOverlayColor >> 16 & 255) / 255.0F;
            float overlayG = (float)(getOverlayColor >> 8 & 255) / 255.0F;
            float overlayB = (float)(getOverlayColor & 255) / 255.0F;
            float var5 = (Float)this.gap.getValue() * 0.002f;
            Block var6 = wc.getBlock(par2MovingObjectPosition.blockX, par2MovingObjectPosition.blockY, par2MovingObjectPosition.blockZ);
            boolean hidePlants = var6.getMaterial() != Material.plants && var6.getMaterial() != Material.vine || !(Boolean) this.hidePlants.getValue();
            if (var6.getMaterial() != Material.air && hidePlants) {
                var6.setBlockBoundsBasedOnState(wc, par2MovingObjectPosition.blockX, par2MovingObjectPosition.blockY, par2MovingObjectPosition.blockZ);
                double var7 = par1EntityPlayer.lastTickPosX + (par1EntityPlayer.posX - par1EntityPlayer.lastTickPosX) * (double)par4;
                double var9 = par1EntityPlayer.lastTickPosY + (par1EntityPlayer.posY - par1EntityPlayer.lastTickPosY) * (double)par4;
                double var11 = par1EntityPlayer.lastTickPosZ + (par1EntityPlayer.posZ - par1EntityPlayer.lastTickPosZ) * (double)par4;
                if (!this.outline.getValue().equals("OFF")) {
                    GL11.glColor4f(outlineR, outlineG, outlineB, outlineA);
                    drawOutlinedBoundingBox(var6.getSelectedBoundingBoxFromPool(wc, par2MovingObjectPosition.blockX, par2MovingObjectPosition.blockY, par2MovingObjectPosition.blockZ).expand((double)var5, (double)var5, (double)var5).getOffsetBoundingBox(-var7, -var9, -var11), -1);
                }
                if (!this.overlay.getValue().equals("OFF")) {
                    GL11.glColor4f(overlayR, overlayG, overlayB, overlayA);
                    drawOverlayedBoundingBox(var6.getSelectedBoundingBoxFromPool(wc, par2MovingObjectPosition.blockX, par2MovingObjectPosition.blockY, par2MovingObjectPosition.blockZ).expand((double)var5, (double)var5, (double)var5).getOffsetBoundingBox(-var7, -var9, -var11), -1);
                }
            }

            GL11.glDepthMask(true);
            GL11.glEnable(GL11.GL_TEXTURE_2D);

            if (Config.isShaders()) {
                Shaders.enableTexture2D();
            }
            GlStateManager.enableDepth();
            GL11.glDisable(GL11.GL_BLEND);
        }
    }

    /**
     * Draws lines for the edges of the bounding box.
     * @author  Mojang
     */
    public static void drawOutlinedBoundingBox(AxisAlignedBB p_147590_0_, int p_147590_1_) {
        Tessellator t = Tessellator.instance;
        t.startDrawing(3);

        if (p_147590_1_ != -1) {
            t.setColorOpaque_I(p_147590_1_);
        }

        t.addVertex(p_147590_0_.minX, p_147590_0_.minY, p_147590_0_.minZ);
        t.addVertex(p_147590_0_.maxX, p_147590_0_.minY, p_147590_0_.minZ);
        t.addVertex(p_147590_0_.maxX, p_147590_0_.minY, p_147590_0_.maxZ);
        t.addVertex(p_147590_0_.minX, p_147590_0_.minY, p_147590_0_.maxZ);
        t.addVertex(p_147590_0_.minX, p_147590_0_.minY, p_147590_0_.minZ);
        t.draw();
        t.startDrawing(3);

        if (p_147590_1_ != -1) {
            t.setColorOpaque_I(p_147590_1_);
        }

        t.addVertex(p_147590_0_.minX, p_147590_0_.maxY, p_147590_0_.minZ);
        t.addVertex(p_147590_0_.maxX, p_147590_0_.maxY, p_147590_0_.minZ);
        t.addVertex(p_147590_0_.maxX, p_147590_0_.maxY, p_147590_0_.maxZ);
        t.addVertex(p_147590_0_.minX, p_147590_0_.maxY, p_147590_0_.maxZ);
        t.addVertex(p_147590_0_.minX, p_147590_0_.maxY, p_147590_0_.minZ);
        t.draw();
        t.startDrawing(1);

        if (p_147590_1_ != -1) {
            t.setColorOpaque_I(p_147590_1_);
        }

        t.addVertex(p_147590_0_.minX, p_147590_0_.minY, p_147590_0_.minZ);
        t.addVertex(p_147590_0_.minX, p_147590_0_.maxY, p_147590_0_.minZ);
        t.addVertex(p_147590_0_.maxX, p_147590_0_.minY, p_147590_0_.minZ);
        t.addVertex(p_147590_0_.maxX, p_147590_0_.maxY, p_147590_0_.minZ);
        t.addVertex(p_147590_0_.maxX, p_147590_0_.minY, p_147590_0_.maxZ);
        t.addVertex(p_147590_0_.maxX, p_147590_0_.maxY, p_147590_0_.maxZ);
        t.addVertex(p_147590_0_.minX, p_147590_0_.minY, p_147590_0_.maxZ);
        t.addVertex(p_147590_0_.minX, p_147590_0_.maxY, p_147590_0_.maxZ);
        t.draw();
    }

    /**
     * Draws rectangles around the bounding box.
     * @author  Tellinq
     */
    public static void drawOverlayedBoundingBox(AxisAlignedBB var0, int var1) {
        Tessellator t = Tessellator.instance;
        t.startDrawing(7);
        if (var1 != -1) {
            t.setColorOpaque_I(var1);
        }
        t.addVertex(var0.minX, var0.minY, var0.minZ);
        t.addVertex(var0.minX, var0.maxY, var0.minZ);
        t.addVertex(var0.maxX, var0.minY, var0.minZ);
        t.addVertex(var0.maxX, var0.maxY, var0.minZ);
        t.addVertex(var0.maxX, var0.minY, var0.maxZ);
        t.addVertex(var0.maxX, var0.maxY, var0.maxZ);
        t.addVertex(var0.minX, var0.minY, var0.maxZ);
        t.addVertex(var0.minX, var0.maxY, var0.maxZ);
        t.draw();
        t.startDrawing(7);
        if (var1 != -1) {
            t.setColorOpaque_I(var1);
        }
        t.addVertex(var0.maxX, var0.maxY, var0.minZ);
        t.addVertex(var0.maxX, var0.minY, var0.minZ);
        t.addVertex(var0.minX, var0.maxY, var0.minZ);
        t.addVertex(var0.minX, var0.minY, var0.minZ);
        t.addVertex(var0.minX, var0.maxY, var0.maxZ);
        t.addVertex(var0.minX, var0.minY, var0.maxZ);
        t.addVertex(var0.maxX, var0.maxY, var0.maxZ);
        t.addVertex(var0.maxX, var0.minY, var0.maxZ);
        t.draw();
        t.startDrawing(7);
        if (var1 != -1) {
            t.setColorOpaque_I(var1);
        }
        t.addVertex(var0.minX, var0.maxY, var0.minZ);
        t.addVertex(var0.maxX, var0.maxY, var0.minZ);
        t.addVertex(var0.maxX, var0.maxY, var0.maxZ);
        t.addVertex(var0.minX, var0.maxY, var0.maxZ);
        t.addVertex(var0.minX, var0.maxY, var0.minZ);
        t.addVertex(var0.minX, var0.maxY, var0.maxZ);
        t.addVertex(var0.maxX, var0.maxY, var0.maxZ);
        t.addVertex(var0.maxX, var0.maxY, var0.minZ);
        t.draw();
        t.startDrawing(7);
        if (var1 != -1) {
            t.setColorOpaque_I(var1);
        }
        t.addVertex(var0.minX, var0.minY, var0.minZ);
        t.addVertex(var0.maxX, var0.minY, var0.minZ);
        t.addVertex(var0.maxX, var0.minY, var0.maxZ);
        t.addVertex(var0.minX, var0.minY, var0.maxZ);
        t.addVertex(var0.minX, var0.minY, var0.minZ);
        t.addVertex(var0.minX, var0.minY, var0.maxZ);
        t.addVertex(var0.maxX, var0.minY, var0.maxZ);
        t.addVertex(var0.maxX, var0.minY, var0.minZ);
        t.draw();
        t.startDrawing(7);
        if (var1 != -1) {
            t.setColorOpaque_I(var1);
        }
        t.addVertex(var0.minX, var0.minY, var0.minZ);
        t.addVertex(var0.minX, var0.maxY, var0.minZ);
        t.addVertex(var0.minX, var0.minY, var0.maxZ);
        t.addVertex(var0.minX, var0.maxY, var0.maxZ);
        t.addVertex(var0.maxX, var0.minY, var0.maxZ);
        t.addVertex(var0.maxX, var0.maxY, var0.maxZ);
        t.addVertex(var0.maxX, var0.minY, var0.minZ);
        t.addVertex(var0.maxX, var0.maxY, var0.minZ);
        t.draw();
        t.startDrawing(7);
        if (var1 != -1) {
            t.setColorOpaque_I(var1);
        }
        t.addVertex(var0.minX, var0.maxY, var0.maxZ);
        t.addVertex(var0.minX, var0.minY, var0.maxZ);
        t.addVertex(var0.minX, var0.maxY, var0.minZ);
        t.addVertex(var0.minX, var0.minY, var0.minZ);
        t.addVertex(var0.maxX, var0.maxY, var0.minZ);
        t.addVertex(var0.maxX, var0.minY, var0.minZ);
        t.addVertex(var0.maxX, var0.maxY, var0.maxZ);
        t.addVertex(var0.maxX, var0.minY, var0.maxZ);
        t.draw();
    }
}

