package com.cheatbreaker.client.ui.mainmenu;

import com.cheatbreaker.client.CheatBreaker;
import lombok.Getter;
import net.minecraft.client.Minecraft;

public abstract class AbstractElement {
    @Getter protected float xPosition;
    @Getter protected float yPosition;
    @Getter protected float width;
    @Getter protected float height;
    protected final Minecraft mc = Minecraft.getMinecraft();
    protected final CheatBreaker cb = CheatBreaker.getInstance();

    public boolean isMouseInside(float f, float f2) {
        return f > this.xPosition && f < this.xPosition + this.width && f2 > this.yPosition && f2 < this.yPosition + this.height;
    }

    public boolean drawElementHover(float f, float f2, boolean bl) {
        this.handleElementDraw(f, f2, bl);
        return this.isMouseInside(f, f2);
    }

    public void setElementSize(float x, float y, float width, float height) {
        this.xPosition = x;
        this.yPosition = y;
        this.width = width;
        this.height = height;
    }

    public void handleElementUpdate() {
    }

    public void handleElementClose() {
    }

    public void keyTyped(char c, int n) {
    }

    public void handleElementMouse() {
    }

    protected abstract void handleElementDraw(float var1, float var2, boolean var3);

    public boolean handleElementMouseClicked(float f, float f2, int n, boolean bl) {
        return false;
    }

    public boolean onMouseMoved(float f, float f2, int n, boolean bl) {
        return false;
    }

    public boolean onMouseClick(float f, float f2, int n) {
        return false;
    }
}
