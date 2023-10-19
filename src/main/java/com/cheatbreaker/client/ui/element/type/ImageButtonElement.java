package com.cheatbreaker.client.ui.element.type;


import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.ui.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ImageButtonElement extends GuiButton {
    private boolean lIIIIlIIllIIlIIlIIIlIIllI = true;
    private ResourceLocation image;

    public ImageButtonElement(int n, ResourceLocation image, int n2, int n3, int n4, int n5, String string, boolean bl) {
        this(n, n2, n3, n4, n5, string);
        this.lIIIIlIIllIIlIIlIIIlIIllI = bl;
        this.image = image;
    }

    public ImageButtonElement(int n, int n2, int n3, int n4, int n5, String string) {
        super(n, n2, n3, n4, n5, string);
    }

    @Override
    public void drawButton(Minecraft mc, int n, int n2) {
        if (this.visible) {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            this.hovered = n >= this.xPosition && n2 >= this.yPosition && n < this.xPosition + this.width && n2 < this.yPosition + this.height;
            int n3 = this.getHoverState(this.hovered);
            if (this.lIIIIlIIllIIlIIlIIIlIIllI) {
                Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, this.hovered ? -15395563 : -14540254);
            }
            this.mouseDragged(mc, n, n2);
            int n4 = -3092272;
            if (!this.enabled) {
                n4 = -986896;
            } else if (this.hovered) {
                n4 = -1;
            }
            CheatBreaker.getInstance().playRegular16px.drawCenteredString(this.displayString, this.xPosition + this.width / 2F, this.yPosition + this.height / 2F - (this.lIIIIlIIllIIlIIlIIIlIIllI ? 6 : 5), n4);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0363636f * 0.2894737f);
            RenderUtil.renderIcon(this.image, 7.0F, (float) (this.xPosition + this.width - 20), (float) (this.yPosition + 5));
        }
    }
}
