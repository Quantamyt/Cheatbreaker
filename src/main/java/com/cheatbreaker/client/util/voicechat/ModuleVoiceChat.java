package com.cheatbreaker.client.util.voicechat;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.event.impl.render.GuiDrawEvent;
import com.cheatbreaker.client.event.impl.tick.TickEvent;
import com.cheatbreaker.client.ui.util.RenderUtil;
import com.cheatbreaker.client.util.voicechat.data.VoiceUser;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ModuleVoiceChat {
    Minecraft mc = Minecraft.getMinecraft();
    CheatBreaker cb = CheatBreaker.getInstance();
    private final ResourceLocation microphone = new ResourceLocation("client/icons/microphone-64.png");
    private final Map<VoiceUser, Object> users = new HashMap();
    private boolean talking;

    public ModuleVoiceChat() {
        CheatBreaker.getInstance().getEventBus().addEvent(GuiDrawEvent.class, this::onGuiDraw);
        CheatBreaker.getInstance().getEventBus().addEvent(TickEvent.class, this::onTick);
    }

    public void updateUser(UUID uUID) {
        VoiceUser voiceUser = this.cb.getCBNetHandler().getVoiceUser(uUID);
        if (voiceUser != null) {
            this.users.put(voiceUser, System.currentTimeMillis() + 250L);
        }
    }

    public void onGuiDraw(GuiDrawEvent event) {
        if (this.cb.getCBNetHandler().isVoiceChatEnabled() && this.cb.getCBNetHandler().getVoiceChannels() != null && (!this.users.isEmpty() || this.talking)) {
            float spacing = 20;
            float width = (float) event.getScaledResolution().getScaledWidth_double() - (float) 120;
            float[] height = new float[]{10};
            if (this.talking) {
                this.renderHead(this.mc.thePlayer.getName(),
                        this.mc.getSession().getPlayerID(), width, height[0], true);
                height[0] = height[0] + spacing;
            }
            this.users.forEach((voiceUser, l) -> {
                this.renderHead(voiceUser.getPlayerName(), voiceUser.getPlayerUUID().toString(), width, height[0], false);
                height[0] = height[0] + spacing;
            });
        }
    }

    private void renderHead(String name, String uuid, float width, float height, boolean self) {
        if (self) {
            RenderUtil.drawGradientRectWithOutline(width, height, width + (float) 110, height + (float) 18, -11493284, -10176146, -11164318);
        } else {
            RenderUtil.drawGradientRectWithOutline(width, height, width + (float) 110, height + (float) 18, -1356454362, -1355664846, -1356191190);
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        ResourceLocation resourceLocation = CheatBreaker.getInstance().getHeadIcon(name);
        RenderUtil.renderIcon(resourceLocation, 7.0F, width + 2.0f, height + 2.0f);
        this.cb.playRegular16px.drawString(name, width + (float) 22, height + (float) 4, -1);
    }

    public void onTick(TickEvent tickEvent) {
        if (!this.users.isEmpty()) {
            ArrayList<VoiceUser> arrayList = new ArrayList<>();
            for (Map.Entry<VoiceUser, Object> entry : this.users.entrySet()) {
                if (System.currentTimeMillis() - (Long) entry.getValue() < 0L) continue;
                arrayList.add(entry.getKey());
            }
            arrayList.forEach(voiceUser -> {
                Long cfr_ignored_0 = (Long) this.users.remove(voiceUser);
            });
        }
//        if (!this.cb.getCBNetHandler().isVoiceChatEnabled() && this.cb.getCBNetHandler().getVoiceChannels() != null) {
//            return;
//        }
        /*if (this.talking && !this.mc.inGameHasFocus) {
            this.talking = false;
            try {
                Message.e(false);
            } catch (UnsatisfiedLinkError unsatisfiedLinkError) {

            }
            CheatBreaker.getInstance().getAudioManager().sendSound("voice_up");
        }
        if (!this.talking && this.mc.inGameHasFocus && this.isButtonDown(this.cb.getGlobalSettings().keyBindVoicePushToTalk.getKeyCode())) {
            this.talking = true;
            try {
                Message.e(true);
            } catch (UnsatisfiedLinkError unsatisfiedLinkError) {

            }
            CheatBreaker.getInstance().getAudioManager().sendSound("voice_down");
        } else if (this.talking && this.mc.inGameHasFocus &&
                !this.isButtonDown(this.cb.getGlobalSettings().keyBindVoicePushToTalk.getKeyCode())) {
            this.talking = false;
            try {
                Message.e(false);
            } catch (UnsatisfiedLinkError unsatisfiedLinkError) {

            }
            CheatBreaker.getInstance().getAudioManager().sendSound("voice_up");
        }*/
    }

    private boolean isButtonDown(int n) {
        return n != 0 && (n < 0 ? Mouse.isButtonDown(n + 100) : Keyboard.isKeyDown(n));
    }
}
