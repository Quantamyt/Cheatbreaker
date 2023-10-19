package com.cheatbreaker.client.cosmetic.type.emote;

import com.cheatbreaker.client.cosmetic.Emote;
import com.cheatbreaker.client.ui.fading.FloatFade;
import com.cheatbreaker.client.ui.fading.MinMaxFade;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;

public class HandsUpEmote extends Emote {
    private final MinMaxFade startTransitionTime = new MinMaxFade(250L);
    private final MinMaxFade endTransitionTime = new MinMaxFade(250L);

    public HandsUpEmote() {
        super("Hands Up", new FloatFade(2000L));
        this.startTransitionTime.startAnimation();
    }

    public void tickEmote(AbstractClientPlayer player, float partialTicks) {
    }

    public void playEmote(AbstractClientPlayer player, ModelBiped model, float partialTicks) {
        float var4 = 1.0F;
        if (this.startTransitionTime.getDuration() > this.duration.llIIllIlIlllllIlIllIIlIll()) {
            var4 = this.startTransitionTime.getFadeAmount();
        } else if (this.duration.IlIlllIIIIllIllllIllIIlIl() <= this.endTransitionTime.getDuration()) {
            if (!this.endTransitionTime.isTimeNotAtZero()) {
                this.endTransitionTime.startAnimation();
            }

            var4 = 1.0F - this.endTransitionTime.getFadeAmount();
        }

        model.bipedLeftArm.rotateAngleX = (float)Math.toRadians(-180.0F * var4);
        model.bipedRightArm.rotateAngleX = (float)Math.toRadians(-180.0F * var4);
        model.bipedRightArm.rotateAngleZ = (float)Math.toRadians(-15.0F * var4);
        model.bipedLeftArm.rotateAngleZ = (float)Math.toRadians(15.0F * var4);
    }
}

