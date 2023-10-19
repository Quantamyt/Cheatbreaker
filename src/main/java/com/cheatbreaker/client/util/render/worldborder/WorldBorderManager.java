package com.cheatbreaker.client.util.render.worldborder;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.event.impl.movement.CollisionEvent;
import com.cheatbreaker.client.event.impl.network.RenderWorldEvent;
import com.cheatbreaker.client.event.impl.tick.TickEvent;
import com.sun.javafx.geom.Vec2d;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class WorldBorderManager {
    private final Minecraft mc = Minecraft.getMinecraft();
    private final CheatBreaker cb = CheatBreaker.getInstance();
    private static final ResourceLocation forceFieldTexture = new ResourceLocation("textures/misc/forcefield.png");
    private final List<WorldBorder> borderList = new ArrayList<>();

    public WorldBorderManager() {
        CheatBreaker.getInstance().logger.info(CheatBreaker.getInstance().loggerPrefix + "Created Friend Manager");
        CheatBreaker.getInstance().getEventBus().addEvent(TickEvent.class, this::onTick);
        CheatBreaker.getInstance().getEventBus().addEvent(RenderWorldEvent.class, this::onWorldRender);
        CheatBreaker.getInstance().getEventBus().addEvent(CollisionEvent.class, this::onCollision);
    }

    private void onCollision(CollisionEvent event) {
        for (WorldBorder worldBorder : this.borderList) {
            if (worldBorder.doesCollide(event.getX(), event.getZ())) continue;
            event.getBoxes().add(new AxisAlignedBB(event.getX(), event.getY(), event.getZ(), event.getX() + 1.0, event.getY() + 1.0, event.getZ() + 1.0));
        }
    }

    private void onTick(TickEvent tickEvent) {
        this.borderList.forEach(WorldBorder::tick);
    }

    private void onWorldRender(RenderWorldEvent event) {
        if (!this.borderList.isEmpty()) {
            EntityPlayer entityClientPlayerMP = this.mc.thePlayer;
            float f = event.getPartialTicks();
            this.borderList.stream().filter(WorldBorder::worldEqualsWorld).forEach(worldBorder -> {
                Tessellator tessellator = Tessellator.getInstance();
                WorldRenderer worldrenderer = tessellator.getWorldRenderer();
                double d = this.mc.gameSettings.renderDistanceChunks * 16;
                if (entityClientPlayerMP.posX >= worldBorder.getStartingMaxX() - d ||
                        entityClientPlayerMP.posX <= worldBorder.getStartingMinX() + d ||
                        entityClientPlayerMP.posZ >= worldBorder.getStartingMaxZ() - d ||
                        entityClientPlayerMP.posZ <= worldBorder.getStartingMinZ() + d) {
                    float f2;
                    double d2;
                    double d3;
                    float f3;
                    double d4 = 1.0 - worldBorder.getClosestDistance(entityClientPlayerMP) / d;
                    d4 = Math.pow(d4, 4);
                    double d5 = entityClientPlayerMP.lastTickPosX + (entityClientPlayerMP.posX - entityClientPlayerMP.lastTickPosX) * (double) f;
                    double d6 = entityClientPlayerMP.lastTickPosY + (entityClientPlayerMP.posY - entityClientPlayerMP.lastTickPosY) * (double) f;
                    double d7 = entityClientPlayerMP.lastTickPosZ + (entityClientPlayerMP.posZ - entityClientPlayerMP.lastTickPosZ) * (double) f;
                    GL11.glEnable(3042);
                    GL11.glBlendFunc(770, 1);
                    this.mc.renderEngine.bindTexture(forceFieldTexture);
                    GL11.glDepthMask(false);
                    GL11.glPushMatrix();
                    boolean bl = true;
                    float f4 = (float) (WorldBorder.getColor(worldBorder).getRed() & 0xFF) / (float) 255;
                    float f5 = (float) (WorldBorder.getColor(worldBorder).getGreen() & 0xFF) / (float) 255;
                    float f6 = (float) (WorldBorder.getColor(worldBorder).getBlue() & 0xFF) / (float) 255;
                    GL11.glPolygonOffset(-3, -3);
                    GL11.glEnable(32823);
                    GL11.glAlphaFunc(516, 0.097260274f * 1.028169f);
                    GL11.glEnable(3008);
                    GL11.glDisable(2884);
                    float f7 = (float) (Minecraft.getSystemTime() % 3000L) / (float) 3000;
                    worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
                    GL11.glTranslated(-d5, -d6, -d7);
                    double d8 = Math.max(MathHelper.floor_double(d7 - d), worldBorder.getStartingMinZ());
                    double d9 = Math.min(MathHelper.ceiling_double_int(d7 + d), worldBorder.getStartingMaxZ());
                    if (d5 > worldBorder.getStartingMaxX() - d) {
                        f3 = 0.0f;
                        d3 = d8;
                        while (d3 < d9) {
                            d2 = Math.min(1.0, d9 - d3);
                            f2 = (float) d2 * (0.04054054f * 12.333334f);
                            worldrenderer.pos(worldBorder.getStartingMaxX(), 256, d3).tex(f7 + f3, f7 + 0.0f).color(f4, f5, f6, 1.0f).endVertex();
                            worldrenderer.pos(worldBorder.getStartingMaxX(), 256, d3 + d2).tex(f7 + f2 + f3, f7 + 0.0f).color(f4, f5, f6, 1.0f).endVertex();
                            worldrenderer.pos(worldBorder.getStartingMaxX(), 0.0, d3 + d2).tex(f7 + f2 + f3, f7 + (float) 128).color(f4, f5, f6, 1.0f).endVertex();
                            worldrenderer.pos(worldBorder.getStartingMaxX(), 0.0, d3).tex(f7 + f3, f7 + (float) 128).color(f4, f5, f6, 1.0f).endVertex();
                            d3 += 1.0;
                            f3 += 0.16463414f * 3.0370371f;
                        }
                    }
                    if (d5 < worldBorder.getStartingMinX() + d) {
                        f3 = 0.0f;
                        d3 = d8;
                        while (d3 < d9) {
                            d2 = Math.min(1.0, d9 - d3);
                            f2 = (float) d2 * (0.3611111f * 1.3846154f);
                            worldrenderer.pos(worldBorder.getStartingMinX(), 256, d3).tex(f7 + f3, f7 + 0.0f).color(f4, f5, f6, 1.0f).endVertex();
                            worldrenderer.pos(worldBorder.getStartingMinX(), 256, d3 + d2).tex(f7 + f2 + f3, f7 + 0.0f).color(f4, f5, f6, 1.0f).endVertex();
                            worldrenderer.pos(worldBorder.getStartingMinX(), 0.0, d3 + d2).tex(f7 + f2 + f3, f7 + (float) 128).color(f4, f5, f6, 1.0f).endVertex();
                            worldrenderer.pos(worldBorder.getStartingMinX(), 0.0, d3).tex(f7 + f3, f7 + (float) 128).color(f4, f5, f6, 1.0f).endVertex();
                            d3 += 1.0;
                            f3 += 1.25f * 0.4f;
                        }
                    }
                    d8 = Math.max(MathHelper.floor_double(d5 - d), worldBorder.getStartingMinX());
                    d9 = Math.min(MathHelper.ceiling_double_int(d5 + d), worldBorder.getStartingMaxX());
                    if (d7 > worldBorder.getStartingMaxZ() - d) {
                        f3 = 0.0f;
                        d3 = d8;
                        while (d3 < d9) {
                            d2 = Math.min(1.0, d9 - d3);
                            f2 = (float) d2 * (0.3115942f * 1.6046512f);
                            worldrenderer.pos(d3, 256, worldBorder.getStartingMaxZ()).tex(f7 + f3, f7 + 0.0f).color(f4, f5, f6, 1.0f).endVertex();
                            worldrenderer.pos(d3 + d2, 256, worldBorder.getStartingMaxZ()).tex(f7 + f2 + f3, f7 + 0.0f).color(f4, f5, f6, 1.0f).endVertex();
                            worldrenderer.pos(d3 + d2, 0.0, worldBorder.getStartingMaxZ()).tex(f7 + f2 + f3, f7 + (float) 128).color(f4, f5, f6, 1.0f).endVertex();
                            worldrenderer.pos(d3, 0.0, worldBorder.getStartingMaxZ()).tex(f7 + f3, f7 + (float) 128).color(f4, f5, f6, 1.0f).endVertex();
                            d3 += 1.0;
                            f3 += 1.5882353f * 0.31481484f;
                        }
                    }
                    if (d7 < worldBorder.getStartingMinZ() + d) {
                        f3 = 0.0f;
                        d3 = d8;
                        while (d3 < d9) {
                            d2 = Math.min(1.0, d9 - d3);
                            f2 = (float) d2 * (1.6071428f * 0.31111112f);
                            worldrenderer.pos(d3, 256, worldBorder.getStartingMinZ()).tex(f7 + f3, f7 + 0.0f).color(f4, f5, f6, 1.0f).endVertex();
                            worldrenderer.pos(d3 + d2, 256, worldBorder.getStartingMinZ()).tex(f7 + f2 + f3, f7 + 0.0f).color(f4, f5, f6, 1.0f).endVertex();
                            worldrenderer.pos(d3 + d2, 0.0, worldBorder.getStartingMinZ()).tex(f7 + f2 + f3, f7 + (float) 128).color(f4, f5, f6, 1.0f).endVertex();
                            worldrenderer.pos(d3, 0.0, worldBorder.getStartingMinZ()).tex(f7 + f3, f7 + (float) 128).color(f4, f5, f6, 1.0f).endVertex();
                            d3 += 1.0;
                            f3 += 2.2820513f * 0.21910112f;
                        }
                    }
                    tessellator.draw();
                    GL11.glTranslated(0.0, 0.0, 0.0);
                    GL11.glEnable(2884);
                    GL11.glDisable(3008);
                    GL11.glPolygonOffset(0.0f, 0.0f);
                    GL11.glDisable(32823);
                    GL11.glEnable(3008);
                    GL11.glDisable(3042);
                    GL11.glPopMatrix();
                    GL11.glDepthMask(true);
                }
            });
        }
    }

    public void createBorder(String id, String world, int color, double minX, double minZ, double maxX, double maxZ, boolean canShrinkExpand, boolean cancelExit) {
        this.borderList.add(new WorldBorder(this, id, world, color, minX, minZ, maxX, maxZ, canShrinkExpand, cancelExit));
    }

    public void updateBorder(String id, double minX, double minZ, double maxX, double maxZ, int durationTick) {
        this.borderList.stream().filter(worldBorder -> Objects.equals(WorldBorder.getId(worldBorder), id)
                && WorldBorder.canShrinkExpand(worldBorder)).findFirst().ifPresent(worldBorder -> {
            WorldBorder.setNewMin(worldBorder, new Vec2d(minX, minZ));
            WorldBorder.setNewMax(worldBorder, new Vec2d(maxX, maxZ));
            WorldBorder.setMoveTicks(worldBorder, 0);
            WorldBorder.setDurationTicks(worldBorder, durationTick);
        });
    }

    public void removeBorder(String string) {
        this.borderList.removeIf(worldBorder -> Objects.equals(WorldBorder.getId(worldBorder), string));
    }

    public void clearBorders() {
        this.borderList.clear();
    }

    public List<WorldBorder> getBorders() {
        return this.borderList;
    }

    static CheatBreaker getCheatBreaker(WorldBorderManager worldBorderManager) {
        return worldBorderManager.cb;
    }
}
