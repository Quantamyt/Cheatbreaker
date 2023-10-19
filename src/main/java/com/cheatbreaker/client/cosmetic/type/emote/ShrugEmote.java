package com.cheatbreaker.client.cosmetic.type.emote;

import com.cheatbreaker.client.cosmetic.Emote;
import com.cheatbreaker.client.ui.fading.CosineFade;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelPlayer;

public class ShrugEmote extends Emote {
    public ShrugEmote() {
        super("Shrug", new CosineFade(500L));
    }

    public void playEmote(AbstractClientPlayer player, ModelPlayer model, float partialTicks) {
        model.bipedRightArm.offsetY = -0.2F * this.duration.getFadeAmount();
        model.bipedLeftArm.offsetY = -0.2F * this.duration.getFadeAmount();

        model.bipedRightArmwear.offsetY = -0.2F * this.duration.getFadeAmount();
        model.bipedLeftArmwear.offsetY = -0.2F * this.duration.getFadeAmount();

        model.bipedHead.offsetY = 0.05F * this.duration.getFadeAmount();
        model.bipedHeadwear.offsetY = 0.05F * this.duration.getFadeAmount();
    }

    public void tickEmote(AbstractClientPlayer player, float partialTicks) {
    }
}
