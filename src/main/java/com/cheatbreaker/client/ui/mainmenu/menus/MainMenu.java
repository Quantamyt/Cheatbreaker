package com.cheatbreaker.client.ui.mainmenu.menus;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.ui.fading.CosineFade;
import com.cheatbreaker.client.ui.fading.MinMaxFade;
import com.cheatbreaker.client.ui.mainmenu.GradientTextButton;
import com.cheatbreaker.client.ui.mainmenu.MainMenuBase;
import com.cheatbreaker.client.ui.util.RenderUtil;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiSelectWorld;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;

public class MainMenu extends MainMenuBase {
    private final ResourceLocation outerLogo = new ResourceLocation("client/logo_255_outer.png");
    private final ResourceLocation innerLogo = new ResourceLocation("client/logo_108_inner.png");
    private final GradientTextButton singleplayerButton = new GradientTextButton("SINGLEPLAYER");
    private final GradientTextButton multiplayerButton = new GradientTextButton("MULTIPLAYER");
    private final MinMaxFade logoMoveUpTime = new MinMaxFade(750L);
    private final CosineFade outerLogoRotationTime = new CosineFade(4000L);
    private final MinMaxFade backgroundFadeOutTime = new MinMaxFade(400L);
    public static int menuCounter;

    @Override
    public void handleMouseInput() throws IOException {
        super.handleMouseInput();
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        if (this.isFromLoadingScreen() && !this.logoMoveUpTime.isTimeNotAtZero()) {
            this.logoMoveUpTime.startAnimation();
        }
        if (!(this.isFromLoadingScreen() && !this.logoMoveUpTime.isOver() || this.outerLogoRotationTime.isTimeNotAtZero())) {
            this.backgroundFadeOutTime.startAnimation();
            this.outerLogoRotationTime.startAnimation();
            this.outerLogoRotationTime.loopAnimation();
        }
    }

    @Override
    public void initGui() {
        super.initGui();
        this.singleplayerButton.setElementSize(this.getScaledWidth() / 2.0f - (float) 50, this.getScaledHeight() / 2.0f + 5.0F, 100.0F, 12);
        this.multiplayerButton.setElementSize(this.getScaledWidth() / 2.0f - 50.0F, this.getScaledHeight() / 2.0f + 24.0F, 100.0F, 12);
        ++menuCounter;
    }

    @Override
    public void drawMenu(float x, float y) {
        super.drawMenu(x, y);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.singleplayerButton.drawElementHover(x, y, true);
        this.multiplayerButton.drawElementHover(x, y, true);
        Gui.drawRect(this.singleplayerButton.getXPosition() - (float) 20, this.getScaledHeight() / 2.0f - (float) 80, this.singleplayerButton.getXPosition() + this.singleplayerButton.getWidth() + (float) 20, this.multiplayerButton.getYPosition() + this.multiplayerButton.getHeight() + (float) 14, 0x2F000000);
        float f3 = this.isFromLoadingScreen() ? this.logoMoveUpTime.getFadeAmount() : 1.0f;
        if (this.isFromLoadingScreen()) {
            Gui.drawRect(0.0f, 0.0f, this.getScaledWidth(), this.getScaledHeight(), new Color(1.0f, 1.0f, 1.0f, 1.0f - this.backgroundFadeOutTime.getFadeAmount()).getRGB());
        }
        this.drawLogo(this.getScaledWidth(), this.getScaledHeight(), f3);
        float f5 = this.getScaledWidth() / 2.0f - 80.0F;
        float f6 = this.getScaledHeight() - 40.0F;
        CheatBreaker.getInstance().ubuntuMedium14px.drawCenteredString("finished", this.getScaledWidth() / 2.0f, f6 - 11.0f, new Color(208, 208, 208, (int) ((float) 255 * (1.0f - Math.min(f3, 0.984f)))).getRGB());
        RenderUtil.drawRoundedRect(f5, f6, f5 + (float) 160, f6 + (float) 10, 8, new Color(218, 66, 83, (int) ((float) 255 * (1.0f - f3))).getRGB());
    }

    private void drawLogo(double x, double y, float yPosMover) {
        float size = 27;
        double x2 = x / 2.0 - (double) size;
        double y2 = y / 2.0 - (double) size - (double) (35.0F * yPosMover);

        GL11.glPushMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glTranslatef((float) x2, (float) y2, 1.0f);
        GL11.glTranslatef(size, size, size);
        GL11.glRotatef(180.0F * this.outerLogoRotationTime.getFadeAmount(), 0.0f, 0.0f, 1.0f);
        GL11.glTranslatef(-size, -size, -size);
        RenderUtil.renderEIcon(this.outerLogo, size, 0.0f, 0.0f);
        GL11.glPopMatrix();
        RenderUtil.renderEIcon(this.innerLogo, size, (float) x2, (float) y2);
    }

    @Override
    protected void mouseClicked(float f, float f2, int n) {
        super.mouseClicked(f, f2, n);
        this.singleplayerButton.handleElementMouseClicked(f, f2, n, true);
        this.multiplayerButton.handleElementMouseClicked(f, f2, n, true);
        if (this.singleplayerButton.isMouseInside(f, f2)) {
            this.mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0f));
            this.mc.displayGuiScreen(new GuiSelectWorld(this));
        } else if (this.multiplayerButton.isMouseInside(f, f2)) {
            this.mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0f));
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        }
    }

    public boolean isFromLoadingScreen() {
        return menuCounter == 1;
    }

    public MinMaxFade getBackgroundFadeOutTime() {
        return this.backgroundFadeOutTime;
    }
}
