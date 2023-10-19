package com.cheatbreaker.client.ui.mainmenu.element;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.ui.fading.ColorFade;
import com.cheatbreaker.client.ui.mainmenu.AbstractElement;
import lombok.Getter;

@Getter
public class TextButtonElement extends AbstractElement {
    private final String string;
    private final ColorFade color;

    public TextButtonElement(String string) {
        this.string = string;
        this.color = new ColorFade(-1879048193, -1);
    }

    @Override
    protected void handleElementDraw(float f, float f2, boolean bl) {
        CheatBreaker.getInstance().robotoBold14px.drawString(this.string, this.xPosition + 6.0F, this.yPosition + (float) 6, this.color.getColor(this.isMouseInside(f, f2) && bl).getRGB());
    }
}
