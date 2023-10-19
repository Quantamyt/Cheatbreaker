package com.cheatbreaker.client.ui.element.type;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.ui.mainmenu.AbstractElement;
import net.minecraft.client.gui.Gui;

public class FlatButtonElement extends AbstractElement {
    private String string;

    public FlatButtonElement(String string) {
        this.string = string;
    }

    @Override
    public void handleElementDraw(float f, float f2, boolean hovering) {
        this.drawButton(this.string, f, f2, hovering);
    }

    public void drawButton(String string, float f, float f2, boolean bl) {
        Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, bl && this.isMouseInside(f, f2) ? -16747106 : -13158601);
        CheatBreaker.getInstance().playRegular14px.drawCenteredString(string, this.xPosition + this.width / 2.0f, this.yPosition + this.height / 2.0f - (float)5, -1);
    }

    @Override
    public boolean handleElementMouseClicked(float f, float f2, int n, boolean bl) {
        if (!bl) {
            return false;
        }
        return false;
    }

    public String getString() {
        return this.string;
    }

    public void setText(String string) {
        this.string = string;
    }
}
