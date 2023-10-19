package com.cheatbreaker.client.ui.mainmenu.element;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.ui.fading.ColorFade;
import com.cheatbreaker.client.ui.mainmenu.AbstractElement;
import com.cheatbreaker.client.util.render.wordwrap.WordWrap;

public class TextWithShadowButton extends AbstractElement {
    private final String string;
    private final ColorFade color;
    private boolean multipleLines = false;
    private int maxStringWidth;

    public TextWithShadowButton(String string) {
        this.string = string;
        this.color = new ColorFade(-1879048193, 0xCFEEEEEE);
    }

    public TextWithShadowButton(String string, int maxStringWidth) {
        this(string);
        this.multipleLines = true;
        this.maxStringWidth = maxStringWidth;
    }

    @Override
    protected void handleElementDraw(float f, float f2, boolean bl) {
        if (this.multipleLines) {
            String wrapped = WordWrap.from(this.string).maxWidth(50).insertHyphens(false).wrap();
            String[] lines = wrapped.split("\n");
            int index = 0;
            for(String line : lines) {
                CheatBreaker.getInstance().playRegular14px.drawStringWithShadow(line, this.xPosition, this.yPosition - CheatBreaker.getInstance().playRegular14px.getFont().getSize() / 2.0f + (index * CheatBreaker.getInstance().playRegular14px.getFont().getSize() / 2.0f), this.color.getColor(this.isMouseInside(f, f2) && bl).getRGB());
                index++;
            }
        } else {
            CheatBreaker.getInstance().playRegular18px.drawStringWithShadow(this.string, this.xPosition, this.yPosition, this.color.getColor(this.isMouseInside(f, f2) && bl).getRGB());
        }

    }
}
