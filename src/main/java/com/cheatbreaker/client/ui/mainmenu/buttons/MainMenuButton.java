package com.cheatbreaker.client.ui.mainmenu.buttons;

import com.cheatbreaker.client.CheatBreaker;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.Minecraft;
import org.lwjgl.opengl.GL11;

public class MainMenuButton extends GuiButton {
    private boolean lIIIIlIIllIIlIIlIIIlIIllI = true;

    public MainMenuButton(int n, int n2, int n3, int n4, int n5, String string, boolean bl) {
        this(n, n2, n3, n4, n5, string);
        this.lIIIIlIIllIIlIIlIIIlIIllI = bl;
    }

    public MainMenuButton(int n, int n2, int n3, int n4, int n5, String string) {
        super(n, n2, n3, n4, n5, string);
    }

    @Override
    public void drawButton(Minecraft mc, int n, int n2) {
        if (this.field_146125_m) {
            FontRenderer fontRenderer = mc.fontRenderer;
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.field_146123_n = n >= this.field_146128_h && n2 >= this.field_146129_i && n <
                    this.field_146128_h + this.field_146120_f && n2 < this.field_146129_i + this.field_146121_g;
            int n3 = this.getHoverState(this.field_146123_n);
            if (this.lIIIIlIIllIIlIIlIIIlIIllI) {
                Gui.drawRect(this.field_146128_h, this.field_146129_i,
                        this.field_146128_h + this.field_146120_f, this.field_146129_i +
                                this.field_146121_g, this.field_146123_n ? -15395563 : -14540254);
            }
            this.mouseDragged(mc, n, n2);
            int n4 = -3092272;
            if (!this.enabled) {
                n4 = -986896;
            } else if (this.field_146123_n) {
                n4 = -1;
            }
            CheatBreaker.getInstance().playRegular16px.drawCenteredString(this.displayString, this.field_146128_h +
                    this.field_146120_f / 2, this.field_146129_i + this.field_146121_g / 2 - (this.lIIIIlIIllIIlIIlIIIlIIllI ? 5 : 4), n4);
        }
    }
}
