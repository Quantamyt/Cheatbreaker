package com.cheatbreaker.client.ui.element.type;

import com.cheatbreaker.client.ui.mainmenu.AbstractElement;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class ScrollableElement extends AbstractElement {
    @Getter protected final AbstractElement abstractElement;
    protected double internalScrollAmount;
    @Getter protected float position;
    @Setter protected float scrollAmount;
    @Getter protected boolean hovering;
    protected boolean drawing; // only gets set to true when its actually drawing
    protected float oldTranslateY;
    @Getter private boolean buttonHeld;

    public ScrollableElement(AbstractElement var1) {
        this.abstractElement = var1;
    }

    public boolean isMouseInside(float var1, float var2) {
        return var1 >= this.xPosition && var1 <= this.xPosition + this.width && var2 > this.yPosition && var2 < this.yPosition + this.height;
    }

    public void handleElementDraw(float var1, float var2, boolean var3) {
        this.drawing = true;
        GL11.glPopMatrix();
        boolean var4 = this.isAtBottom();
        if (this.hovering && (!Mouse.isButtonDown(0) || !this.isMouseInside(var1, var2) || !var3)) {
            this.hovering = false;
        }

        if (this.buttonHeld && !Mouse.isButtonDown(0)) {
            this.buttonHeld = false;
        }

        float var5 = this.height;
        float var6 = this.scrollAmount;
        float var7 = var5 / var6 * (float)100;
        float var8 = var5 / (float)100 * var7;
        float var9 = this.position / (float)100 * var7;
        float yPos;
        if (Mouse.isButtonDown(0) && this.buttonHeld) {
            yPos = var2 - this.yPosition;
            float var11 = yPos / this.height;
            this.position = -(this.scrollAmount * var11) + var8 / 2.0F;
        }

        if (var4) {
            yPos = this.height;
            boolean var13 = var1 >= this.xPosition && var1 <= this.xPosition + this.width && var2 > this.yPosition - var9 && var2 < this.yPosition + var8 - var9;
            boolean var12 = var1 >= this.xPosition && var1 <= this.xPosition + this.width && var2 > this.yPosition && var2 < this.yPosition + var5 - (float)3;
            if (Mouse.isButtonDown(0) && !this.hovering && var12) {
                // ???
            }

            if (this.hovering) {
                if (this.position != this.oldTranslateY && this.oldTranslateY != var8 / 2.0F && this.oldTranslateY != var8 / 2.0F + -this.scrollAmount + yPos) {
                    if (var2 > this.yPosition + var8 - var8 / (float)4 - var9) {
                        this.position -= var6 / (float)7;
                    } else if (var2 < this.yPosition + var8 / (float)4 - var9) {
                        this.position += var6 / (float)7;
                    }

                    this.oldTranslateY = this.position;
                } else if (var2 > this.yPosition + var8 - var8 / (float)4 - var9 || var2 < this.yPosition + var8 / (float)4 - var9) {
                    this.oldTranslateY = 1.0F;
                }
            }

            if (this.position < -this.scrollAmount + yPos) {
                this.position = -this.scrollAmount + yPos;
                this.internalScrollAmount = 0.0D;
            }

            if (this.position > 0.0F) {
                this.position = 0.0F;
                this.internalScrollAmount = 0.0D;
            }

            Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, -13158601);
            Gui.drawRect(this.xPosition, this.yPosition - var9, this.xPosition + this.width, this.yPosition + var8 - var9, !var13 && !this.hovering ? -4180940 : -52429);
        }

        if (!var4 && this.position != 0.0F) {
            this.position = 0.0F;
        }

    }

    public void drawScrollable(float f, float f2, boolean bl) {
        if (bl && (this.abstractElement == null || this.abstractElement.isMouseInside(f, f2)) && this.internalScrollAmount != 0.0) {
            this.position = (float)((double)this.position + this.internalScrollAmount / (double)8);
            this.internalScrollAmount = 0.0;
        }

        if (this.drawing) {
            if (this.position < -this.scrollAmount + this.height) {
                this.position = -this.scrollAmount + this.height;
                this.internalScrollAmount = 0.0;
            }
            if (this.position > 0.0f) {
                this.position = 0.0f;
                this.internalScrollAmount = 0.0;
            }
        }

        GL11.glPushMatrix();
        GL11.glTranslatef(0.0f, this.position, 0.0f);
    }

    // Naming is temporary. This is checking for a specific issue.
    public void drawElementHoverNotOverride(float var1, float var2, boolean var3) {
        this.drawing = true;
        GL11.glPopMatrix();
        boolean var4 = this.isAtBottom();
        if (this.hovering && (!Mouse.isButtonDown(0) || !this.isMouseInside(var1, var2) || !var3)) {
            this.hovering = false;
        }

        if (this.buttonHeld && !Mouse.isButtonDown(0)) {
            this.buttonHeld = false;
        }

        float var5 = this.height;
        float var6 = this.scrollAmount;
        float var7 = var5 / var6 * (float)100;
        float var8 = var5 / (float)100 * var7;
        float var9 = this.position / (float)100 * var7;
        float var10;
        if (Mouse.isButtonDown(0) && this.buttonHeld) {
            var10 = var2 - this.yPosition;
            float var11 = var10 / this.height;
            this.position = -(this.scrollAmount - this.height / 2.0F) + this.scrollAmount * var11 + var8 / 2.0F;
        }

        if (var4) {
            var10 = this.height;
            boolean var13 = var1 >= this.xPosition && var1 <= this.xPosition + this.width && var2 > this.yPosition - var9 && var2 < this.yPosition + var8 - var9;
            boolean var12 = var1 >= this.xPosition && var1 <= this.xPosition + this.width && var2 > this.yPosition && var2 < this.yPosition + var5 - (float)3;
            if (Mouse.isButtonDown(0) && !this.hovering && var12) {
                // ???
            }

            if (this.hovering) {
                if (this.position != this.oldTranslateY && this.oldTranslateY != var8 / 2.0F && this.oldTranslateY != var8 / 2.0F + -this.scrollAmount + var10) {
                    if (var2 > this.yPosition + this.height - var8 - var8 / (float)4 + var9) {
                        this.position = var6 / (float)7;
                    } else if (var2 < this.yPosition + this.height - var8 / (float)4 + var9) {
                        this.position += var6 / (float)7;
                    }

                    this.oldTranslateY = this.position;
                } else if (var2 > this.yPosition + this.height - var8 - var8 / (float)4 + var9 || var2 < this.yPosition + this.height - var8 / (float)4 - var9) {
                    this.oldTranslateY = 1.0F;
                }
            }

            if (this.position < -this.scrollAmount + var10) {
                this.position = -this.scrollAmount + var10;
                this.internalScrollAmount = 0.0D;
            }

            if (this.position > 0.0F) {
                this.position = 0.0F;
                this.internalScrollAmount = 0.0D;
            }

            Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, -13158601);
            Gui.drawRect(this.xPosition, this.yPosition + this.height + var9, this.xPosition + this.width, this.yPosition + this.height - var8 + var9, !var13 && !this.hovering ? -4180940 : -52429);
        }

        if (!var4 && this.position != 0.0F) {
            this.position = 0.0F;
        }
    }

    public void onScroll(float var1, float var2, boolean var3) {
        if (var3 && (this.abstractElement == null || this.abstractElement.isMouseInside(var1, var2)) && this.internalScrollAmount != 0.0D) {
            this.position = (float)((double)this.position - this.internalScrollAmount / (double)8);
            this.internalScrollAmount = 0.0D;
        }

        if (this.drawing) {
            if (this.position < -this.scrollAmount + this.height) {
                this.position = -this.scrollAmount + this.height;
                this.internalScrollAmount = 0.0D;
            }

            if (this.position > 0.0F) {
                this.position = 0.0F;
                this.internalScrollAmount = 0.0D;
            }
        }

        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, -this.position, 0.0F);
    }

    public boolean isAtBottom() {
        return this.scrollAmount > this.height;
    }

    public boolean handleElementMouseClicked(float var1, float var2, int var3, boolean var4) {
        if (this.isMouseInside(var1, var2) && var4) {
            this.buttonHeld = true;
        }
        return false;
    }

    public void handleElementMouse() {
        int var1 = Mouse.getEventDWheel();
        if (var1 != 0 && this.scrollAmount >= this.height) {
            this.internalScrollAmount += (float)var1 / (0.48387095F * 3.6166668F);
        }
    }
}

