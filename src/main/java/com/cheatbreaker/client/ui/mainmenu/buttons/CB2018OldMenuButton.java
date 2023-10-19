package com.cheatbreaker.client.ui.mainmenu.buttons;

import com.cheatbreaker.client.ui.fading.MinMaxFade;
import com.cheatbreaker.client.ui.mainmenu.AbstractElement;
import com.cheatbreaker.client.ui.fading.ColorFade;
import net.minecraft.client.gui.Gui;

public class CB2018OldMenuButton extends AbstractElement {
    private final ColorFade buttonColor = new ColorFade(0x1F000000, 0x3F000000);
    private final ColorFade IllIIIIIIIlIlIllllIIllIII = new ColorFade(0, -1073741825);
    private final MinMaxFade lIIIIllIIlIlIllIIIlIllIlI = new MinMaxFade(750L);
    private boolean IlllIllIlIIIIlIIlIIllIIIl;

    @Override
    protected void handleElementDraw(float f, float f2, boolean bl) {
        Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, this.buttonColor.getColor(this.isMouseInside(f, f2) && bl).getRGB());
    }

    @Override
    public boolean handleElementMouseClicked(float f, float f2, int n, boolean bl) {
        if (!bl) {
            return false;
        }
        return false;
    }
}
