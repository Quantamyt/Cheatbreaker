package com.cheatbreaker.client.cosmetic;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.cosmetic.data.AnimationStage;
import com.cheatbreaker.client.cosmetic.type.emote.*;
import com.cheatbreaker.client.event.impl.keyboard.KeyboardEvent;
import com.cheatbreaker.client.event.impl.mouse.ClickStateEvent;
import com.cheatbreaker.client.event.impl.render.PlayerAnimationEvent;
import com.cheatbreaker.client.event.impl.tick.TickEvent;
import com.cheatbreaker.client.network.websocket.shared.WSPacketEmote;
import com.cheatbreaker.client.ui.cosmetic.EmoteGUI;
import com.google.common.collect.ImmutableBiMap;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.input.Keyboard;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Getter
@Setter
public class CosmeticManager {

    private final List<Cosmetic> capes = new ArrayList<>();
    private final List<Cosmetic> wings = new ArrayList<>();
    private final List<Integer> emotes = new ArrayList<>();

    private final Map<UUID, Emote> activeEmotes = new ConcurrentHashMap<>();
    private final Map<UUID, ModelPlayer> models = new ConcurrentHashMap<>();

    private List<Integer> ownedEmotes = new ArrayList<>();

    private boolean doingEmote;
    private boolean sendingEmote;
    private Emote currentEmote;

    public static final ImmutableBiMap<Object, Object> CLIENT_EMOTES = ImmutableBiMap.builder()
            .put(0, WaveEmote.class)
            .put(1, HandsUpEmote.class)
            .put(2, FlossEmote.class)
            .put(3, DabEmote.class)
            .put(4, TPoseEmote.class)
            .put(5, ShrugEmote.class)
            .put(6, FacepalmEmote.class)
            .put(7, NarutoRunEmote.class)
            .put(8, SuperFacepalmEmote.class)
            .build();

    public CosmeticManager() {
        ownedEmotes.addAll(new ArrayList<>(emotes));
        CheatBreaker.getInstance().logger.info(CheatBreaker.getInstance().loggerPrefix + "Created Cosmetic Manager");

        CheatBreaker.getInstance().getEventBus().addEvent(TickEvent.class, this::onTick);
        CheatBreaker.getInstance().getEventBus().addEvent(PlayerAnimationEvent.class, this::onPlayerAnimation);

        CheatBreaker.getInstance().getEventBus().addEvent(ClickStateEvent.class, (event) -> {
            if (event.getMouseButton() == 0 && event.isState()) this.stopEmote(Minecraft.getMinecraft().thePlayer);
        });

        CheatBreaker.getInstance().getEventBus().addEvent(KeyboardEvent.class, (event) -> {
            if (Minecraft.getMinecraft().currentScreen == null && !this.ownedEmotes.isEmpty()
                    && event.getPressed() == Keyboard.KEY_B) {
                Minecraft.getMinecraft().displayGuiScreen(new EmoteGUI(event.getPressed()));
            }
        });
    }

    public List<Cosmetic> getFullCosmeticList() {
        ArrayList<Cosmetic> cosmetics = new ArrayList<>();

        // Adds the wings first so that they are at the front of the list always.
        for (Cosmetic wing : this.wings) {
            if (!wing.getPlayerId().equals(Minecraft.getMinecraft().getSession().getPlayerID())) continue;
            cosmetics.add(wing);
        }

        for (Cosmetic cape : this.capes) {
            if (!cape.getPlayerId().equals(Minecraft.getMinecraft().getSession().getPlayerID())) continue;
            cosmetics.add(cape);
        }

        return cosmetics;
    }

    public Cosmetic getActiveCape(UUID uuid) {
        for (Cosmetic cape : this.getCapes()) {
            if (cape.isEquipped() && uuid.toString().equals(cape.getPlayerId())) {
                return cape;
            }
        }
        return null;
    }

    public Cosmetic getActiveWings(UUID uuid) {
        for (Cosmetic wing : this.getWings()) {
            if (wing.isEquipped() && uuid.toString().equals(wing.getPlayerId())) {
                return wing;
            }
        }
        return null;
    }

    public void clearCosmetics(String uuid) {
        this.getCapes().removeIf(cape -> cape.getPlayerId().equals(uuid));
        this.getWings().removeIf(wings -> wings.getPlayerId().equals(uuid));
    }

    public void playEmote(Emote emote) {
        if (this.hasEmote(emote)) {
            if (this.doingEmote) {
                this.doingEmote = false;
                this.sendingEmote = true;
            }

            currentEmote = emote;
            this.stopEmote(Minecraft.getMinecraft().thePlayer);
            int emoteId = (Integer) CLIENT_EMOTES.inverse().get(emote.getClass());
            CheatBreaker.getInstance().getWsNetHandler().sendPacket(
                    new WSPacketEmote(Minecraft.getMinecraft().thePlayer.getUniqueID(), emoteId));
        }
    }

    private boolean hasEmote(Emote var1) {
        return this.emotes.contains(CLIENT_EMOTES.inverse().get(var1));
    }

    public Emote getEmoteById(int var1) {
        if (!CLIENT_EMOTES.containsKey(var1)) {
            return null;
        } else {
            try {
                return (Emote) ((Class) CLIENT_EMOTES.get(var1)).newInstance();
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

        currentEmote = var2;
        this.activeEmotes.putIfAbsent(player.getUniqueID(), var2);
    }

    public void stopEmote(AbstractClientPlayer var1) {
        if (this.activeEmotes.containsKey(var1.getUniqueID())) {
            currentEmote = null;
            Emote var2 = this.activeEmotes.get(var1.getUniqueID());
            var2.endEmote(var1);
            this.activeEmotes.remove(var1.getUniqueID());
        }
    }

    private void onTick(TickEvent event) {
        if (!this.activeEmotes.isEmpty()) {
            ArrayList<UUID> list = new ArrayList<>();
            this.activeEmotes.forEach((uuid, emote) -> {
                if (Minecraft.getMinecraft().theWorld != null) {
                    EntityPlayer var3 = Minecraft.getMinecraft().theWorld.getPlayerEntityByUUID(uuid);
                    if (emote.isEmoteOver()) {
                        currentEmote = null;
                        activeEmotes.remove(var3.getUniqueID());
                        emote.endEmote((AbstractClientPlayer) var3);
                        ModelPlayer modelPlayer = this.getModels().get(uuid);
                        if (modelPlayer == null || modelPlayer.bipedCape == null)
                            return;

                        modelPlayer.bipedCape.rotateAngleZ = 0;
                        this.getModels().remove(uuid);
                        list.add(uuid);
                    }
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

    public List<Integer> getOwnedEmotes() {
        return ownedEmotes;
    }

}
