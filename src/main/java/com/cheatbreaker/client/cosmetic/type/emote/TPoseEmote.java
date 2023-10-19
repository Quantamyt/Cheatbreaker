package com.cheatbreaker.client.cosmetic.type.emote;

import com.cheatbreaker.client.cosmetic.Emote;
import com.cheatbreaker.client.ui.fading.ExponentialFade;
import com.cheatbreaker.client.ui.fading.MinMaxFade;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;

public class TPoseEmote extends Emote {
    private final ExponentialFade startTransitionTime = new ExponentialFade(600L);
    private final MinMaxFade endTransitionTime = new MinMaxFade(600L);

    public TPoseEmote() {
        super("T-Pose", new MinMaxFade(5000L));
        this.startTransitionTime.startAnimation();
    }

    public void playEmote(AbstractClientPlayer player, ModelBiped model, float partialTicks) {
        model.bipedRightArm.rotateAngleX *= this.endTransitionTime.getFadeAmount();
        model.bipedLeftArm.rotateAngleX *= this.endTransitionTime.getFadeAmount();
        if (this.startTransitionTime.isZeroOrLess()) {
            model.bipedRightArm.rotateAngleZ = (float)Math.toRadians(90.0F * this.startTransitionTime.getFadeAmount());
            model.bipedLeftArm.rotateAngleZ = (float)Math.toRadians(-90.0F * this.startTransitionTime.getFadeAmount());
        } else if (this.duration.IlIlllIIIIllIllllIllIIlIl() <= this.endTransitionTime.getDuration()) {
            if (!this.endTransitionTime.isTimeNotAtZero()) {
                this.endTransitionTime.startAnimation();
            }

            model.bipedRightArm.rotateAngleZ = Math.max((float)Math.toRadians(90.0F - 90.0F * this.endTransitionTime.getFadeAmount()), model.bipedRightArm.rotateAngleZ);
            model.bipedLeftArm.rotateAngleZ = Math.min((float)Math.toRadians(-90.0F + 90.0F * this.endTransitionTime.getFadeAmount()), model.bipedRightArm.rotateAngleZ);
        } else {
            model.bipedRightArm.rotateAngleZ = (float)Math.toRadians(90.0);
            model.bipedLeftArm.rotateAngleZ = (float)Math.toRadians(-90.0);
        }

    }

    public void tickEmote(AbstractClientPlayer player, float partialTicks) {
    }
}
