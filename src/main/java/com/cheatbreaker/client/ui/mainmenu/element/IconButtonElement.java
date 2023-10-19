package com.cheatbreaker.client.ui.mainmenu.element;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.ui.fading.ColorFade;
import com.cheatbreaker.client.ui.mainmenu.AbstractElement;
import com.cheatbreaker.client.ui.util.RenderUtil;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class IconButtonElement extends AbstractElement {
    private ResourceLocation resourceLocation;
    private String string;
    private boolean isString;
    private final ColorFade outline;
    private final ColorFade topGradient;
    private final ColorFade bottomGradient;
    private float iconSize = 4;

    public IconButtonElement(ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
        this.iconSize = 4;
        this.outline = new ColorFade(0x4FFFFFFF, -1353670564);
        this.topGradient = new ColorFade(444958085, 1063565678);
        this.bottomGradient = new ColorFade(444958085, 1062577506);
    }

    public IconButtonElement(float iconSize, ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
        this.iconSize = iconSize;
        this.outline = new ColorFade(0x4FFFFFFF, -1353670564);
        this.topGradient = new ColorFade(444958085, 1063565678);
        this.bottomGradient = new ColorFade(444958085, 1062577506);
    }

    public IconButtonElement(String string) {
        this.string = string;
        this.isString = true;
        this.outline = new ColorFade(0x4FFFFFFF, -1353670564);
        this.topGradient = new ColorFade(444958085, 1063565678);
        this.bottomGradient = new ColorFade(444958085, 1062577506);
    }

    @Override
    protected void handleElementDraw(float mouseX, float mouseY, boolean bl) {
        boolean hovering = bl && this.isMouseInside(mouseX, mouseY);
        RenderUtil.drawGradientRectWithOutline(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, this.outline.getColor(hovering).getRGB(), this.topGradient.getColor(hovering).getRGB(), this.bottomGradient.getColor(hovering).getRGB());
        if (this.isString) {
            CheatBreaker.getInstance().robotoRegular13px.drawCenteredString(this.string, this.xPosition + this.width / 2.0f, this.yPosition + 2.0f, -1);
        } else {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.4444444f * 0.5538462f);
            RenderUtil.renderIcon(this.resourceLocation, this.iconSize, this.xPosition + this.width / 2.0f - this.iconSize, this.yPosition + this.height / 2.0f - this.iconSize);
        }
    }

    public float getLongerTextWidth() {
        return 22 + CheatBreaker.getInstance().robotoRegular13px.getStringWidth(this.string) + 6;
    }

    @Override
    public boolean handleElementMouseClicked(float f, float f2, int n, boolean bl) {
        if (!bl) {
            return false;
        }
        return false;
    }

    public void setResourceLocation(ResourceLocation resourceLocation) {
        this.resourceLocation = resourceLocation;
    }

    public void setString(String string) {
        this.string = string;
    }
}
