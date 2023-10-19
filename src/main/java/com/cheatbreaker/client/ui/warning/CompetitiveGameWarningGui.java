package com.cheatbreaker.client.ui.warning;


import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.ui.AbstractGui;
import com.cheatbreaker.client.ui.fading.ColorFade;
import com.cheatbreaker.client.ui.mainmenu.GradientTextButton;
import com.cheatbreaker.client.ui.mainmenu.menus.VanillaMenu;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;

public class CompetitiveGameWarningGui extends AbstractGui {
    private final GuiScreen field_146298_h;
    private final GradientTextButton cancelButton;
    private final GradientTextButton continueButton;
    private final ColorFade warningTextFade;
    private boolean ended;

    public CompetitiveGameWarningGui(GuiScreen guiScreen) {
        this.field_146298_h = guiScreen;
        this.warningTextFade = new ColorFade(2000L, -1, -52429);
        this.cancelButton = new GradientTextButton("Cancel");
        this.continueButton = new GradientTextButton("I understand, continue");
    }

    @Override
    public void initGui() {
        this.blurGui();
        this.ended = true;
        float f = this.getScaledWidth() / 2.0f;
        float f2 = this.getScaledHeight() / 2.0f - (float)50;
        this.continueButton.setElementSize(f - (float)75, f2 + (float)50, (float)150, 12);
        this.cancelButton.setElementSize(f - (float)75, f2 + (float)64, (float)150, 12);
        this.cancelButton.buttonColor1();
    }

    @Override
    public void onGuiClosed() {
        this.mc.entityRenderer.stopUseShader();
    }

    @Override
    public void drawMenu(float x, float y) {
        if (this.ended && this.warningTextFade.isOver()) {
            this.ended = false;
        } else if (!this.ended && this.warningTextFade.isOver()) {
            this.ended = true;
        }
        this.renderBlur(this.getScaledWidth(), this.getScaledHeight());
        float centeredX = this.getScaledWidth() / 2.0f;
        float centeredY = this.getScaledHeight() / 2.0f - (float)50;
        CheatBreaker.getInstance().playBold18px.drawCenteredString("WARNING!", centeredX, centeredY, this.warningTextFade.getColor(this.ended).getRGB());
        CheatBreaker.getInstance().playRegular16px.drawCenteredString("Leaving competitive games may result in penalties.", centeredX, centeredY + (float)15, -1);
        CheatBreaker.getInstance().playRegular16px.drawCenteredString("You may be suspended from competitive games if you continue leaving games!", centeredX, centeredY + 25.0F, -1);
        this.cancelButton.drawElementHover(x, y, true);
        this.continueButton.drawElementHover(x, y, true);
    }

    @Override
    protected void mouseClicked(float f, float f2, int n) {
        if (this.cancelButton.isMouseInside(f, f2)) {
            this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
            this.mc.displayGuiScreen(this.field_146298_h);
        } else if (this.continueButton.isMouseInside(f, f2)) {
            this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
            this.mc.theWorld.sendQuittingDisconnectingPacket();
            this.mc.loadWorld(null);
            this.mc.displayGuiScreen(new VanillaMenu());
        }
    }

    @Override
    public void mouseMovedOrUp(float f, float f2, int n) {
    }
}
