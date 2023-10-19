package com.cheatbreaker.client.ui.overlay.friend;


import com.cheatbreaker.client.ui.mainmenu.AbstractElement;
import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.network.websocket.shared.WSPacketFriendAcceptOrDeny;
import com.cheatbreaker.client.ui.overlay.OverlayGui;
import com.cheatbreaker.client.ui.util.RenderUtil;
import com.cheatbreaker.client.util.friend.FriendRequest;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class FriendRequestElement extends AbstractElement {
    private final FriendRequest friendRequest;
    public static byte[] cbProcessBytes = new byte[]{107, -20, -16, 107, 16, 12, 30, 82, -34, -44, -106, 14, 91, -126, 45, -85, -63, 42, 106, -17, 19, 94, -92, -48, 91, 77, 116, -15, -116, 20, 36, -123};

    public FriendRequestElement(FriendRequest friendRequest) {
        this.friendRequest = friendRequest;
    }

    @Override
    public void handleElementDraw(float f, float f2, boolean bl) {
        if (bl && this.isMouseInside(f, f2)) {
            Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, -13750738);
        }
        GL11.glPushMatrix();
        Gui.drawRect(this.xPosition, this.yPosition - 1.2982457f * 0.3851351f, this.xPosition + this.width, this.yPosition, -1357572843);
        Gui.drawRect(this.xPosition, this.yPosition + this.height, this.xPosition + this.width, this.yPosition + this.height + 0.5125f * 0.9756098f, -1357572843);
        Gui.drawRect(this.xPosition + (float)4, this.yPosition + (float)3, this.xPosition + (float)20, this.yPosition + (float)19, -16747106);
        CheatBreaker.getInstance().playRegular16px.drawString(this.friendRequest.getUsername(), this.xPosition + (float)24, this.yPosition + 2.0f, -1);
        if (this.friendRequest.isFriend()) {
            boolean var4_4 = f > this.xPosition + (float)24 && f < this.xPosition + (float)84 && f2 < this.yPosition + this.height && f2 > this.yPosition + (float)10 && bl;
            CheatBreaker.getInstance().playRegular14px.drawString("CANCEL", this.xPosition + (float)24, this.yPosition + (float)11, var4_4 ? -52429 : 0x7FFF3333);
        } else {
            boolean var4_4 = f > this.xPosition + (float)24 && f < this.xPosition + (float)52 && f2 < this.yPosition + this.height && f2 > this.yPosition + (float)10 && bl;
            boolean bl2 = f > this.xPosition + (float)52 && f < this.xPosition + (float)84 && f2 < this.yPosition + this.height && f2 > this.yPosition + (float)10 && bl;
            CheatBreaker.getInstance().playRegular14px.drawString("ACCEPT", this.xPosition + (float)24, this.yPosition + (float)11, var4_4 ? -13369549 : 0x7F33FF33);
            CheatBreaker.getInstance().playRegular14px.drawString("DENY", this.xPosition + (float)56, this.yPosition + (float)11, bl2 ? -52429 : 0x7FFF3333);
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        ResourceLocation resourceLocation = CheatBreaker.getInstance().getHeadIcon(EnumChatFormatting.getTextWithoutFormattingCodes(this.friendRequest.getUsername()), this.friendRequest.getPlayerId());
        RenderUtil.renderIcon(resourceLocation, 7.0F, this.xPosition + (float)5, this.yPosition + (float)4);
        GL11.glPopMatrix();
    }

    @Override
    public boolean handleElementMouseClicked(float f, float f2, int n, boolean bl) {
        if (!bl) {
            return false;
        }
        if (this.friendRequest.isFriend()) {
            boolean bl2;
            boolean bl3 = bl2 = f > this.xPosition + (float)24 && f < this.xPosition + (float)84 && f2 < this.yPosition + this.height && f2 > this.yPosition + (float)10 && bl;
            if (bl2) {
                this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
                CheatBreaker.getInstance().getWSNetHandler().sendPacket(new WSPacketFriendAcceptOrDeny(false, this.friendRequest.getPlayerId()));
                OverlayGui.getInstance().getFriendRequestsElement().getElements().add(this);
            }
        } else {
            boolean bl4;
            boolean bl5 = f > this.xPosition + (float)24 && f < this.xPosition + (float)52 && f2 < this.yPosition + this.height && f2 > this.yPosition + (float)10;
            boolean bl6 = bl4 = f > this.xPosition + (float)52 && f < this.xPosition + (float)84 && f2 < this.yPosition + this.height && f2 > this.yPosition + (float)10;
            if (bl5) {
                this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
                CheatBreaker.getInstance().getWSNetHandler().sendPacket(new WSPacketFriendAcceptOrDeny(true, this.friendRequest.getPlayerId()));
                OverlayGui.getInstance().getFriendRequestsElement().getElements().add(this);
            } else if (bl4) {
                this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
                CheatBreaker.getInstance().getWSNetHandler().sendPacket(new WSPacketFriendAcceptOrDeny(false, this.friendRequest.getPlayerId()));
                OverlayGui.getInstance().getFriendRequestsElement().getElements().add(this);
            }
        }
        return super.handleElementMouseClicked(f, f2, n, bl);
    }

    public FriendRequest getFriendRequest() {
        return this.friendRequest;
    }
}
