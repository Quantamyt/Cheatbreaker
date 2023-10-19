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
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class ChangelogDetailMenu extends MainMenuBase {
    private final String title;
    private final String author;
    private final String description;

    private final GradientTextButton backButton;
    private final ScrollableElement scrollableElement;

    public ChangelogDetailMenu(String title, String author, String description) {
        this.title = title;
        this.author = author;
        this.description = description;

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
        Gui.drawRect(leftPos + 8.0F, topPos + 22F, rightPos - 8.0F, topPos + 22.5F, 0x1AFFFFFF);

        CheatBreaker.getInstance().playRegular16px.drawString(this.title, leftPos + 8.0f, topPos + 5.0f, -1);
        CheatBreaker.getInstance().playRegular14px.drawString(EnumChatFormatting.ITALIC + "Posted by " + EnumChatFormatting.BOLD + this.author, leftPos + 8.0f, topPos + 13.0f, -1);
        String wrapped = WordWrap.from(this.description).maxWidth(60).insertHyphens(false).wrap();
        String[] lines = wrapped.split("\n");

        int index = 0;
        this.backButton.setElementSize(this.getScaledWidth() / 2.0f - 30.0f, this.getScaledHeight() - 35.0f, 60.0f, 12);
        this.backButton.drawElementHover(x, y, true);
        this.scrollableElement.drawScrollable(x, y, true);
        GL11.glPushMatrix();
        GL11.glEnable(3089);

        RenderUtil.startScissorBox((int) leftPos, (int) topPos + 18, (int) rightPos, (int) bottomPos - 8, (float) ((int) ((float) this.getScaledResolution().getScaleFactor() * this.getScaleFactor())), (int) this.getScaledHeight());
        for (String line : lines) {
            CheatBreaker.getInstance().playRegular14px.drawString(line, leftPos + 8.0f, topPos + 24.0f + (index * (CheatBreaker.getInstance().playRegular14px.getFont().getSize() / 2.0f)), -1);
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
            this.mc.displayGuiScreen(this.mc.lastScreen);
        } else if (this.scrollableElement.isMouseInside(f, f2)) {
            this.scrollableElement.handleElementMouseClicked(f, f2, n, true);
        }
    }
}