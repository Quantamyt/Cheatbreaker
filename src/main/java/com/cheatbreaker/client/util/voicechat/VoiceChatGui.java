package com.cheatbreaker.client.util.voicechat;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.network.plugin.client.PacketVoiceChannelSwitch;
import com.cheatbreaker.client.network.plugin.client.PacketVoiceMute;
import com.cheatbreaker.client.ui.AbstractGui;
import com.cheatbreaker.client.ui.mainmenu.GradientTextButton;
import com.cheatbreaker.client.ui.util.RenderUtil;
import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;


import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class VoiceChatGui extends AbstractGui {
    private static final CheatBreaker cheatbreaker = CheatBreaker.getInstance();
    private List<GradientTextButton> voiceChannelButtons;
    private GradientTextButton joinChannelButton;
    private GradientTextButton deafenButton;
    private VoiceChannel currentVoiceChannel = null;
    private final ResourceLocation headphoneIcon = new ResourceLocation("client/icons/headphones.png");
    private final ResourceLocation speakerIcon = new ResourceLocation("client/icons/speaker.png");
    private final ResourceLocation speaker_muteIcon = new ResourceLocation("client/icons/speaker-mute.png");
    private final ResourceLocation mircophoneIcon = new ResourceLocation("client/icons/microphone-64.png");

    @Override
    public void initGui() {
        this.blurGui();
        if (cheatbreaker.getCbNetHandler().isVoiceChatEnabled() && cheatbreaker.getCbNetHandler().getVoiceChannels() != null) {
            this.currentVoiceChannel = cheatbreaker.getCbNetHandler().getCurrentVoiceChannel();
            boolean var1 = cheatbreaker.getCbNetHandler().getVoiceUsers().contains(this.mc.thePlayer.getGameProfile().getId());
            this.joinChannelButton = new GradientTextButton("Join Channel");
            this.deafenButton = new GradientTextButton(var1 ? "Un-deafen" : "Deafen");
            this.voiceChannelButtons = new ArrayList<>();
            float var2 = 16.0f;
            float var3 = this.getScaledWidth() / 8.0f;
            float var4 = this.getScaledHeight() / 2.0f - 8.0f - var2 * (float) cheatbreaker.getCbNetHandler().getVoiceChannels().size() / 2.0f;
            int var5 = 0;
            for (VoiceChannel var7 : cheatbreaker.getCbNetHandler().getVoiceChannels()) {
                GradientTextButton var8 = new GradientTextButton(var7.getName());
                this.voiceChannelButtons.add(var8);
                var8.setElementSize(var3, var4 + 12.0f + var2 * (float)var5, 110.0f, 12.0f);
                if (this.currentVoiceChannel == var7) {
                    var8.buttonColor2();
                }
                ++var5;
            }
        }
    }

    @Override
    public void onGuiClosed() {
        this.mc.entityRenderer.stopUseShader();
    }

    @Override
    public void drawMenu(float var1, float var2) {
        this.renderBlur(this.getScaledWidth(), this.getScaledHeight());
        float var3 = this.getScaledWidth() / 8.0f;
        if (cheatbreaker.getCbNetHandler().isVoiceChatEnabled() && cheatbreaker.getCbNetHandler().getVoiceChannels() != null) {
            float var4 = 16.0f;
            float var5 = this.getScaledHeight() / 2.0f - 8.0f - var4 * (float) cheatbreaker.getCbNetHandler().getVoiceChannels().size() / 2.0f;
            VoiceChatGui.cheatbreaker.playBold18px.drawString("VOICE CHAT", var3, var5 - 4.0f, -1);
            this.deafenButton.setElementSize(var3 + 60.0f, var5 - 4.0f, 50.0f, 12.0f);
            this.deafenButton.drawElementHover(var1, var2, true);
            this.voiceChannelButtons.forEach(buttons -> {
                if (this.getVoiceChannel(buttons.getString()) == cheatbreaker.getCbNetHandler().getCurrentVoiceChannel()) {
                    buttons.drawElement(var1, var2, true);
                    RenderUtil.renderIcon(new ResourceLocation("client/icons/microphone-64.png"), buttons.getXPosition() + 4.0f,
                            buttons.getYPosition() + 2.0f, 8.0f, 8.0f);
                } else if (this.currentVoiceChannel != null && this.currentVoiceChannel.getName().equals(buttons.getString())) {
                    buttons.drawElement(var1, var2, true);
                } else {
                    buttons.drawElementHover(var1, var2, true);
                }
            });
            if (this.currentVoiceChannel != null) {
                this.drawCurrentChannel(var1, var2, var3 + 130.0f, this.getScaledHeight() / 2.0f);
            }
        } else {
            float var4 = this.getScaledHeight() / 2.0f - 8.0f;
            VoiceChatGui.cheatbreaker.playBold18px.drawString("VOICE CHAT IS NOT SUPPORTED", var3, var4, -1);
        }
    }

    @Override
    protected void mouseClicked(float var1, float var2, int var3) {
        if (this.voiceChannelButtons != null) {
            Iterator<GradientTextButton> var4 = this.voiceChannelButtons.iterator();
            while (true) {
                VoiceChannel var6;
                GradientTextButton button;
                if (!var4.hasNext()) {
                    if (this.currentVoiceChannel != null) {
                        if (this.joinChannelButton.isMouseInside(var1, var2)) {
                            this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
                            cheatbreaker.getCbNetHandler().sendPacket(new PacketVoiceChannelSwitch(this.currentVoiceChannel.getUuid()));
                            for (GradientTextButton var52 : this.voiceChannelButtons) {
                                var52.buttonColor3();
                            }
                            for (GradientTextButton var52 : this.voiceChannelButtons) {
                                if (!var52.getString().equals(this.currentVoiceChannel.getName())) continue;
                                var52.buttonColor2();
                            }
                        }
                        if (this.deafenButton.isMouseInside(var1, var2)) {
                            UUID var9 = this.mc.thePlayer.getGameProfile().getId();
                            this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
                            cheatbreaker.getCbNetHandler().sendPacket(new PacketVoiceMute(var9));
                            if (!cheatbreaker.getCbNetHandler().getVoiceUsers().removeIf(var1x -> var1x.equals(var9))) {
                                cheatbreaker.getCbNetHandler().getVoiceUsers().add(var9);
                            }
                            this.deafenButton.setString(cheatbreaker.getCbNetHandler().getVoiceUsers().contains(this.mc.thePlayer.getGameProfile().getId()) ? "Un-deafen" : "Deafen");
                        }
                        this.handleMouseClick(var1, var2, this.getScaledWidth() / 8.0f + 130.0f, this.getScaledHeight() / 2.0f);
                    }
                    return;
                }
                button = var4.next();
                if (!button.isMouseInside(var1, var2) || this.currentVoiceChannel == (var6 = this.getVoiceChannel(button.getString()))) continue;
                for (GradientTextButton var8 : this.voiceChannelButtons) {
                    if (this.currentVoiceChannel == cheatbreaker.getCbNetHandler().getCurrentVoiceChannel() || !var8.getString().equals(this.currentVoiceChannel.getName())) continue;
                    var8.buttonColor3();
                }
                this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
                this.currentVoiceChannel = var6;
                if (this.currentVoiceChannel == cheatbreaker.getCbNetHandler().getCurrentVoiceChannel()) continue;
                button.buttonColor1();
            }
        }
    }

    @Override
    public void mouseMovedOrUp(float var1, float var2, int var3) {
    }

    private void drawCurrentChannel(float var1, float var2, float var3, float var4) {
        float var5 = 14.0f;
        float var6 = (float)this.currentVoiceChannel.getUsers().size() * var5;
        VoiceChatGui.cheatbreaker.playBold18px.drawString(this.currentVoiceChannel.getName(), var3, (var4 -= var6 / 2.0f) - 14.0f, -1);
        if (!this.isInServerSetChannel()) {
            this.joinChannelButton.setElementSize(var3 + 125.0f, var4 - 14.0f, 50.0f, 12.0f);
            this.joinChannelButton.drawElementHover(var1, var2, true);
        }
        Gui.drawRect(var3, var4, var3 + 175.0f, var4 + var6, -1626337264);
        int userIndex = 0;
        ArrayList<VoiceUser> listeners = Lists.newArrayList(this.currentVoiceChannel.getUsers());
        listeners.sort((var1x, var2x) -> {
            if (this.currentVoiceChannel.isListener(var1x.getPlayerUUID()) && !this.currentVoiceChannel.isListener(var2x.getPlayerUUID())) {
                return -1;
            }
            return !this.currentVoiceChannel.isListener(var1x.getPlayerUUID()) && this.currentVoiceChannel.isListener(var2x.getPlayerUUID()) ? 1 : 0;
        });
        for (VoiceUser users : listeners) {
            boolean listener = this.currentVoiceChannel.isListener(users.getPlayerUUID());
            boolean isUser = cheatbreaker.getCbNetHandler().getVoiceUsers().contains(users.getPlayerUUID());
            float var13 = var4 + (float)userIndex * var5;
            boolean var15 = var1 > var3 + 158.0f && var1 < var3 + 184.0f && var2 > var13 && var2 < var13 + var5;
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            if (!listener) {
                RenderUtil.renderIcon(this.headphoneIcon, var3 + 4.0f, var13 + 3.0f, 8.0f, 8.0f);
            } else {
                RenderUtil.renderIcon(this.mircophoneIcon, var3 + 4.0f, var13 + 3.0f, 8.0f, 8.0f);
            }
            float var14 = var3 + 10.0f;
            if (!users.getPlayerUUID().equals(this.mc.thePlayer.getUniqueID())) {
                if (isUser) {
                    GL11.glColor4f(1.0f, 0.1f, 0.1f, var15 ? 1.0f : 0.6f);
                    RenderUtil.renderIcon(this.speaker_muteIcon, var3 + 162.0f, var13 + 3.0f, 8.0f, 8.0f);
                } else {
                    GL11.glColor4f(1.0f, 1.0f, 1.0f, var15 ? 1.0f : 0.6f);
                    RenderUtil.renderIcon(this.speakerIcon, var3 + 162.0f, var13 + 3.0f, 8.0f, 8.0f);
                }
            }
            VoiceChatGui.cheatbreaker.ubuntuMedium16px.drawString(users.getPlayerName().toUpperCase(), var14 + 6.0f, var13 + 2.0f, listener ? -1 : 0x6FFFFFFF);
            ++userIndex;
        }
    }

    private void handleMouseClick(float var1, float var2, float var3, float var4) {
        float var5 = 14.0f;
        float var6 = (float)this.currentVoiceChannel.getUsers().size() * var5;
        var4 -= var6 / 2.0f;
        int var7 = 0;
        for (VoiceUser listener : this.currentVoiceChannel.getUsers()) {
            float var10 = var4 + (float)var7 * var5;
            boolean var11 = var1 > var3 + 158.0f && var1 < var3 + 184.0f && var2 > var10 && var2 < var10 + var5;
            if (!listener.getPlayerUUID().equals(this.mc.thePlayer.getUniqueID()) && var11) {
                this.mc.getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
                cheatbreaker.getCbNetHandler().sendPacket(new PacketVoiceMute(listener.getPlayerUUID()));
                if (!cheatbreaker.getCbNetHandler().getVoiceUsers().removeIf(var1x -> var1x.equals(listener.getPlayerUUID()))) {
                    cheatbreaker.getCbNetHandler().getVoiceUsers().add(listener.getPlayerUUID());
                }
            }
            ++var7;
        }
    }

    @Override
    public void keyTyped(char var1, int var2) {
        super.keyTyped(var1, var2);
        if (var2 == 25 && blurColor.isOver()) {
            if ((Boolean) VoiceChatGui.cheatbreaker.getGlobalSettings().guiBlur.getValue()) {
                this.mc.entityRenderer.stopUseShader();
            }
            this.mc.displayGuiScreen(null);
            this.mc.setIngameFocus();
        }
    }

    private VoiceChannel getVoiceChannel(String name) {
        VoiceChannel voiceChannel;
        Iterator<VoiceChannel> channelIterator = cheatbreaker.getCbNetHandler().getVoiceChannels().iterator();
        do {
            if (channelIterator.hasNext()) continue;
            return null;
        } while (!(voiceChannel = channelIterator.next()).getName().equals(name));
        return voiceChannel;
    }

    private boolean isInServerSetChannel() {
        return this.currentVoiceChannel == cheatbreaker.getCbNetHandler().getCurrentVoiceChannel();
    }
}
