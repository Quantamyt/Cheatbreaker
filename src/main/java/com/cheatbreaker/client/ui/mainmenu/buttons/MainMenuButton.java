package com.cheatbreaker.client.ui.mainmenu.buttons;

import com.cheatbreaker.client.CheatBreaker;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class MainMenuButton extends GuiButton {
    private boolean drawButton = true;

    public MainMenuButton(int n, int n2, int n3, int n4, int n5, String string, boolean bl) {
        this(n, n2, n3, n4, n5, string);
        this.drawButton = bl;
    }

    public MainMenuButton(int n, int n2, int n3, int n4, int n5, String string) {
        super(n, n2, n3, n4, n5, string);
    }

    @Override
    public void drawButton(Minecraft mc, int n, int n2) {
        if (this.visible) {
            FontRenderer fontRenderer = mc.fontRendererObj;
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.hovered = n >= this.xPosition && n2 >= this.yPosition && n <
                    this.xPosition + this.width && n2 < this.yPosition + this.height;
            int n3 = this.getHoverState(this.hovered);
            if (this.drawButton) {
                Gui.drawRect(this.xPosition, this.yPosition,
                        this.xPosition + this.width, this.yPosition +
                                this.height, this.hovered ? -15395563 : -14540254);
            }
            this.mouseDragged(mc, n, n2);
            int n4 = -3092272;
            if (!this.enabled) {
                n4 = -986896;
            } else if (this.hovered) {
                n4 = -1;
            }
            CheatBreaker.getInstance().playRegular16px.drawCenteredString(this.displayString, this.xPosition +
                    this.width / 2F, this.yPosition + this.height / 2F - (this.drawButton ? 5 : 4), n4);
        }
    }
}
