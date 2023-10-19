package com.cheatbreaker.client.cosmetic;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.ui.fading.AbstractFade;
import lombok.Getter;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.util.ResourceLocation;

@Getter
public abstract class Emote {
    protected final AbstractFade duration;
    private final String name;
    private final ResourceLocation resourceLocation;

    public Emote(String string, AbstractFade duration) {
        this.duration = duration;
        duration.startAnimation();
        this.name = string;
        this.resourceLocation = new ResourceLocation("client/emote/" + string.toLowerCase().replace("-", "").replace(" ", "") + ".png");
    }

    public abstract void playEmote(AbstractClientPlayer player, ModelBiped model, float partialTicks);

    public abstract void tickEmote(AbstractClientPlayer player, float partialTicks);

    public boolean isEmoteOver() {
        return this.duration.isOver();
    }

    protected void endEmote(AbstractClientPlayer abstractClientPlayer) {
        if (abstractClientPlayer == null) {
            return;
        }

        if (abstractClientPlayer.getUniqueID().equals(Minecraft.getMinecraft().getSession().getUniqueID())) {
            if (CheatBreaker.getInstance().getEmoteManager().isDoingEmote()) {
                Minecraft.getMinecraft().gameSettings.thirdPersonView = 0;
                CheatBreaker.getInstance().getEmoteManager().setDoingEmote(false);
                CheatBreaker.getInstance().getEmoteManager().setSendingEmote(false);
            }
        }
    }

    public ResourceLocation getResourceLocation() {
        return this.resourceLocation;
    }
}
