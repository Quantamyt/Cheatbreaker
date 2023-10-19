package com.cheatbreaker.client.ui.element.type;

import com.cheatbreaker.client.ui.mainmenu.AbstractElement;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;


public class ScrollableElement extends AbstractElement {
    protected final AbstractElement abstractElement;
    protected double IllIIIIIIIlIlIllllIIllIII;
    protected float position;
    protected float scrollAmount;
    protected boolean hovering;
    protected boolean llIIlllIIIIlllIllIlIlllIl;
    protected float lIIlIlIllIIlIIIlIIIlllIII;
    private float IIIlllIIIllIllIlIIIIIIlII;
    private boolean buttonHeld;

    public ScrollableElement(AbstractElement var1) {
        this.abstractElement = var1;
    }

    public boolean isMouseInside(float var1, float var2) {
        return var1 >= this.xPosition && var1 <= this.xPosition + this.width && var2 > this.yPosition && var2 < this.yPosition + this.height;
    }

    public void handleElementDraw(float var1, float var2, boolean var3) {
        this.llIIlllIIIIlllIllIlIlllIl = true;
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
        float var7 = var5 / var6 * (float) 100;
        float var8 = var5 / (float) 100 * var7;
        float var9 = this.position / (float) 100 * var7;
        float var10;
        if (Mouse.isButtonDown(0) && this.buttonHeld) {
            var10 = var2 - this.yPosition;
            float var11 = var10 / this.height;
            this.position = -(this.scrollAmount * var11) + var8 / 2.0F;
        }

        if (var4) {
            var10 = this.height;
            boolean var13 = var1 >= this.xPosition && var1 <= this.xPosition + this.width && var2 > this.yPosition - var9 && var2 < this.yPosition + var8 - var9;
            boolean var12 = var1 >= this.xPosition && var1 <= this.xPosition + this.width && var2 > this.yPosition && var2 < this.yPosition + var5 - (float) 3;
            if (Mouse.isButtonDown(0) && !this.hovering && var12) {
            }

            if (this.hovering) {
                if (this.position != this.lIIlIlIllIIlIIIlIIIlllIII && this.lIIlIlIllIIlIIIlIIIlllIII != var8 / 2.0F && this.lIIlIlIllIIlIIIlIIIlllIII != var8 / 2.0F + -this.scrollAmount + var10) {
                    if (var2 > this.yPosition + var8 - var8 / (float) 4 - var9) {
                        this.position -= var6 / (float) 7;
                    } else if (var2 < this.yPosition + var8 / (float) 4 - var9) {
                        this.position += var6 / (float) 7;
                    }

                    this.lIIlIlIllIIlIIIlIIIlllIII = this.position;
                } else if (var2 > this.yPosition + var8 - var8 / (float) 4 - var9 || var2 < this.yPosition + var8 / (float) 4 - var9) {
                    this.lIIlIlIllIIlIIIlIIIlllIII = 1.0F;
                }
            }

            if (this.position < -this.scrollAmount + var10) {
                this.position = -this.scrollAmount + var10;
                this.IllIIIIIIIlIlIllllIIllIII = 0.0D;
            }

            if (this.position > 0.0F) {
                this.position = 0.0F;
                this.IllIIIIIIIlIlIllllIIllIII = 0.0D;
            }

            Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, -13158601);
            Gui.drawRect(this.xPosition, this.yPosition - var9, this.xPosition + this.width, this.yPosition + var8 - var9, !var13 && !this.hovering ? -4180940 : -52429);
        }

        if (!var4 && this.position != 0.0F) {
            this.position = 0.0F;
        }

    }

    public void drawScrollable(float var1, float var2, boolean var3) {
        if (var3 && (this.abstractElement == null || this.abstractElement.isMouseInside(var1, var2)) && this.IllIIIIIIIlIlIllllIIllIII != 0.0D) {
            this.position = (float) ((double) this.position + this.IllIIIIIIIlIlIllllIIllIII / (double) 8);
            this.IllIIIIIIIlIlIllllIIllIII = 0.0D;
        }

        if (this.llIIlllIIIIlllIllIlIlllIl) {
            if (this.position < -this.scrollAmount + this.height) {
                this.position = -this.scrollAmount + this.height;
                this.IllIIIIIIIlIlIllllIIllIII = 0.0D;
            }

            if (this.position > 0.0F) {
                this.position = 0.0F;
                this.IllIIIIIIIlIlIllllIIllIII = 0.0D;
            }
        }

        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, this.position, 0.0F);
    }

    // Naming is temporary. This is checking for a specific issue.
    public void drawElementHoverNotOverride(float var1, float var2, boolean var3) {
        this.llIIlllIIIIlllIllIlIlllIl = true;
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
        float var7 = var5 / var6 * (float) 100;
        float var8 = var5 / (float) 100 * var7;
        float var9 = this.position / (float) 100 * var7;
        float var10;
        if (Mouse.isButtonDown(0) && this.buttonHeld) {
            var10 = var2 - this.yPosition;
            float var11 = var10 / this.height;
            this.position = -(this.scrollAmount - this.height / 2.0F) + this.scrollAmount * var11 + var8 / 2.0F;
        }

        if (var4) {
            var10 = this.height;
            boolean var13 = var1 >= this.xPosition && var1 <= this.xPosition + this.width && var2 > this.yPosition - var9 && var2 < this.yPosition + var8 - var9;
            boolean var12 = var1 >= this.xPosition && var1 <= this.xPosition + this.width && var2 > this.yPosition && var2 < this.yPosition + var5 - (float) 3;
            if (Mouse.isButtonDown(0) && !this.hovering && var12) {
            }

            if (this.hovering) {
                if (this.position != this.lIIlIlIllIIlIIIlIIIlllIII && this.lIIlIlIllIIlIIIlIIIlllIII != var8 / 2.0F && this.lIIlIlIllIIlIIIlIIIlllIII != var8 / 2.0F + -this.scrollAmount + var10) {
                    if (var2 > this.yPosition + this.height - var8 - var8 / (float) 4 + var9) {
                        this.position = var6 / (float) 7;
                    } else if (var2 < this.yPosition + this.height - var8 / (float) 4 + var9) {
                        this.position += var6 / (float) 7;
                    }

                    this.lIIlIlIllIIlIIIlIIIlllIII = this.position;
                } else if (var2 > this.yPosition + this.height - var8 - var8 / (float) 4 + var9 || var2 < this.yPosition + this.height - var8 / (float) 4 - var9) {
                    this.lIIlIlIllIIlIIIlIIIlllIII = 1.0F;
                }
            }

            if (this.position < -this.scrollAmount + var10) {
                this.position = -this.scrollAmount + var10;
                this.IllIIIIIIIlIlIllllIIllIII = 0.0D;
            }

            if (this.position > 0.0F) {
                this.position = 0.0F;
                this.IllIIIIIIIlIlIllllIIllIII = 0.0D;
            }

            Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, -13158601);
            Gui.drawRect(this.xPosition, this.yPosition + this.height + var9, this.xPosition + this.width, this.yPosition + this.height - var8 + var9, !var13 && !this.hovering ? -4180940 : -52429);
        }

        if (!var4 && this.position != 0.0F) {
            this.position = 0.0F;
        }

    }

    public void onScroll(float var1, float var2, boolean var3) {
        if (var3 && (this.abstractElement == null || this.abstractElement.isMouseInside(var1, var2)) && this.IllIIIIIIIlIlIllllIIllIII != 0.0D) {
            this.position = (float) ((double) this.position - this.IllIIIIIIIlIlIllllIIllIII / (double) 8);
            this.IllIIIIIIIlIlIllllIIllIII = 0.0D;
        }

        if (this.llIIlllIIIIlllIllIlIlllIl) {
            if (this.position < -this.scrollAmount + this.height) {
                this.position = -this.scrollAmount + this.height;
                this.IllIIIIIIIlIlIllllIIllIII = 0.0D;
            }

            if (this.position > 0.0F) {
                this.position = 0.0F;
                this.IllIIIIIIIlIlIllllIIllIII = 0.0D;
            }
        }

        GL11.glPushMatrix();
        GL11.glTranslatef(0.0F, -this.position, 0.0F);
    }

    public float getPosition() {
        return this.position;
    }

    public boolean isAtBottom() {
        return this.scrollAmount > this.height;
    }

    public boolean handleElementMouseClicked(float var1, float var2, int var3, boolean var4) {
        if (this.isMouseInside(var1, var2) && var4) {
            this.IIIlllIIIllIllIlIIIIIIlII = var2 - this.yPosition;
            this.buttonHeld = true;
        }

        return false;
    }

    public void handleElementMouse() {
        int var1 = Mouse.getEventDWheel();
        if (var1 != 0 && this.scrollAmount >= this.height) {
            this.IllIIIIIIIlIlIllllIIllIII += (float) var1 / (0.48387095F * 3.6166668F);
        }

    }

    public AbstractElement getAbstractElement() {
        return this.abstractElement;
    }

    public void setScrollAmount(float var1) {
        this.scrollAmount = var1;
    }

    public boolean isHovering() {
        return this.hovering;
    }

    public boolean isButtonHeld() {
        return this.buttonHeld;
    }
}

