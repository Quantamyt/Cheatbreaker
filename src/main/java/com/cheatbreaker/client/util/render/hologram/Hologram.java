package com.cheatbreaker.client.util.render.hologram;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderManager;
import org.lwjgl.opengl.GL11;

public class Hologram {
    private final UUID uuid;
    private String[] lines;
    private final double xPos;
    private final double yPos;
    private final double zPos;
    private static final List<Hologram> holograms = new ArrayList<>();

    public Hologram(UUID uUID, double xPos, double yPos, double zPos) {
        this.uuid = uUID;
        this.xPos = xPos;
        this.yPos = yPos;
        this.zPos = zPos;
    }

    public static void renderHologram() {
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        RenderManager renderManager = RenderManager.instance;
        for (Hologram hologram : holograms) {
            if (hologram.getLines() == null || hologram.getLines().length <= 0) continue;
            for (int i = hologram.getLines().length - 1; i >= 0; --i) {
                String line = hologram.getLines()[hologram.getLines().length - i - 1];
                float xPos = (float)(hologram.getXPos() - (double)((float)RenderManager.renderPosX));
                float yPos = (float)(hologram.getYPos() + 1.0 + (double)((float)i * (0.16049382f * 1.5576924f)) -
                        (double)((float)RenderManager.renderPosY));
                float zPos = (float)(hologram.getZPos() - (double)((float)RenderManager.renderPosZ));
                float scale = 1.6f;
                float scaled = 0.016666668f * scale;
                GL11.glPushMatrix();
                GL11.glTranslatef(xPos, yPos, zPos);
                GL11.glNormal3f(0.0f, 1.0f, 0.0f);
                GL11.glRotatef(-renderManager.playerViewY, 0.0f, 1.0f, 0.0f);
                GL11.glRotatef(renderManager.playerViewX, 1.0f, 0.0f, 0.0f);
                GL11.glScalef(-scaled, -scaled, scaled);
                GL11.glDisable(2896);
                GL11.glDepthMask(false);
                GL11.glDisable(2929);
                GL11.glEnable(3042);
                OpenGlHelper.glBlendFunc(770, 771, 1, 0);
                Tessellator tessellator = Tessellator.instance;
                int renderYPos = 0;
                GL11.glDisable(3553);
                tessellator.startDrawingQuads();
                int renderXPos = fontRenderer.getStringWidth(line) / 2;
                tessellator.setColorRGBA_F(0.0f, 0.0f, 0.0f, 0.6875f * 0.36363637f);
                tessellator.addVertex(-renderXPos - 1, -1 + renderYPos, 0.0);
                tessellator.addVertex(-renderXPos - 1, 8 + renderYPos, 0.0);
                tessellator.addVertex(renderXPos + 1, 8 + renderYPos, 0.0);
                tessellator.addVertex(renderXPos + 1, -1 + renderYPos, 0.0);
                tessellator.draw();
                GL11.glEnable(3553);
                fontRenderer.drawString(line, -fontRenderer.getStringWidth(line) / 2, renderYPos, 0x20FFFFFF);
                GL11.glEnable(2929);
                GL11.glDepthMask(true);
                fontRenderer.drawString(line, -fontRenderer.getStringWidth(line) / 2, renderYPos, -1);
                GL11.glEnable(2896);
                GL11.glDisable(3042);
                GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
                GL11.glPopMatrix();
            }
        }
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public String[] getLines() {
        return this.lines;
    }

    public void setLines(String[] lines) {
        this.lines = lines;
    }

    public double getXPos() {
        return this.xPos;
    }

    public double getYPos() {
        return this.yPos;
    }

    public double getZPos() {
        return this.zPos;
    }

    public static List<Hologram> getHolograms() {
        return holograms;
    }
}
