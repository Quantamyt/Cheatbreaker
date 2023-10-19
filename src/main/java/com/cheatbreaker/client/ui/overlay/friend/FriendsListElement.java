package com.cheatbreaker.client.ui.overlay.friend;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.ui.element.type.ElementListElement;
import com.cheatbreaker.client.ui.element.type.InputFieldElement;
import com.cheatbreaker.client.ui.element.type.ScrollableElement;
import com.cheatbreaker.client.ui.overlay.OverlayGui;
import com.cheatbreaker.client.ui.util.RenderUtil;
import com.google.common.collect.ImmutableList;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;


import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL11;

public class FriendsListElement extends ElementListElement<FriendElement> {
    private final InputFieldElement filterElement;
    private final ScrollableElement scrollableElement;
    private final List<FriendElement> friendElements = new ArrayList<>();

    public FriendsListElement(List<FriendElement> list) {
        super(list);
        this.filterElement = new InputFieldElement(CheatBreaker.getInstance().playRegular14px, "Filter", 0x2FFFFFFF, 0x6FFFFFFF);
        this.scrollableElement = new ScrollableElement(this);
    }

    public void updateSize() {
        this.setElementSize(this.xPosition, this.yPosition, this.width, this.height);
    }

    @Override
    public void setElementSize(float var1, float var2, float var3, float var4) {
        super.setElementSize(var1, var2, var3, var4);
        this.filterElement.setElementSize(0.0F, var2, var3, (float)13);
        this.scrollableElement.setElementSize(var1 + var3 - (float)4, var2, (float)4, var4);
        this.elements.sort((var0, var1x) -> {
            String var22 = EnumChatFormatting.getTextWithoutFormattingCodes(var0.getFriend().getName());
            String var32 = EnumChatFormatting.getTextWithoutFormattingCodes(var1x.getFriend().getName());
            if (var0.getFriend().isOnline() == var1x.getFriend().isOnline()) {
                return var22.compareTo(var32);
            } else {
                return var0.getFriend().isOnline() ? -1 : 1;
            }
        });
        boolean var5 = true;
        int var6 = 0;

        for (FriendElement var8 : this.elements) {
            if (this.isFilterMatch(var8)) {
                var8.setElementSize(var1, var2 + (float) 14 + (float) (var6 * 22), var3, (float) 22);
                ++var6;
            }
        }

        this.scrollableElement.setScrollAmount((float)(14 + this.elements.size() * 22));
    }

    private boolean isFilterMatch(FriendElement var1) {
        return this.filterElement.getText().equals("") || EnumChatFormatting.getTextWithoutFormattingCodes(var1.getFriend().getName()).toLowerCase().startsWith(this.filterElement.getText().toLowerCase());
    }

    public void handleElementDraw(float var1, float var2, boolean var3) {
        if (!this.friendElements.isEmpty()) {
            this.elements.removeAll(this.friendElements);
            OverlayGui.getInstance().getFriendsListElement().updateSize();
        }

        float var10002;
        float var10003;
        if (!CheatBreaker.getInstance().getWSNetHandler().isOpen()) {
            var10002 = this.xPosition + this.width / 2.0F;
            var10003 = this.yPosition + (float)10;
            CheatBreaker.getInstance().playBold18px.drawCenteredString("Connection lost", var10002, var10003, -1);
            var10002 = this.xPosition + this.width / 2.0F;
            var10003 = this.yPosition + (float)22;
            CheatBreaker.getInstance().playRegular14px.drawCenteredString("Please try again later.", var10002, var10003, -1);
        } else {
            GL11.glPushMatrix();
            GL11.glEnable(3089);
            OverlayGui var4 = OverlayGui.getInstance();
            this.scrollableElement.drawScrollable(var1, var2, var3);
            RenderUtil.startScissorBox((int)this.xPosition, (int)this.yPosition, (int)(this.xPosition + this.width), (int)(this.yPosition + this.height), (float)((int)((float)var4.getScaledResolution().getScaleFactor() * var4.getScaleFactor())), (int)var4.getScaledHeight());
            ImmutableList var5 = ImmutableList.copyOf((Collection)this.elements);
            Iterator var6 = var5.iterator();

            while(true) {
                FriendElement var7;
                do {
                    if (!var6.hasNext()) {
                        if (var5.isEmpty()) {
                            var10002 = this.xPosition + this.width / 2.0F;
                            var10003 = this.yPosition + (float)30;
                            CheatBreaker.getInstance().playBold18px.drawCenteredString("No friends", var10002, var10003, -1);
                        }

                        this.filterElement.drawElementHover(var1, var2 - this.scrollableElement.getPosition(), var3);
                        GL11.glDisable(3089);
                        GL11.glPopMatrix();
                        this.scrollableElement.handleElementDraw(var1, var2, var3);
                        return;
                    }

                    var7 = (FriendElement)var6.next();
                } while(!this.isFilterMatch(var7));

                var7.drawElementHover(var1, var2 - this.scrollableElement.getPosition(), var3 && !this.scrollableElement.isMouseInside(var1, var2));
            }
        }
    }

    public void handleElementMouse() {
        this.scrollableElement.handleElementMouse();
    }

    public void handleElementUpdate() {
        this.filterElement.handleElementUpdate();
        this.scrollableElement.handleElementUpdate();
    }

    public void handleElementClose() {
        this.filterElement.handleElementClose();
        this.scrollableElement.handleElementClose();
    }

    public void keyTyped(char c, int n) {
        super.keyTyped(c, n);
        this.filterElement.keyTyped(c, n);
        this.scrollableElement.keyTyped(c, n);
        this.setElementSize(this.xPosition, this.yPosition, this.width, this.height);
    }

    public boolean handleElementMouseClicked(float f, float f2, int n, boolean bl) {
        this.filterElement.handleElementMouseClicked(f, f2 - this.scrollableElement.getPosition(), n, bl);
        if (this.filterElement.isFocused() && n == 1 && this.filterElement.getText().equals("")) {
            this.updateSize();
        }

        if (!bl) {
            return false;
        } else {
            this.scrollableElement.handleElementMouseClicked(f, f2, n, bl);
            boolean var5 = false;
            Iterator var6 = this.elements.iterator();

            while(var6.hasNext()) {
                FriendElement var7 = (FriendElement)var6.next();
                if (this.isFilterMatch(var7)) {
                    if (var5) {
                        break;
                    }

                    var5 = var7.handleElementMouseClicked(f, f2 - this.scrollableElement.getPosition(), n, bl && !this.scrollableElement.isMouseInside(f, f2));
                }
            }

            return var5;
        }
    }

    public boolean onMouseMoved(float var1, float var2, int var3, boolean var4) {
        this.filterElement.onMouseMoved(var1, var2 - this.scrollableElement.getPosition(), var3, var4);
        this.scrollableElement.onMouseMoved(var1, var2, var3, var4);
        if (!var4) {
            return false;
        } else {
            boolean var5 = false;
            Iterator var6 = this.elements.iterator();

            while(var6.hasNext()) {
                FriendElement var7 = (FriendElement)var6.next();
                if (this.isFilterMatch(var7)) {
                    if (var5) {
                        break;
                    }

                    var5 = var7.onMouseMoved(var1, var2, var3, var4);
                }
            }

            return var5;
        }
    }

    public List<FriendElement> getElements() {
        return this.friendElements;
    }
}
