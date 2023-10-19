package com.cheatbreaker.client.cosmetic;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.ui.fading.AbstractFade;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.util.ResourceLocation;

import java.util.UUID;

@Getter
public abstract class Emote {
    private final String name;
    protected final AbstractFade duration;
    private final ResourceLocation resourceLocation;

    public Emote(String name, AbstractFade duration) {
        this.duration = duration;
        duration.startAnimation();
        this.name = name;
        this.resourceLocation = new ResourceLocation("client/emote/" + name.toLowerCase().replace("-", "").replace(" ", "") + ".png");
    }

    public abstract void playEmote(AbstractClientPlayer player, ModelPlayer model, float partialTicks);

    public abstract void tickEmote(AbstractClientPlayer player, float partialTicks);

    protected void endEmote(AbstractClientPlayer abstractClientPlayer) {
        if (abstractClientPlayer == null) {
            return;
        }

        if (abstractClientPlayer.getUniqueID().equals(UUID.fromString(Minecraft.getMinecraft().getSession().getPlayerID()))) {
            if (CheatBreaker.getInstance().getCosmeticManager().isDoingEmote()) {
                Minecraft.getMinecraft().gameSettings.thirdPersonView = 0;
                CheatBreaker.getInstance().getCosmeticManager().setDoingEmote(false);
                CheatBreaker.getInstance().getCosmeticManager().setSendingEmote(false);
            }
        }
    }

    public boolean isEmoteOver() {
        return this.duration.isOver();
    }
}
