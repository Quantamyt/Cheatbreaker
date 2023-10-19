package com.cheatbreaker.client.ui.mainmenu;

import com.cheatbreaker.client.ui.AbstractGui;
import com.cheatbreaker.client.ui.util.font.CBFontRenderer;
import com.cheatbreaker.client.ui.util.RenderUtil;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.shader.FrameBuffer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class LoadingScreen extends AbstractGui {//TODO: Finish mapping this class
    private final ResourceLocation logo = new ResourceLocation("client/logo_108.png");
    private final CBFontRenderer font = new CBFontRenderer(new ResourceLocation("client/font/Ubuntu-M.ttf"), 14.0f);

    private final FrameBuffer framebuffer;

    private String currentPhaseString;

    public int totalPhases;
    @Getter private int currentPhase;

    /**
     * Initial setup.
     */
    public LoadingScreen(int phases) {
        this.totalPhases = phases;
        this.mc = Minecraft.getMinecraft();
        this.scaledResolution = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        this.framebuffer = new FrameBuffer(this.scaledResolution.getScaledWidth() * this.scaledResolution.getScaleFactor(), this.scaledResolution.getScaledHeight() * this.scaledResolution.getScaleFactor(), true);
    }

    /**
     * Updates the text and percentage bar.
     */
    public void updatePhase(String phase) {
        this.currentPhaseString = phase;
        this.addPhase();
    }

    /**
     * Updates the percentage bar.
     */
    public void addPhase() {
        ++this.currentPhase;
        if (this.totalPhases <= this.currentPhase) {
            this.totalPhases = this.currentPhase + 1;
        }
        this.drawMenu(0.0f, 0.0f);
    }

    /**
     * Draws the logo.
     */
    private void drawLogo(float resWidth, float resHeight) {
        float size = 27.0f;
        float x = resWidth / 2.0f - size;
        float y = resHeight / 2.0f - size;
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderUtil.renderEIcon(this.logo, size, x, y);
    }

    /**
     * Sets the frame buffer.
     */
    private void setFrameBuffer() {
        try {
            this.framebuffer.bindFramebuffer(false);
            GL11.glMatrixMode(5889);
            GL11.glLoadIdentity();
            GL11.glOrtho(0.0, this.scaledResolution.getScaledWidth(), this.scaledResolution.getScaledHeight(), 0.0, 1000.0, 3000.0);
            GL11.glMatrixMode(5888);
            GL11.glLoadIdentity();
            GL11.glTranslatef(0.0f, 0.0f, -2000.0f);
            GL11.glDisable(GL11.GL_LIGHTING);
            GL11.glDisable(GL11.GL_FOG);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_ALPHA_TEST);
        } catch (RuntimeException ignored) {}
    }

    /**
     * Initializes the frame buffer renderer.
     */
    private void initFrameBufferRenderer() {
        int scaleFactor = this.scaledResolution.getScaleFactor();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_FOG);
        this.framebuffer.unbindFramebuffer();
        this.framebuffer.framebufferRender(this.scaledResolution.getScaledWidth() * scaleFactor, this.scaledResolution.getScaledHeight() * scaleFactor);
        GL11.glAlphaFunc(516, 0.1f);
        GL11.glFlush();
    }

    /**
     * Draws the menu.
     */
    @Override
    public void drawMenu(float x, float y) {
        this.setFrameBuffer();

        float scale = this.getScaleFactor();
        float resolutionWidth = this.scaledResolution.getScaledWidth() / scale;
        float resolutionHeight = this.scaledResolution.getScaledHeight() / scale;

        GL11.glScaled(scale, scale, scale);
        Gui.drawRect(0.0f, 0.0f, resolutionWidth, resolutionHeight, -1);

        this.drawLogo(resolutionWidth, resolutionHeight);

        float barWidth = 160.0f;
        float barX = resolutionWidth / 2.0f - 80.0f;
        float barY = resolutionHeight - 40.0f;
        RenderUtil.drawRoundedRect(barX, barY, barX + barWidth, barY + 10.0f, 8.0, -657931);

        float phaseBar = barWidth * ((float) this.currentPhase / (float) this.totalPhases);
        if (this.currentPhaseString != null) this.font.drawCenteredString(this.currentPhaseString.toLowerCase(), resolutionWidth / 2.0f, barY - 11.0f, -3092272);

        RenderUtil.drawRoundedRect(barX, barY, barX + Math.max(phaseBar, 8.0F), barY + 10.0f, 8.0, -2473389);
        this.initFrameBufferRenderer();
        this.mc.func_147120_f();
    }

    @Override
    protected void mouseClicked(float var1, float var2, int var3) {
    }

    @Override
    public void mouseMovedOrUp(float var1, float var2, int var3) {
    }
}
