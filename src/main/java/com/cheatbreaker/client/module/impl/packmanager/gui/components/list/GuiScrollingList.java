package com.cheatbreaker.client.module.impl.packmanager.gui.components.list;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.ui.util.RenderUtil;
import com.cheatbreaker.client.util.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public abstract class GuiScrollingList {
    protected final Minecraft mc;
    public final int listWidth;
    public final int listHeight;
    public final int top;
    public final int bottom;
    public final int right;
    public final int left;
    public final String title;
    protected final int slotHeight;
    protected int selectedIndex;
    private int scrollIndex;
    private int scrollDirection;
    private float initialMouseClickY;
    private float scrollFactor;
    private float scrollDistance;
    private boolean hovering;
    private long lastClickTime;
    private final double[] scrollCache;

    public GuiScrollingList(Minecraft mc, int x, int y, int listWidth, int listHeight, int slotHeight, String title) {
        this.mc = mc;
        this.listWidth = listWidth;
        this.listHeight = listHeight;
        this.left = x;
        this.top = y;
        this.right = x + listWidth;
        this.bottom = y + listHeight;
        this.slotHeight = slotHeight;
        this.title = title;
        this.selectedIndex = -1;
        this.scrollIndex = -1;
        this.initialMouseClickY = -2.0f;
        this.scrollCache = new double[]{5.088448, 4.809692672, 3.4292885120000003, 3.268147903999999, 2.697228288, 2.019487744, 1.8882322560000002, 1.6936698879999996, 1.4352491520000008, 1.2045501440000006, 0.7097322879999997, 0.5842770560000003, 0.5043583360000001, 0.37950342400000014, 0.282300416, 0.21170873600000029, 0.09733600000000031, 0.08991539199999998, 0.06209913599999961, 0.030371328000000197, 0.01452678400000007, 0.006229504000000219, 0.0017279999999999518};
    }

    protected abstract void drawBackground();

    protected abstract void drawSlot(int var1, int var2, int var3, int var4, int var5, int var6, boolean var7);

    protected abstract void onClick(int var1, boolean var2);

    protected abstract int getSize();

    public void draw(int mouseX, int mouseY) {
        int top;
        this.drawBackground();
        this.hovering = this.left <= mouseX && mouseX <= this.left + this.listWidth && this.top <= mouseY && mouseY <= this.bottom;
        int listLength = this.getSize();
        int scrollBarWidth = 6;
        int scrollBarRight = this.left + this.listWidth;
        int scrollBarLeft = scrollBarRight - 6;
        int slotRight = scrollBarLeft - 1;
        int viewHeight = this.bottom - this.top;
        int border = 4;
        if (Mouse.isButtonDown(0)) {
            if (this.initialMouseClickY == -1.0f) {
                if (this.hovering) {
                    int mouseListY = mouseY - this.top + (int) this.scrollDistance - 4;
                    int slotIndex = mouseListY / this.slotHeight;
                    if (0 <= slotIndex && slotIndex < listLength && mouseX <= slotRight && 0 <= mouseListY) {
                        this.onClick(slotIndex, slotIndex == this.selectedIndex && System.currentTimeMillis() - this.lastClickTime < 500L);
                        this.selectedIndex = this.selectedIndex == slotIndex ? -1 : slotIndex;
                        this.lastClickTime = System.currentTimeMillis();
                    }
                    if (scrollBarLeft <= mouseX && mouseX <= scrollBarRight) {
                        this.scrollFactor = -1.0f;
                        int scrollHeight = this.getContentHeight() - viewHeight - 4;
                        if (scrollHeight < 1) {
                            scrollHeight = 1;
                        }
                        if ((top = (int) ((float) (viewHeight * viewHeight) / (float) this.getContentHeight())) < 32) {
                            top = 32;
                        }
                        if (top > viewHeight - 8) {
                            top = viewHeight - 8;
                        }
                        this.scrollFactor /= (float) (viewHeight - top) / (float) scrollHeight;
                    } else {
                        this.scrollFactor = 1.0f;
                    }
                    this.initialMouseClickY = mouseY;
                } else {
                    this.initialMouseClickY = -2.0f;
                }
            } else if (this.initialMouseClickY >= 0.0f) {
                this.scrollDistance -= ((float) mouseY - this.initialMouseClickY) * this.scrollFactor;
                this.initialMouseClickY = mouseY;
            }
        } else {
            if (this.scrollIndex != -1) {
                this.scrollDistance = (float) ((double) this.scrollDistance + this.scrollCache[this.scrollIndex] * (double) this.scrollDirection * 2.0);
                ++this.scrollIndex;
                if (this.scrollIndex >= this.scrollCache.length) {
                    this.scrollIndex = -1;
                }
            }
            this.initialMouseClickY = -1.0f;
        }
        this.applyScrollLimits();
        int baseY = this.top + 2 - (int) this.scrollDistance;
        for (int index = 0; index < listLength; ++index) {
            int slotTop = baseY + index * this.slotHeight;
            int slotBuffer = this.slotHeight - 4;
            int slotBottom = slotTop + slotBuffer + 2;
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
            GL11.glEnable(3042);
            GL11.glPushMatrix();
            GL11.glEnable(3089);
            ScaledResolution res = new ScaledResolution(this.mc);
            RenderUtil.startMCScaledScissorBox(this.left, this.top + 1, this.right, this.bottom, res);
            this.drawSlot(index, slotRight, slotTop, slotBuffer, mouseX, mouseY, this.left <= mouseX && mouseX < slotRight && slotTop - 2 <= mouseY && mouseY < slotBottom);
            GL11.glDisable(3089);
            GL11.glPopMatrix();
        }
        int extraHeight = this.getContentHeight() - viewHeight - 4;
        if (extraHeight > 0) {
            int height = viewHeight * viewHeight / this.getContentHeight();
            if (height < 32) {
                height = 32;
            }
            if (height > viewHeight - 8) {
                height = viewHeight - 8;
            }
            if ((top = (int) this.scrollDistance * (viewHeight - height) / extraHeight + this.top) < this.top) {
                top = this.top;
            }
            Gui.drawRect(scrollBarLeft, top, scrollBarRight, top + height, -4144960);
        }
        GlStateManager.shadeModel(7424);
        GL11.glEnable(3553);
        GL11.glEnable(3008);
        Gui.zLevel = 1;
        this.mc.ingameGUI.drawGradientRect(this.left, this.top, scrollBarRight, this.top + 5, -16777216, 0);
        this.mc.ingameGUI.drawGradientRect(this.left, this.bottom - 5, scrollBarRight, this.bottom, 0, -16777216);
        Gui.zLevel = -90;
    }

    public void handleMouseInput() {
        int scroll;
        if (this.hovering && (scroll = Mouse.getEventDWheel()) != 0) {
            scroll = scroll > 0 ? -1 : 1;
            if ((Boolean) CheatBreaker.getInstance().getGlobalSettings().packSmoothScrolling.getValue()) {
                this.scrollDirection = scroll;
                this.scrollIndex = 0;
            } else {
                this.scrollDistance += (float) (scroll * this.slotHeight / 2);
            }
        }
    }

    private void applyScrollLimits() {
        int listHeight = this.getContentHeight() - (this.bottom - this.top - 4);
        if (listHeight < 0) {
            listHeight /= 2;
        }
        if (this.scrollDistance > (float) listHeight) {
            this.scrollDistance = listHeight;
        }
        if (this.scrollDistance < 0.0f) {
            this.scrollDistance = 0.0f;
        }
    }

    protected int getContentHeight() {
        return this.getSize() * this.slotHeight;
    }
}
