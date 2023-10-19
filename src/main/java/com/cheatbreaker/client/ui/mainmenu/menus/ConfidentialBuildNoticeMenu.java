package com.cheatbreaker.client.ui.mainmenu.menus;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.ui.element.type.ScrollableElement;
import com.cheatbreaker.client.ui.mainmenu.GradientTextButton;
import com.cheatbreaker.client.ui.mainmenu.MainMenuBase;
import com.cheatbreaker.client.ui.util.RenderUtil;
import com.cheatbreaker.client.util.render.wordwrap.WordWrap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ConfidentialBuildNoticeMenu extends MainMenuBase {
    private final GradientTextButton backButton;
    private final ScrollableElement scrollableElement;
    private final boolean skipFirstLine = false;

    public ConfidentialBuildNoticeMenu() {
        this.backButton = new GradientTextButton("BACK");
        this.scrollableElement = new ScrollableElement(null);
    }

    @Override
    public void initGui() {
        super.initGui();
        float f = Math.min(240.0f, this.getScaledWidth() - 10.0f);
        float f3 = this.getScaledWidth() / 2.0f + f / 2.0f;
        float f4 = 40;
        float f5 = this.getScaledHeight() - 40.0f;
        this.scrollableElement.setElementSize(f3 - 8.0f, f4 + 18.0f, 4.0f, f5 - f4 - 28.0f);
    }

    @Override
    public void handleMouseInput() {
        super.handleMouseInput();
        this.scrollableElement.handleElementMouse();
    }

    @Override
    public void drawMenu(float x, float y) {
        super.drawMenu(x, y);
        float f3 = Math.min(240.0f, this.getScaledWidth() - 10.0f);
        float leftPos = this.getScaledWidth() / 2.0f - f3 / 2.0f;
        float rightPos = this.getScaledWidth() / 2.0f + f3 / 2.0f;
        float topPos = 40;
        float bottomPos = this.getScaledHeight() - 40.0f;
        Gui.drawRect(leftPos, topPos, rightPos, bottomPos, 0x2F000000);
        Gui.drawRect(leftPos + 8.0f, topPos + 16.0f, rightPos - 8.0f, topPos + 0.15789473f * 104.50001f, 0x1AFFFFFF);
        String title = "About confidential builds";
        CheatBreaker.getInstance().playRegular16px.drawString(title.toUpperCase(), leftPos + 8.0f, topPos + 5.0f, -1);
        String description = "PLEASE READ THIS SECTION CAREFULLY." +
                "\n\n" +
                "You are currently using a private CheatBreaker build. Private builds have certain restrictions in place which are described below:" +
                "\n\n" +
                "Branch " + CheatBreaker.getInstance().getGitBranch() + " restrictions:" +
                "\n- Disclosure to any user below the lowest authorized ranking is prohibited. This includes but is not limited to new changes inside the build such as new features, fixes, and improvements." +
                "\n- Recording/streaming on this build is not recommended, but allowed to a certain extent. If any capturing software is showing this build, do not show any changes from this build that differ from the master branch. If, however, you are recording to submit a bug report, these restrictions do not apply." +
                "\n\n" +
                "Management reserves the rights to restrict your access to future builds if you violate any restrictions applied.";
        String wrapped = WordWrap.from(description).maxWidth(60).insertHyphens(false).wrap();
        String[] lines = wrapped.split("\n");
        int index = 0;
        int currentLine = 0;
        this.backButton.setElementSize(this.getScaledWidth() / 2.0f - 30.0f, this.getScaledHeight() - 35.0f, 60.0f, 12);
        this.backButton.drawElementHover(x, y, true);
        this.scrollableElement.drawScrollable(x, y, true);
        GL11.glPushMatrix();
        GL11.glEnable(3089);
        RenderUtil.startScissorBox((int)leftPos, (int)topPos + 18, (int)rightPos, (int)bottomPos - 8, (float)((int)((float)this.getScaledResolution().getScaleFactor() * this.getScaleFactor())), (int)this.getScaledHeight());
        for (String line : lines) {
            if (this.skipFirstLine && currentLine == 0) {
                currentLine++;
                continue;
            }
            CheatBreaker.getInstance().playRegular14px.drawString(line, leftPos + 8.0f, topPos + 18.0f + (index * (CheatBreaker.getInstance().playRegular14px.getFont().getSize() / 2.0f)), -1);
            index++;
        }
        this.scrollableElement.setScrollAmount(index * (CheatBreaker.getInstance().playRegular14px.getFont().getSize() / 2.0f) + 2.0f);
        GL11.glDisable(3089);
        GL11.glPopMatrix();
        this.scrollableElement.drawElementHover(x, y, true);
    }

    @Override
    public void mouseClicked(float f, float f2, int n) {
        super.mouseClicked(f, f2, n);
        if (this.backButton.isMouseInside(f, f2)) {
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
            this.mc.displayGuiScreen(new MainMenu());
        } else if (this.scrollableElement.isMouseInside(f, f2)) {
            this.scrollableElement.handleElementMouseClicked(f, f2, n, true);
        }
    }
}
