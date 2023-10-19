package com.cheatbreaker.client.cosmetic;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.cosmetic.data.AnimationStage;
import com.cheatbreaker.client.cosmetic.type.emote.*;
import com.cheatbreaker.client.event.impl.ClickStateEvent;
import com.cheatbreaker.client.event.impl.KeyboardEvent;
import com.cheatbreaker.client.event.impl.PlayerAnimationEvent;
import com.cheatbreaker.client.event.impl.TickEvent;
import com.cheatbreaker.client.network.websocket.shared.WSPacketEmote;
import com.cheatbreaker.client.ui.cosmetic.EmoteGUI;
import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.entity.player.EntityPlayer;

import java.util.*;

@Getter
public class EmoteManager {
    public static final BiMap emotes = ImmutableBiMap.builder()
            .put(0, WaveEmote.class)
            .put(1, HandsUpEmote.class)
            .put(2, FlossEmote.class)
            .put(3, DabEmote.class)
            .put(4, TPoseEmote.class)
            .put(5, ShrugEmote.class)
            .put(6, FacepalmEmote.class)
            .put(7, NarutoRunEmote.class)
            .put(8, SuperFacepalmEmote.class).build();

    private final Map<UUID, Emote> activeEmotes = new HashMap<>();
    private List<Integer> ownedEmotes = new ArrayList<>();

    @Setter private boolean doingEmote;
    @Setter private boolean sendingEmote;

    public EmoteManager() {
        ownedEmotes.addAll(emotes.keySet());
        CheatBreaker.getInstance().getEventBus().addEvent(TickEvent.class, this::onTick);
        CheatBreaker.getInstance().getEventBus().addEvent(PlayerAnimationEvent.class, this::onPlayerAnimation);
        CheatBreaker.getInstance().getEventBus().addEvent(ClickStateEvent.class, (event) -> {
            if (event.getMouseButton() == 0 && event.isState()) {
                this.stopEmote(Minecraft.getMinecraft().thePlayer);
            }

        });
        CheatBreaker.getInstance().getEventBus().addEvent(KeyboardEvent.class, (event) -> {
            if (Minecraft.getMinecraft().currentScreen == null && !this.ownedEmotes.isEmpty() && event.getPressed() == CheatBreaker.getInstance().getGlobalSettings().keyBindEmote.getKeyCode()) {
                Minecraft.getMinecraft().displayGuiScreen(new EmoteGUI(event.getPressed()));
            }

        });
    }

    public void playEmote(Emote emote) {
        if (this.hasEmote(emote)) {
            if (this.doingEmote) {
                this.doingEmote = false;
                this.sendingEmote = true;
            }

            this.stopEmote(Minecraft.getMinecraft().thePlayer);
            int emoteId = (Integer)emotes.inverse().get(emote.getClass());
            CheatBreaker.getInstance().getWSNetHandler().sendPacket(
                    new WSPacketEmote(Minecraft.getMinecraft().thePlayer.getUniqueID(), emoteId));
        }
    }

    private boolean hasEmote(Emote var1) {
        return ownedEmotes.contains(emotes.inverse().get(var1));
    }

    public Emote getEmote(int var1) {
        if (!emotes.containsKey(var1)) {
            return null;
        } else {
            try {
                return (Emote)((Class)emotes.get(var1)).newInstance();
            } catch (Exception var3) {
                var3.printStackTrace();
                return null;
            }
        }
    }

    public void playEmote(AbstractClientPlayer player, Emote var2) {
        if (player.getUniqueID().equals(Minecraft.getMinecraft().thePlayer.getUniqueID())) {
            if (Minecraft.getMinecraft().gameSettings.thirdPersonView == 0 || this.sendingEmote) {
                Minecraft.getMinecraft().gameSettings.thirdPersonView = 1;
                this.doingEmote = true;
            }

            this.sendingEmote = false;
        }
        this.activeEmotes.putIfAbsent(player.getUniqueID(), var2);
    }

    public void stopEmote(AbstractClientPlayer var1) {
        if (this.activeEmotes.containsKey(var1.getUniqueID())) {
            Emote var2 = this.activeEmotes.get(var1.getUniqueID());
            var2.endEmote(var1);
            this.activeEmotes.remove(var1.getUniqueID());
        }
    }

    private void onTick(TickEvent event) {
        if (!this.activeEmotes.isEmpty()) {
            ArrayList<UUID> list = new ArrayList<>();
            this.activeEmotes.forEach((uuid, emote) -> {
                EntityPlayer var3 = Minecraft.getMinecraft().theWorld.func_152378_a(uuid);
                if (emote.isEmoteOver()) {
                    emote.endEmote((AbstractClientPlayer)var3);
                    list.add(uuid);
                }

            });
            list.forEach(this.activeEmotes::remove);
        }
    }

    private void onPlayerAnimation(PlayerAnimationEvent event) {
        Emote activeEmotes = this.activeEmotes.get(event.getPlayer().getUniqueID());
        if (activeEmotes != null) {
            if (event.getStage() == AnimationStage.START) {
                activeEmotes.playEmote(event.getPlayer(), event.getModel(), event.getPartialTicks());
            } else {
                activeEmotes.tickEmote(event.getPlayer(), event.getPartialTicks());
            }
        }
    }

    public List<Integer> getEmotes() {
        return this.ownedEmotes;
    }

    public void setEmotes(List<Integer> emotes) {
        this.ownedEmotes = emotes;
    }
}
