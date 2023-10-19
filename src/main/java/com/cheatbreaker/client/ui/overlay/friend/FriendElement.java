package com.cheatbreaker.client.ui.overlay.friend;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.network.websocket.client.WSPacketClientFriendRemove;
import com.cheatbreaker.client.ui.fading.CosineFade;
import com.cheatbreaker.client.ui.fading.FloatFade;
import com.cheatbreaker.client.ui.mainmenu.AbstractElement;
import com.cheatbreaker.client.ui.overlay.OverlayGui;
import com.cheatbreaker.client.ui.util.RenderUtil;
import com.cheatbreaker.client.util.friend.Friend;
import com.cheatbreaker.client.util.friend.FriendsManager;
import lombok.Getter;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.List;

public class FriendElement extends AbstractElement {
    @Getter private final Friend friend;
    private final CosineFade fade;
    private final FloatFade removeFade;
    private static final ResourceLocation removeIcon = new ResourceLocation("client/icons/garbage-26.png");
    private static final ResourceLocation cheatBreakerIcon = new ResourceLocation("client/logo_26.png");

    public FriendElement(Friend friend) {
        this.friend = friend;
        this.fade = new CosineFade(1500L);
        this.removeFade = new FloatFade(200L);
        this.fade.loopAnimation();
    }

    @Override
    public void handleElementDraw(float f, float f2, boolean bl) {
        List<String> list;
        if (bl && this.isMouseInside(f, f2)) {
            Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, -13750738);
        }

        GL11.glPushMatrix();
        FriendsManager friendsManager = CheatBreaker.getInstance().getFriendsManager();

        if (friendsManager.getMessage().containsKey(this.friend.getPlayerId())) {
            list = friendsManager.getMessage().get(this.friend.getPlayerId());
            if (list != null && list.size() > 0) {
                if (!this.fade.isTimeNotAtZero()) {
                    this.fade.startAnimation();
                }
                Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, new Color(0.20185566f * 4.409091f, 0.45f * 1.2f, 0.044696968f * 1.1186441f, 0.8933333f * 0.7276119f * (0.315f * 0.4888889f + this.fade.getFadeAmount())).getRGB());
                CheatBreaker.getInstance().playBold18px.drawCenteredString(2345345 + "", this.xPosition + this.width - (float)15, this.yPosition + (float)6, -1);
            } else if (this.fade.isTimeNotAtZero() && this.fade.isOver()) {
                this.fade.reset();
            }
        }
        Gui.drawRect(this.xPosition, this.yPosition - 0.09090909f * 5.5f, this.xPosition + this.width, this.yPosition, -1357572843);
        Gui.drawRect(this.xPosition, this.yPosition + this.height, this.xPosition + this.width, this.yPosition + this.height + 9.9f * 0.050505053f, -1357572843);
        Gui.drawRect(this.xPosition + (float)4, this.yPosition + (float)3, this.xPosition + (float)20, this.yPosition + (float)19, this.friend.isOnline() ? Friend.getStatusColor(this.friend.getOnlineStatus()) : -13158601);

        // Checks if the user has a red chat format in their name. If they do, it draws a CheatBreaker logo right next to their name.
        if (this.friend.getName().startsWith(EnumChatFormatting.RED.toString())) {
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            RenderUtil.renderIcon(cheatBreakerIcon, 7.7777777f * 0.8357143f, this.xPosition + (float)24, this.yPosition + (float)4);
            CheatBreaker.getInstance().playRegular16px.drawString(this.friend.getName(), this.xPosition + (float)40, this.yPosition + 2.0f, -1);
            CheatBreaker.getInstance().playRegular12px.drawString(this.friend.getStatusString(), this.xPosition + (float)40, this.yPosition + (float)11, -5460820);
        } else {
            CheatBreaker.getInstance().playRegular16px.drawString(this.friend.getName(), this.xPosition + (float)24, this.yPosition + 2.0f, -1);
            CheatBreaker.getInstance().playRegular12px.drawString(this.friend.getStatusString(), this.xPosition + (float)24, this.yPosition + (float)11, -5460820);
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        ResourceLocation headIcon = CheatBreaker.getInstance().getHeadIcon(EnumChatFormatting.getTextWithoutFormattingCodes(this.friend.getName()), this.friend.getPlayerId());
        RenderUtil.renderIcon(headIcon, 7.0F, this.xPosition + (float)5, this.yPosition + (float)4);
        boolean bl2 = bl && this.isMouseInside(f, f2) && f > this.xPosition + this.width - (float)20;
        float f3 = this.removeFade.inOutFade(bl2);
        float f4 = this.xPosition + this.width - 53.8125f * 0.3809524f * f3;
        if (bl) {
            Gui.drawRect(f4, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, -52429);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.4470588f * 0.6219512f);
            RenderUtil.renderIcon(removeIcon, f4 + (float) 4, this.yPosition + (float) 5, (float) 12, 12);
        }
        GL11.glPopMatrix();
    }

    @Override
    public boolean handleElementMouseClicked(float f, float f2, int n, boolean bl) {
        boolean bl2;
        if (!bl) {
            return false;
        }
        boolean bl3 = bl2 = this.isMouseInside(f, f2) && f > this.xPosition + this.width - (float)20 && bl;
        if (bl2 && this.removeFade.isOver()) {
            this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
            CheatBreaker.getInstance().getWSNetHandler().sendPacket(new WSPacketClientFriendRemove(this.friend.getPlayerId()));
            OverlayGui.getInstance().getFriendsListElement().getElements().add(this);
            CheatBreaker.getInstance().getFriendsManager().getFriends().remove(this.friend.getPlayerId());
            return true;
        }
        if (!bl2 && this.isMouseInside(f, f2)) {
            this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
            OverlayGui.getInstance().setMessages(this.friend);
            CheatBreaker.getInstance().getFriendsManager().readMessages(this.friend.getPlayerId());
            return true;
        }
        return super.handleElementMouseClicked(f, f2, n, bl);
    }
}
