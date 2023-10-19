package com.cheatbreaker.client.module.impl.normal.vanilla;

import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.Setting;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.Config;
import net.minecraft.util.*;
import net.optifine.model.BlockModelUtils;
import net.optifine.shaders.Shaders;
import org.lwjgl.opengl.GL11;

import java.awt.*;

/**
 * @Module - ModuleBlockOverlay
 * @see AbstractModule
 *
 * This module activates a custom overlay when you look at a block.
 */
public class ModuleBlockOverlay extends AbstractModule {
    public Setting outline;

    public Setting overlay;

    public Setting outlineColor;

    public Setting outline2Color;

    public Setting overlayColor;

    public Setting overlay2Color;

    public Setting lineWidth;

    public Setting gap;

    public Setting depthless;

    public Setting hidePlants;

    private static final Tessellator TESSELLATOR = Tessellator.getInstance();

    private static final WorldRenderer WORLD_RENDERER = TESSELLATOR.getWorldRenderer();

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
        this.outline2Color = new Setting(this, "Outline 2 Color").setValue(1711276032).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCondition(() -> !(Boolean) this.outline.getValue().equals("OFF"));
        this.overlayColor = new Setting(this, "Overlay Color").setValue(1056964608).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCondition(() -> !(Boolean) this.overlay.getValue().equals("OFF"));
        this.overlay2Color = new Setting(this, "Overlay 2 Color").setValue(1056964608).setMinMax(Integer.MIN_VALUE, Integer.MAX_VALUE).setCondition(() -> !(Boolean) this.overlay.getValue().equals("OFF"));
        this.setPreviewIcon(new ResourceLocation("client/icons/mods/overlay.png"), 36, 40);
        this.setDescription("Replace the Vanilla outline with a customizable outline and overlay.");
        this.setCreators("aycy");
    }

    /**
     * Draws the selection box for the player. Args: entityPlayer, rayTraceHit, i, itemStack, partialTickTime
     *
     * @author Tellinq
     */
    public void drawSelectionBox(EntityPlayer player, MovingObjectPosition movingObjectPositionIn, int execute, float partialTicks, WorldClient wc) {
        if (execute == 0 && movingObjectPositionIn.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            GlStateManager.color(1.0F, 0.0F, 0.0F, 0.4F);
            GL11.glLineWidth(2.0F);
            GlStateManager.disableTexture2D();

            if (Config.isShaders()) {
                Shaders.disableTexture2D();
            }

            GlStateManager.depthMask(false);
            float f = 0.002F;
            BlockPos blockpos = movingObjectPositionIn.getBlockPos();
            Block block = wc.getBlockState(blockpos).getBlock();

            if (block.getMaterial() != Material.air && wc.getWorldBorder().contains(blockpos)) {
                block.setBlockBoundsBasedOnState(wc, blockpos);
                double d0 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) partialTicks;
                double d1 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) partialTicks;
                double d2 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double) partialTicks;
                AxisAlignedBB axisalignedbb = block.getSelectedBoundingBox(wc, blockpos);
                Block.EnumOffsetType enumoffsettype = block.getOffsetType();

                if (enumoffsettype != Block.EnumOffsetType.NONE) {
                    axisalignedbb = BlockModelUtils.getOffsetBoundingBox(axisalignedbb, enumoffsettype, blockpos);
                }

                drawBlock(axisalignedbb.expand(0.0020000000949949026D, 0.0020000000949949026D, 0.0020000000949949026D).offset(-d0, -d1, -d2), null, this.overlayColor.getColorValue(), this.overlay2Color.getColorValue(), this.outlineColor.getColorValue(), this.outline2Color.getColorValue(), this.overlay.getValue().equals("Full"), this.outline.getValue().equals("Full"));
//                drawOutlinedBoundingBox(axisalignedbb.expand(0.0020000000949949026D, 0.0020000000949949026D, 0.0020000000949949026D).offset(-d0, -d1, -d2));
//                drawOverlayedBoundingBox(axisalignedbb.expand(0.0020000000949949026D, 0.0020000000949949026D, 0.0020000000949949026D).offset(-d0, -d1, -d2));
            }

            GlStateManager.depthMask(true);
            GlStateManager.enableTexture2D();

            if (Config.isShaders()) {
                Shaders.enableTexture2D();
            }

            GlStateManager.disableBlend();
        }
    }

    /**
     * Draws lines for the edges of the bounding box.
     *
     * @author Mojang
     */
    public static void drawOutlinedBoundingBox(AxisAlignedBB boundingBox) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(3, DefaultVertexFormats.POSITION);
        worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
        worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
        worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
        worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        tessellator.draw();
        worldrenderer.begin(3, DefaultVertexFormats.POSITION);
        worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
        worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        tessellator.draw();
        worldrenderer.begin(1, DefaultVertexFormats.POSITION);
        worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).endVertex();
        worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).endVertex();
        worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).endVertex();
        worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).endVertex();
        worldrenderer.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).endVertex();
        worldrenderer.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        worldrenderer.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).endVertex();
        worldrenderer.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).endVertex();
        tessellator.draw();
    }

    /**
     * Draws rectangles around the bounding box.
     *
     * @author Tellinq
     */
    public static void drawOverlayedBoundingBox(AxisAlignedBB var0) {
        Tessellator t = Tessellator.getInstance();
        WorldRenderer worldrenderer = t.getWorldRenderer();
        worldrenderer.begin(7, DefaultVertexFormats.POSITION);

        worldrenderer.pos(var0.minX, var0.minY, var0.minZ);
        worldrenderer.pos(var0.minX, var0.maxY, var0.minZ);
        worldrenderer.pos(var0.maxX, var0.minY, var0.minZ);
        worldrenderer.pos(var0.maxX, var0.maxY, var0.minZ);
        worldrenderer.pos(var0.maxX, var0.minY, var0.maxZ);
        worldrenderer.pos(var0.maxX, var0.maxY, var0.maxZ);
        worldrenderer.pos(var0.minX, var0.minY, var0.maxZ);
        worldrenderer.pos(var0.minX, var0.maxY, var0.maxZ);
        t.draw();

        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(var0.maxX, var0.maxY, var0.minZ);
        worldrenderer.pos(var0.maxX, var0.minY, var0.minZ);
        worldrenderer.pos(var0.minX, var0.maxY, var0.minZ);
        worldrenderer.pos(var0.minX, var0.minY, var0.minZ);
        worldrenderer.pos(var0.minX, var0.maxY, var0.maxZ);
        worldrenderer.pos(var0.minX, var0.minY, var0.maxZ);
        worldrenderer.pos(var0.maxX, var0.maxY, var0.maxZ);
        worldrenderer.pos(var0.maxX, var0.minY, var0.maxZ);
        t.draw();

        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(var0.minX, var0.maxY, var0.minZ);
        worldrenderer.pos(var0.maxX, var0.maxY, var0.minZ);
        worldrenderer.pos(var0.maxX, var0.maxY, var0.maxZ);
        worldrenderer.pos(var0.minX, var0.maxY, var0.maxZ);
        worldrenderer.pos(var0.minX, var0.maxY, var0.minZ);
        worldrenderer.pos(var0.minX, var0.maxY, var0.maxZ);
        worldrenderer.pos(var0.maxX, var0.maxY, var0.maxZ);
        worldrenderer.pos(var0.maxX, var0.maxY, var0.minZ);
        t.draw();

        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(var0.minX, var0.minY, var0.minZ);
        worldrenderer.pos(var0.maxX, var0.minY, var0.minZ);
        worldrenderer.pos(var0.maxX, var0.minY, var0.maxZ);
        worldrenderer.pos(var0.minX, var0.minY, var0.maxZ);
        worldrenderer.pos(var0.minX, var0.minY, var0.minZ);
        worldrenderer.pos(var0.minX, var0.minY, var0.maxZ);
        worldrenderer.pos(var0.maxX, var0.minY, var0.maxZ);
        worldrenderer.pos(var0.maxX, var0.minY, var0.minZ);
        t.draw();

        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(var0.minX, var0.minY, var0.minZ);
        worldrenderer.pos(var0.minX, var0.maxY, var0.minZ);
        worldrenderer.pos(var0.minX, var0.minY, var0.maxZ);
        worldrenderer.pos(var0.minX, var0.maxY, var0.maxZ);
        worldrenderer.pos(var0.maxX, var0.minY, var0.maxZ);
        worldrenderer.pos(var0.maxX, var0.maxY, var0.maxZ);
        worldrenderer.pos(var0.maxX, var0.minY, var0.minZ);
        worldrenderer.pos(var0.maxX, var0.maxY, var0.minZ);
        t.draw();

        worldrenderer.begin(7, DefaultVertexFormats.POSITION);
        worldrenderer.pos(var0.minX, var0.maxY, var0.maxZ);
        worldrenderer.pos(var0.minX, var0.minY, var0.maxZ);
        worldrenderer.pos(var0.minX, var0.maxY, var0.minZ);
        worldrenderer.pos(var0.minX, var0.minY, var0.minZ);
        worldrenderer.pos(var0.maxX, var0.maxY, var0.minZ);
        worldrenderer.pos(var0.maxX, var0.minY, var0.minZ);
        worldrenderer.pos(var0.maxX, var0.maxY, var0.maxZ);
        worldrenderer.pos(var0.maxX, var0.minY, var0.maxZ);
        t.draw();
    }

    public static void drawBlock(AxisAlignedBB box, EnumFacing side, int overlayStartColor, int overlayEndColor, int outlineStartColor, int outlineEndColor, boolean overlay, boolean outline) {
        if (side == null) {
            drawBlockFull(box, new Color(overlayStartColor, true), new Color(overlayEndColor, true), new Color(outlineStartColor, true), new Color(outlineEndColor, true), overlay, outline);
        } else {
            drawBlockSide(box, side, new Color(overlayStartColor, true), new Color(overlayEndColor, true), new Color(outlineStartColor, true), new Color(outlineEndColor, true), overlay, outline);
        }
    }

    private static void drawBlockFull(AxisAlignedBB box, Color overlayStartColor, Color overlayEndColor, Color outlineStartColor, Color outlineEndColor, boolean overlay, boolean outline) {
        if (overlay) {
            drawBlockTop(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, true, false);
            drawBlockBottom(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, true, false);
            drawBlockNorth(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, true, false);
            drawBlockEast(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, true, false);
            drawBlockSouth(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, true, false);
            drawBlockWest(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, true, false);
        }
        if (outline) {
            drawBlockTop(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, false, true);
            drawBlockBottom(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, false, true);
            drawBlockNorth(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, false, true);
            drawBlockEast(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, false, true);
            drawBlockSouth(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, false, true);
            drawBlockWest(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, false, true);
        }
    }

    public static void drawBlockSide(AxisAlignedBB box, EnumFacing side, Color overlayStartColor, Color overlayEndColor, Color outlineStartColor, Color outlineEndColor, boolean overlay, boolean outline) {
        switch (side) {
            case UP: {
                drawBlockTop(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, overlay, outline);
                break;
            }
            case DOWN: {
                drawBlockBottom(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, overlay, outline);
                break;
            }
            case NORTH: {
                drawBlockNorth(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, overlay, outline);
                break;
            }
            case EAST: {
                drawBlockEast(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, overlay, outline);
                break;
            }
            case SOUTH: {
                drawBlockSouth(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, overlay, outline);
                break;
            }
            case WEST: {
                drawBlockWest(box, overlayStartColor, overlayEndColor, outlineStartColor, outlineEndColor, overlay, outline);
            }
        }
    }

    private static void drawBlockTop(AxisAlignedBB box, Color overlayStartColor, Color overlayEndColor, Color outlineStartColor, Color outlineEndColor, boolean overlay, boolean outline) {
        if (overlay) {
            WORLD_RENDERER.begin(7, DefaultVertexFormats.POSITION_COLOR);
            WORLD_RENDERER.pos(box.minX, box.maxY, box.maxZ).color(overlayStartColor.getRed(), overlayStartColor.getGreen(), overlayStartColor.getBlue(), overlayStartColor.getAlpha()).endVertex();
            WORLD_RENDERER.pos(box.maxX, box.maxY, box.maxZ).color(overlayEndColor.getRed(), overlayEndColor.getGreen(), overlayEndColor.getBlue(), overlayEndColor.getAlpha()).endVertex();
            WORLD_RENDERER.pos(box.maxX, box.maxY, box.minZ).color(overlayStartColor.getRed(), overlayStartColor.getGreen(), overlayStartColor.getBlue(), overlayStartColor.getAlpha()).endVertex();
            WORLD_RENDERER.pos(box.minX, box.maxY, box.minZ).color(overlayEndColor.getRed(), overlayEndColor.getGreen(), overlayEndColor.getBlue(), overlayEndColor.getAlpha()).endVertex();
            TESSELLATOR.draw();
        }
        if (outline) {
            WORLD_RENDERER.begin(2, DefaultVertexFormats.POSITION_COLOR);
            WORLD_RENDERER.pos(box.minX, box.maxY, box.maxZ).color(outlineStartColor.getRed(), outlineStartColor.getGreen(), outlineStartColor.getBlue(), outlineStartColor.getAlpha()).endVertex();
            WORLD_RENDERER.pos(box.maxX, box.maxY, box.maxZ).color(outlineEndColor.getRed(), outlineEndColor.getGreen(), outlineEndColor.getBlue(), outlineEndColor.getAlpha()).endVertex();
            WORLD_RENDERER.pos(box.maxX, box.maxY, box.minZ).color(outlineStartColor.getRed(), outlineStartColor.getGreen(), outlineStartColor.getBlue(), outlineStartColor.getAlpha()).endVertex();
            WORLD_RENDERER.pos(box.minX, box.maxY, box.minZ).color(outlineEndColor.getRed(), outlineEndColor.getGreen(), outlineEndColor.getBlue(), outlineEndColor.getAlpha()).endVertex();
            TESSELLATOR.draw();
        }
    }

    private static void drawBlockBottom(AxisAlignedBB box, Color overlayStartColor, Color overlayEndColor, Color outlineStartColor, Color outlineEndColor, boolean overlay, boolean outline) {
        if (overlay) {
            WORLD_RENDERER.begin(7, DefaultVertexFormats.POSITION_COLOR);
            WORLD_RENDERER.pos(box.maxX, box.minY, box.maxZ).color(overlayStartColor.getRed(), overlayStartColor.getGreen(), overlayStartColor.getBlue(), overlayStartColor.getAlpha()).endVertex();
            WORLD_RENDERER.pos(box.minX, box.minY, box.maxZ).color(overlayEndColor.getRed(), overlayEndColor.getGreen(), overlayEndColor.getBlue(), overlayEndColor.getAlpha()).endVertex();
            WORLD_RENDERER.pos(box.minX, box.minY, box.minZ).color(overlayStartColor.getRed(), overlayStartColor.getGreen(), overlayStartColor.getBlue(), overlayStartColor.getAlpha()).endVertex();
            WORLD_RENDERER.pos(box.maxX, box.minY, box.minZ).color(overlayEndColor.getRed(), overlayEndColor.getGreen(), overlayEndColor.getBlue(), overlayEndColor.getAlpha()).endVertex();
            TESSELLATOR.draw();
        }
        if (outline) {
            WORLD_RENDERER.begin(2, DefaultVertexFormats.POSITION_COLOR);
            WORLD_RENDERER.pos(box.maxX, box.minY, box.maxZ).color(outlineStartColor.getRed(), outlineStartColor.getGreen(), outlineStartColor.getBlue(), outlineStartColor.getAlpha()).endVertex();
            WORLD_RENDERER.pos(box.minX, box.minY, box.maxZ).color(outlineEndColor.getRed(), outlineEndColor.getGreen(), outlineEndColor.getBlue(), outlineEndColor.getAlpha()).endVertex();
            WORLD_RENDERER.pos(box.minX, box.minY, box.minZ).color(outlineStartColor.getRed(), outlineStartColor.getGreen(), outlineStartColor.getBlue(), outlineStartColor.getAlpha()).endVertex();
            WORLD_RENDERER.pos(box.maxX, box.minY, box.minZ).color(outlineEndColor.getRed(), outlineEndColor.getGreen(), outlineEndColor.getBlue(), outlineEndColor.getAlpha()).endVertex();
            TESSELLATOR.draw();
        }
    }

    private static void drawBlockNorth(AxisAlignedBB box, Color overlayStartColor, Color overlayEndColor, Color outlineStartColor, Color outlineEndColor, boolean overlay, boolean outline) {
        if (overlay) {
            WORLD_RENDERER.begin(7, DefaultVertexFormats.POSITION_COLOR);
            WORLD_RENDERER.pos(box.maxX, box.maxY, box.minZ).color(overlayStartColor.getRed(), overlayStartColor.getGreen(), overlayStartColor.getBlue(), overlayStartColor.getAlpha()).endVertex();
            WORLD_RENDERER.pos(box.maxX, box.minY, box.minZ).color(overlayEndColor.getRed(), overlayEndColor.getGreen(), overlayEndColor.getBlue(), overlayEndColor.getAlpha()).endVertex();
            WORLD_RENDERER.pos(box.minX, box.minY, box.minZ).color(overlayStartColor.getRed(), overlayStartColor.getGreen(), overlayStartColor.getBlue(), overlayStartColor.getAlpha()).endVertex();
            WORLD_RENDERER.pos(box.minX, box.maxY, box.minZ).color(overlayEndColor.getRed(), overlayEndColor.getGreen(), overlayEndColor.getBlue(), overlayEndColor.getAlpha()).endVertex();
            TESSELLATOR.draw();
        }
        if (outline) {
            WORLD_RENDERER.begin(2, DefaultVertexFormats.POSITION_COLOR);
            WORLD_RENDERER.pos(box.maxX, box.maxY, box.minZ).color(outlineStartColor.getRed(), outlineStartColor.getGreen(), outlineStartColor.getBlue(), outlineStartColor.getAlpha()).endVertex();
            WORLD_RENDERER.pos(box.maxX, box.minY, box.minZ).color(outlineEndColor.getRed(), outlineEndColor.getGreen(), outlineEndColor.getBlue(), outlineEndColor.getAlpha()).endVertex();
            WORLD_RENDERER.pos(box.minX, box.minY, box.minZ).color(outlineStartColor.getRed(), outlineStartColor.getGreen(), outlineStartColor.getBlue(), outlineStartColor.getAlpha()).endVertex();
            WORLD_RENDERER.pos(box.minX, box.maxY, box.minZ).color(outlineEndColor.getRed(), outlineEndColor.getGreen(), outlineEndColor.getBlue(), outlineEndColor.getAlpha()).endVertex();
            WORLD_RENDERER.pos(box.maxX, box.maxY, box.minZ).color(outlineStartColor.getRed(), outlineStartColor.getGreen(), outlineStartColor.getBlue(), outlineStartColor.getAlpha()).endVertex();
            TESSELLATOR.draw();
        }
    }

    private static void drawBlockEast(AxisAlignedBB box, Color overlayStartColor, Color overlayEndColor, Color outlineStartColor, Color outlineEndColor, boolean overlay, boolean outline) {
        if (overlay) {
            WORLD_RENDERER.begin(7, DefaultVertexFormats.POSITION_COLOR);
            WORLD_RENDERER.pos(box.maxX, box.maxY, box.minZ).color(overlayStartColor.getRed(), overlayStartColor.getGreen(), overlayStartColor.getBlue(), overlayStartColor.getAlpha()).endVertex();
            WORLD_RENDERER.pos(box.maxX, box.maxY, box.maxZ).color(overlayEndColor.getRed(), overlayEndColor.getGreen(), overlayEndColor.getBlue(), overlayEndColor.getAlpha()).endVertex();
            WORLD_RENDERER.pos(box.maxX, box.minY, box.maxZ).color(overlayStartColor.getRed(), overlayStartColor.getGreen(), overlayStartColor.getBlue(), overlayStartColor.getAlpha()).endVertex();
            WORLD_RENDERER.pos(box.maxX, box.minY, box.minZ).color(overlayEndColor.getRed(), overlayEndColor.getGreen(), overlayEndColor.getBlue(), overlayEndColor.getAlpha()).endVertex();
            TESSELLATOR.draw();
        }
        if (outline) {
            WORLD_RENDERER.begin(2, DefaultVertexFormats.POSITION_COLOR);
            WORLD_RENDERER.pos(box.maxX, box.maxY, box.minZ).color(outlineStartColor.getRed(), outlineStartColor.getGreen(), outlineStartColor.getBlue(), outlineStartColor.getAlpha()).endVertex();
            WORLD_RENDERER.pos(box.maxX, box.maxY, box.maxZ).color(outlineEndColor.getRed(), outlineEndColor.getGreen(), outlineEndColor.getBlue(), outlineEndColor.getAlpha()).endVertex();
            WORLD_RENDERER.pos(box.maxX, box.minY, box.maxZ).color(outlineStartColor.getRed(), outlineStartColor.getGreen(), outlineStartColor.getBlue(), outlineStartColor.getAlpha()).endVertex();
            WORLD_RENDERER.pos(box.maxX, box.minY, box.minZ).color(outlineEndColor.getRed(), outlineEndColor.getGreen(), outlineEndColor.getBlue(), outlineEndColor.getAlpha()).endVertex();
            TESSELLATOR.draw();
        }
    }

    private static void drawBlockSouth(AxisAlignedBB box, Color overlayStartColor, Color overlayEndColor, Color outlineStartColor, Color outlineEndColor, boolean overlay, boolean outline) {
        if (overlay) {
            WORLD_RENDERER.begin(7, DefaultVertexFormats.POSITION_COLOR);
            WORLD_RENDERER.pos(box.minX, box.maxY, box.maxZ).color(overlayStartColor.getRed(), overlayStartColor.getGreen(), overlayStartColor.getBlue(), overlayStartColor.getAlpha()).endVertex();
            WORLD_RENDERER.pos(box.minX, box.minY, box.maxZ).color(overlayEndColor.getRed(), overlayEndColor.getGreen(), overlayEndColor.getBlue(), overlayEndColor.getAlpha()).endVertex();
            WORLD_RENDERER.pos(box.maxX, box.minY, box.maxZ).color(overlayStartColor.getRed(), overlayStartColor.getGreen(), overlayStartColor.getBlue(), overlayStartColor.getAlpha()).endVertex();
            WORLD_RENDERER.pos(box.maxX, box.maxY, box.maxZ).color(overlayEndColor.getRed(), overlayEndColor.getGreen(), overlayEndColor.getBlue(), overlayEndColor.getAlpha()).endVertex();
            TESSELLATOR.draw();
        }
        if (outline) {
            WORLD_RENDERER.begin(2, DefaultVertexFormats.POSITION_COLOR);
            WORLD_RENDERER.pos(box.maxX, box.maxY, box.maxZ).color(outlineEndColor.getRed(), outlineEndColor.getGreen(), outlineEndColor.getBlue(), outlineEndColor.getAlpha()).endVertex();
            WORLD_RENDERER.pos(box.minX, box.maxY, box.maxZ).color(outlineStartColor.getRed(), outlineStartColor.getGreen(), outlineStartColor.getBlue(), outlineStartColor.getAlpha()).endVertex();
            WORLD_RENDERER.pos(box.minX, box.minY, box.maxZ).color(outlineEndColor.getRed(), outlineEndColor.getGreen(), outlineEndColor.getBlue(), outlineEndColor.getAlpha()).endVertex();
            WORLD_RENDERER.pos(box.maxX, box.minY, box.maxZ).color(outlineStartColor.getRed(), outlineStartColor.getGreen(), outlineStartColor.getBlue(), outlineStartColor.getAlpha()).endVertex();
            WORLD_RENDERER.pos(box.maxX, box.maxY, box.maxZ).color(outlineEndColor.getRed(), outlineEndColor.getGreen(), outlineEndColor.getBlue(), outlineEndColor.getAlpha()).endVertex();
            TESSELLATOR.draw();
        }
    }

    private static void drawBlockWest(AxisAlignedBB box, Color overlayStartColor, Color overlayEndColor, Color outlineStartColor, Color outlineEndColor, boolean overlay, boolean outline) {
        if (overlay) {
            WORLD_RENDERER.begin(7, DefaultVertexFormats.POSITION_COLOR);
            WORLD_RENDERER.pos(box.minX, box.maxY, box.maxZ).color(overlayStartColor.getRed(), overlayStartColor.getGreen(), overlayStartColor.getBlue(), overlayStartColor.getAlpha()).endVertex();
            WORLD_RENDERER.pos(box.minX, box.maxY, box.minZ).color(overlayEndColor.getRed(), overlayEndColor.getGreen(), overlayEndColor.getBlue(), overlayEndColor.getAlpha()).endVertex();
            WORLD_RENDERER.pos(box.minX, box.minY, box.minZ).color(overlayStartColor.getRed(), overlayStartColor.getGreen(), overlayStartColor.getBlue(), overlayStartColor.getAlpha()).endVertex();
            WORLD_RENDERER.pos(box.minX, box.minY, box.maxZ).color(overlayEndColor.getRed(), overlayEndColor.getGreen(), overlayEndColor.getBlue(), overlayEndColor.getAlpha()).endVertex();
            TESSELLATOR.draw();
        }
        if (outline) {
            WORLD_RENDERER.begin(2, DefaultVertexFormats.POSITION_COLOR);
            WORLD_RENDERER.pos(box.minX, box.maxY, box.maxZ).color(outlineStartColor.getRed(), outlineStartColor.getGreen(), outlineStartColor.getBlue(), outlineStartColor.getAlpha()).endVertex();
            WORLD_RENDERER.pos(box.minX, box.maxY, box.minZ).color(outlineEndColor.getRed(), outlineEndColor.getGreen(), outlineEndColor.getBlue(), outlineEndColor.getAlpha()).endVertex();
            WORLD_RENDERER.pos(box.minX, box.minY, box.minZ).color(outlineStartColor.getRed(), outlineStartColor.getGreen(), outlineStartColor.getBlue(), outlineStartColor.getAlpha()).endVertex();
            WORLD_RENDERER.pos(box.minX, box.minY, box.maxZ).color(outlineEndColor.getRed(), outlineEndColor.getGreen(), outlineEndColor.getBlue(), outlineEndColor.getAlpha()).endVertex();
            TESSELLATOR.draw();
        }
    }
}

