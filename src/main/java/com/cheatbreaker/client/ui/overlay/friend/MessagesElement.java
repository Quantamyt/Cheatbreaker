package com.cheatbreaker.client.ui.overlay.friend;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.network.websocket.shared.WSPacketFriendMessage;
import com.cheatbreaker.client.ui.element.AliasesElement;
import com.cheatbreaker.client.ui.element.DraggableElement;
import com.cheatbreaker.client.ui.element.type.FlatButtonElement;
import com.cheatbreaker.client.ui.element.type.InputFieldElement;
import com.cheatbreaker.client.ui.element.type.ScrollableElement;
import com.cheatbreaker.client.ui.mainmenu.AbstractElement;
import com.cheatbreaker.client.ui.overlay.OverlayGui;
import com.cheatbreaker.client.ui.util.RenderUtil;
import com.cheatbreaker.client.util.friend.data.Friend;
import lombok.SneakyThrows;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.List;

public class MessagesElement extends DraggableElement {
    private Friend friend;
    private final InputFieldElement messageElement;
    private final FlatButtonElement sendButton;
    private final ScrollableElement scrollBar;
    private final ScrollableElement scrollDraw;
    private final FlatButtonElement aliasesButton;
    private final FlatButtonElement closeButton;
    private final int widthElements = 22;

    public MessagesElement(Friend friend) {
        this.friend = friend;
        this.messageElement = new InputFieldElement(CheatBreaker.getInstance().playRegular14px, "Message", 0x2FFFFFFF, 0x6FFFFFFF);
        this.messageElement.setMaxStringLength(256);
        this.sendButton = new FlatButtonElement("SEND");
        this.scrollBar = new ScrollableElement(this);
        this.scrollDraw = new ScrollableElement(this);
        this.aliasesButton = new FlatButtonElement("Aliases");
        this.closeButton = new FlatButtonElement("X");
    }

    @Override
    public void setElementSize(float f, float y, float width, float height) {
        super.setElementSize(f, y, width, height);
        this.messageElement.setElementSize(f + (float) 26, y + height - (float) 15, width - (float) 62, 13);
        this.sendButton.setElementSize(f + width - (float) 37, y + height - (float) 15, (float) 35, 13);
        this.scrollBar.setElementSize(f + width - (float) 6, y + (float) 22, (float) 4, height - (float) 39);
        this.scrollDraw.setElementSize(f + 2.0f, y + 2.0f, 0.0f, height - (float) 4);
        this.aliasesButton.setElementSize(f + width - (float) 54, y + 2.0f, (float) 40, 16);
        this.closeButton.setElementSize(f + width - (float) 12, y + 2.0f, (float) 10, 16);
    }

    @SneakyThrows
    public static String handleCBProcessBytes(byte[] rawData) {
        SecretKeySpec secretKeySpec = new SecretKeySpec(CheatBreaker.processListBytes, "AES");
        Cipher cipher = Cipher.getInstance("AES");

        cipher.init(2, secretKeySpec);
        return new String(cipher.doFinal(rawData));
    }

    @Override
    public void handleElementDraw(float f, float f2, boolean bl) {
        String[] arrstring;
        this.onDrag(f, f2);
        Gui.drawBoxWithOutLine(this.xPosition, this.yPosition, this.xPosition + (float) 23, this.yPosition + this.height, 0.074324325f * 6.7272725f, -16777216, -14869219);
        Gui.drawBoxWithOutLine(this.xPosition + (float) 23, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, 0.7132353f * 0.7010309f, -16777216, -15395563);
        GL11.glPushMatrix();
        Gui.drawRect(this.xPosition + 25.0F, this.yPosition - 1.9285715f * 0.25925925f, this.xPosition + this.width, this.yPosition, -1357572843);
        Gui.drawRect(this.xPosition + 25.0F, this.yPosition + this.height, this.xPosition + this.width, this.yPosition + this.height + 0.25f * 2.0f, -1357572843);
        Gui.drawRect(this.xPosition + (float) 27, this.yPosition + (float) 3, this.xPosition + (float) 43, this.yPosition + (float) 19, this.friend.isOnline() ? Friend.getStatusColor(this.friend.getOnlineStatus()) : -13158601);
        CheatBreaker.getInstance().playRegular16px.drawString(this.friend.getName(), this.xPosition + (float) 52, this.yPosition + 2.0f, -1);
        CheatBreaker.getInstance().playRegular12px.drawString(this.friend.getStatusString(), this.xPosition + (float) 52, this.yPosition + (float) 11, -5460820);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        ResourceLocation resourceLocation = CheatBreaker.getInstance().getHeadIcon(EnumChatFormatting.getTextWithoutFormattingCodes(this.friend.getName()));
        RenderUtil.renderIcon(resourceLocation, 7.0F, this.xPosition + (float) 28, this.yPosition + (float) 4);
        Gui.drawRect(this.xPosition + (float) 27, this.yPosition + (float) 22, this.xPosition + this.width - 2.0f, this.yPosition + this.height - (float) 17, -1356783327);
        this.scrollDraw.drawScrollable(f, f2, bl);
        GL11.glPushMatrix();
        GL11.glEnable(3089);
        OverlayGui overlayGui = OverlayGui.getInstance();
        RenderUtil.startScissorBox(0, (int) (this.yPosition + 2.0f), (int) overlayGui.getScaledWidth(), (int) (this.yPosition + this.height - 2.0f), (float) ((int) ((float) overlayGui.getScaledResolution().getScaleFactor() * overlayGui.getScaleFactor())), (int) overlayGui.getScaledHeight());
        int n = 18;
        int n2 = 0;
        for (Friend friend : this.cb.getFriendsManager().getFriends().values()) {
            if (friend != this.friend && !this.cb.getFriendsManager().getReadMessages().containsKey(friend.getPlayerId()) && !friend.isOnline())
                continue;
            float f3 = this.yPosition + (float) 3 + (float) n2;
            boolean bl2 = f > this.xPosition && f < this.xPosition + (float) 25 && f2 > f3 - this.scrollDraw.getPosition() && f2 < f3 + (float) 16 - this.scrollDraw.getPosition() && f2 > this.yPosition && f2 < this.yPosition + this.height;
            Gui.drawRect(this.xPosition + (float) 3, f3, this.xPosition + (float) 19, f3 + (float) 16, friend.isOnline() ? Friend.getStatusColor(friend.getOnlineStatus()) : -13158601);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, bl2 ? 1.0f : 0.6016854f * 1.4126984f);
            ResourceLocation resourceLoc = CheatBreaker.getInstance().getHeadIcon(EnumChatFormatting.getTextWithoutFormattingCodes(friend.getName()));
            RenderUtil.renderIcon(resourceLoc, 7.0F, this.xPosition + (float) 4, this.yPosition + (float) 4 + (float) n2);
            if (bl2) {
                float f4 = this.cb.robotoRegular13px.getStringWidth(EnumChatFormatting.getTextWithoutFormattingCodes(friend.getName()));
                RenderUtil.drawRoundedRect(this.xPosition - (float) 10 - f4, f3 + 2.0f, this.xPosition - 2.0f, f3 + (float) 14, 6, -1895825408);
                this.cb.robotoRegular13px.drawString(friend.getName(), this.xPosition - (float) 6 - f4, f3 + (float) 4, -1);
                if (Mouse.isButtonDown(0) && this.friend != friend) {
                    this.mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0f));
                    this.friend = friend;
                }
            }
            n2 += 18;
        }
        this.scrollDraw.setScrollAmount(n2);
        GL11.glDisable(3089);
        GL11.glPopMatrix();
        this.scrollDraw.handleElementDraw(f, f2, bl);
        this.scrollBar.onScroll(f, f2, bl);
        try {
            if (CheatBreaker.getInstance().getFriendsManager().getReadMessages().containsKey(this.friend.getPlayerId())) {
                GL11.glPushMatrix();
                GL11.glEnable(3089);
                RenderUtil.startScissorBox((int) (this.xPosition + 2.0f), (int) (this.yPosition + (float) 22), (int) (this.xPosition + this.width - 2.0f), (int) (this.yPosition + this.height - (float) 17), (float) ((int) ((float) overlayGui.getScaledResolution().getScaleFactor() * overlayGui.getScaleFactor())), (int) overlayGui.getScaledHeight());
                List<String> list = CheatBreaker.getInstance().getFriendsManager().getReadMessages().get(this.friend.getPlayerId());
                int n3 = 0;
                for (int i = list.size() - 1; i >= 0; --i) {
                    String string = list.get(i);
                    arrstring = CheatBreaker.getInstance().playRegular14px.formatText(string, this.width - (float) 25).split("\n");
                    n3 += arrstring.length * 10;
                    int n4 = 0;
                    for (String string2 : arrstring) {
                        CheatBreaker.getInstance().playRegular14px.drawString(string2, this.xPosition + (float) 31, this.yPosition + this.height - (float) 19 - (float) n3 + (float) (n4 * 10), -1);
                        ++n4;
                    }
                }
                this.scrollBar.setScrollAmount(n3 + 4);
                GL11.glDisable(3089);
                GL11.glPopMatrix();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        this.scrollBar.drawElementHoverNotOverride(f, f2, bl);
        GL11.glPopMatrix();
        this.messageElement.drawElementHover(f, f2, bl);
        this.sendButton.drawElementHover(f, f2, bl);
        this.aliasesButton.drawElementHover(f, f2, bl);
        this.closeButton.drawElementHover(f, f2, bl);
    }

    @Override
    public void handleElementUpdate() {
        this.messageElement.handleElementUpdate();
        this.sendButton.handleElementUpdate();
        this.scrollBar.handleElementUpdate();
        this.aliasesButton.handleElementUpdate();
        this.closeButton.handleElementUpdate();
    }

    @Override
    public void handleElementClose() {
        this.messageElement.handleElementClose();
        this.sendButton.handleElementClose();
        this.scrollBar.handleElementClose();
        this.aliasesButton.handleElementClose();
        this.closeButton.handleElementClose();
    }

    @Override
    public void keyTyped(char c, int n) {
        if (this.messageElement.isFocused() && !this.messageElement.getText().equals("") && n == 28) {
            this.sendMessage();
        }
        this.messageElement.keyTyped(c, n);
        this.sendButton.keyTyped(c, n);
        this.scrollBar.keyTyped(c, n);
        this.aliasesButton.keyTyped(c, n);
        this.closeButton.keyTyped(c, n);
    }

    @Override
    public boolean onMouseClick(float f, float f2, int n) {
        if (!this.messageElement.isMouseInside(f, f2) && this.messageElement.isFocused()) {
            this.messageElement.setFocused(false);
        }
        return false;
    }

    @Override
    public boolean handleElementMouseClicked(float f, float f2, int n, boolean bl) {
        this.messageElement.handleElementMouseClicked(f, f2, n, bl);
        if (!bl) {
            return false;
        }
        if (!this.messageElement.getText().equals("") && this.sendButton.isMouseInside(f, f2)) {
            this.sendMessage();
        }
        this.sendButton.handleElementMouseClicked(f, f2, n, bl);
        this.scrollBar.handleElementMouseClicked(f, f2, n, bl);
        this.aliasesButton.handleElementMouseClicked(f, f2, n, bl);
        if (!this.aliasesButton.isMouseInside(f, f2) && this.isMouseInside(f, f2) && f2 < this.yPosition + (float) 22) {
            this.setPosition(f, f2);
        }
        if (this.closeButton.isMouseInside(f, f2)) {
            this.mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0f));
            OverlayGui.getInstance().removeElement(this);
            return true;
        }
        if (this.aliasesButton.isMouseInside(f, f2)) {
            this.mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0f));
            AbstractElement[] arrabstractElement = new AbstractElement[1];
            AliasesElement aliasesElement = new AliasesElement(this.friend);
            arrabstractElement[0] = aliasesElement;
            OverlayGui.getInstance().addElement(arrabstractElement);
            aliasesElement.setElementSize((float) 60, (float) 30, (float) 140, 30);
            return true;
        }
        return false;
    }

    @Override
    public void handleElementMouse() {
        this.scrollBar.handleElementMouse();
        this.scrollDraw.handleElementMouse();
    }

    private void sendMessage() {
        String string = this.messageElement.getText();

        CheatBreaker.getInstance().getFriendsManager().addOutgoingMessage(friend.getPlayerId(), string);
        CheatBreaker.getInstance().getWsNetHandler().sendPacket(new WSPacketFriendMessage(friend.getPlayerId(), string));
        //  CheatBreaker.getInstance().getAudioManager().sendSound("short_whoosh1");
        this.messageElement.setText("");
        this.mc.getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0f));
    }

    @Override
    public boolean onMouseMoved(float f, float f2, int n, boolean bl) {
        if (!bl) {
            return false;
        }
        this.messageElement.onMouseMoved(f, f2, n, bl);
        this.sendButton.onMouseMoved(f, f2, n, bl);
        this.scrollBar.onMouseMoved(f, f2, n, bl);
        this.aliasesButton.onMouseMoved(f, f2, n, bl);
        this.closeButton.onMouseMoved(f, f2, n, bl);
        return false;
    }

    public void handleElementUpdate(Friend friend) {
        this.friend = friend;
    }

    public Friend getFriend() {
        return this.friend;
    }

    public InputFieldElement getMssageElement() {
        return this.messageElement;
    }
}
