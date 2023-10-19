package com.cheatbreaker.client.ui.mainmenu.buttons;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.ui.fading.ColorFade;
import com.cheatbreaker.client.ui.mainmenu.AbstractElement;
import com.cheatbreaker.client.ui.mainmenu.menus.ChangelogDetailMenu;
import com.cheatbreaker.client.util.lang.WordWrap;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class ChangeLogButton extends AbstractElement {
    private final ColorFade backgroundFade = new ColorFade(0x1F000000, 0x3F000000);
    private final String title;
    private final String author;
    private final String description;

    public ChangeLogButton(String title, String author, String description) {
        this.title = title;
        this.author = author;
        this.description = description;
    }

    @Override
    protected void handleElementDraw(float f, float f2, boolean bl) {
        Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, this.backgroundFade.getColor(this.isMouseInside(f, f2) && bl).getRGB());
        String wrapped = WordWrap.from(description).maxWidth(60).insertHyphens(false).wrap();
        String[] lines = wrapped.split("\n");

        int index = 2;
        float yOffset = CheatBreaker.getInstance().playRegular14px.getFont().getSize() / 2.0f;

        CheatBreaker.getInstance().playRegular14px.drawString(EnumChatFormatting.BOLD + this.title, 1.0f + xPosition, yPosition, -1);
        CheatBreaker.getInstance().playRegular12px.drawString(EnumChatFormatting.ITALIC + "Posted by " + EnumChatFormatting.BOLD + this.author, 1.0f + xPosition, yPosition + yOffset, -1);

        for (String line : lines) {
            if (index < 3) {
                if (index == 2) line += "...";
                CheatBreaker.getInstance().playRegular14px.drawString(line, 1.0f + xPosition, yPosition + (index * yOffset) - 1, -1);
            }
            index++;
        }
    }

    @Override
    public boolean handleElementMouseClicked(float f, float f2, int n, boolean bl) {
        if (this.isMouseInside(f, f2)) {
            this.mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0f));
            this.mc.displayGuiScreen(new ChangelogDetailMenu(this.title, this.author, this.description));
        }
        return false;
    }
}
