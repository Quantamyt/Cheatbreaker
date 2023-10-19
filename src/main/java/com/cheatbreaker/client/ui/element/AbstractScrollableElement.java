package com.cheatbreaker.client.ui.element;

import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.ui.util.RenderUtil;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public abstract class AbstractScrollableElement extends AbstractModulesGuiElement {
    protected double startPosition = 0.0;
    public int scrollAmount = 0;
    public int scrollHeight = 0;
    public int x2;
    protected int y2;
    public boolean bottom = false;
    public boolean hovering = false;
    private boolean interacting = false;
    private float scrollPosition;

    public AbstractScrollableElement(float scale, int x, int y, int width, int height) {
        super(scale);
        this.x2 = x;
        this.y2 = y;
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void handleDrawElement(int mouseX, int n2, float partialTicks) {
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY, int button) {
        double d = this.height - 10;
        double d2 = this.scrollHeight;
        double d3 = d / d2 * (double)100;
        double d4 = d / (double)100 * d3;
        double d5 = (double)this.scrollAmount / 100.0 * d3;
        boolean bl4 = (float)mouseX > (float)(this.x + this.width - 9) * this.scale && (float)mouseX < (float)(this.x + this.width - 3) * this.scale && (double)mouseY > ((double)(this.y + 11) - d5) * (double)this.scale && (double)mouseY < ((double)(this.y + 8) + d4 - d5) * (double)this.scale;
        boolean bl3 = (float)mouseX > (float)(this.x + this.width - 9) * this.scale && (float)mouseX < (float)(this.x + this.width - 3) * this.scale && (float)mouseY > (float)(this.y + 11) * this.scale && (double)mouseY < ((double)(this.y + 6) + d - (double)3) * (double)this.scale;
        if (button == 0 && bl3 || bl4) {
            this.hovering = true;
        }
    }

    public void onScroll(int mouseX, int mouseY) {
        if (this.isMouseInside(mouseX, mouseY)) {
            double d = Math.round(this.startPosition / (double)25);
            this.startPosition -= d;
            if (this.startPosition != 0.0) {
                this.scrollAmount = (int)((double)this.scrollAmount + d);
            }
        } else {
            this.startPosition = 0.0;
        }
        if (this.bottom) {
            if (this.scrollAmount < -this.scrollHeight + this.height) {
                this.scrollAmount = -this.scrollHeight + this.height;
                this.startPosition = 0.0;
            }
            if (this.scrollAmount > 0) {
                this.scrollAmount = 0;
                this.startPosition = 0.0;
            }
        }
        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, this.scrollAmount, 0.0f);
    }

    public void onGuiDraw(int mouseX, int mouseY) {
        this.bottom = true;
        GL11.glPopMatrix();
        boolean bl = this.scrollHeight > this.height;
//        if (!(!this.hovering || Mouse.isButtonDown(0) && this.isMouseInside(mouseX, mouseY))) {
//            this.hovering = false;
//        }
        if (this.hovering && !Mouse.isButtonDown(0)) {
            this.hovering = false;
        }
        double d = this.height - 10;
        double d2 = this.scrollHeight;
        double d3 = d / d2 * (double)100;
        double d4 = d / (double)100 * d3;
        double d5 = (double)this.scrollAmount / 100.0 * d3;
        if (bl) {
            int n3 = this.height;
            boolean bl4 = (float)mouseX > (float)(this.x + this.width - 9) * this.scale && (float)mouseX < (float)(this.x + this.width - 3) * this.scale && (double)mouseY > ((double)(this.y + 11) - d5) * (double)this.scale && (double)mouseY < ((double)(this.y + 8) + d4 - d5) * (double)this.scale;
            boolean bl3 = (float)mouseX > (float)(this.x + this.width - 9) * this.scale && (float)mouseX < (float)(this.x + this.width - 3) * this.scale && (float)mouseY > (float)(this.y + 11) * this.scale && (double)mouseY < ((double)(this.y + 6) + d - (double)3) * (double)this.scale;
//            if (Mouse.isButtonDown(0) && !this.hovering && bl3) {
//                this.hovering = true;
//            }
            if (this.hovering) {
                if ((float)this.scrollAmount != this.scrollPosition && (double)this.scrollPosition != d4 / (double)2 && (double)this.scrollPosition != d4 / (double)2 + (double)(-this.scrollHeight) + (double)n3) {
                    if ((double)mouseY > ((double)(this.y + 11) + d4 - d4 / 4.0 - d5) * (double)this.scale) {
                        this.scrollAmount = (int)((double)this.scrollAmount - d2 / 70.0);
                    } else if ((double)mouseY < ((double)(this.y + 11) + d4 / 4.0 - d5) * (double)this.scale) {
                        this.scrollAmount = (int)((double)this.scrollAmount + d2 / 70.0);
                    }
                    this.scrollPosition = this.scrollAmount;
                } else if ((double)mouseY > ((double)(this.y + 11) + d4 - d4 / 4.0 - d5) * (double)this.scale || (double)mouseY < ((double)(this.y + 11) + d4 / 4.0 - d5) * (double)this.scale) {
                    this.scrollPosition = 1.0f;
                }
            }
            if (this.scrollAmount < -this.scrollHeight + n3) {
                this.scrollAmount = -this.scrollHeight + n3;
                this.startPosition = 0.0;
            }
            if (this.scrollAmount > 0) {
                this.scrollAmount = 0;
                this.startPosition = 0.0;
            }
            RenderUtil.drawRoundedRect(this.x + this.width - 6, this.y + 11, this.x + this.width - 4, (double)(this.y + 6) + d - (double)3, 2, bl3 && !bl4 ? 0x6F000000 : 0x3F000000);
            RenderUtil.drawRoundedRect(this.x + this.width - 7, (double)(this.y + 11) - d5, this.x + this.width - 3, (double)(this.y + 8) + d4 - d5, 4, bl4 || this.hovering ? 0xFF0240FF : -12418828);
        }
        if (!bl && this.scrollAmount != 0) {
            this.scrollAmount = 0;
        }
    }

    @Override
    public void onScroll(int n) {
        if (n != 0 && this.scrollHeight >= this.height) {
            this.startPosition += n / 2;
        }
    }

    public abstract boolean hasSettings(AbstractModule module);

    public abstract void handleModuleMouseClick(AbstractModule var1);
}
