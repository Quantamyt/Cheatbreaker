package com.cheatbreaker.client.cosmetic.type.emote;

import com.cheatbreaker.client.cosmetic.Emote;
import com.cheatbreaker.client.ui.fading.ExponentialFade;
import com.cheatbreaker.client.ui.fading.MinMaxFade;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelPlayer;

public class NarutoRunEmote extends Emote {
    private final ExponentialFade startTransitionTime = new ExponentialFade(300L);
    private final MinMaxFade endTransitionTime = new MinMaxFade(500L);

    public NarutoRunEmote() {
        super("Naruto Run", new MinMaxFade(10000L));
        this.startTransitionTime.startAnimation();
    }

    public void playEmote(AbstractClientPlayer player, ModelPlayer model, float partialTicks) {
        model.bipedRightArm.rotateAngleX *= this.endTransitionTime.getFadeAmount();
        model.bipedLeftArm.rotateAngleX *= this.endTransitionTime.getFadeAmount();

        model.bipedRightArmwear.rotateAngleX *= this.endTransitionTime.getFadeAmount();
        model.bipedLeftArmwear.rotateAngleX *= this.endTransitionTime.getFadeAmount();

        if (this.startTransitionTime.isZeroOrLess()) {

            model.bipedRightArm.rotateAngleX = (float) Math.toRadians(90.0F * this.startTransitionTime.getFadeAmount());
            model.bipedLeftArm.rotateAngleX = (float) Math.toRadians(90.0F * this.startTransitionTime.getFadeAmount());

            model.bipedRightArmwear.rotateAngleX = (float) Math.toRadians(90.0F * this.startTransitionTime.getFadeAmount());
            model.bipedLeftArmwear.rotateAngleX = (float) Math.toRadians(90.0F * this.startTransitionTime.getFadeAmount());

        } else if (this.duration.getRemainingTime() <= this.endTransitionTime.getDuration()) {

            if (!this.endTransitionTime.isTimeNotAtZero()) {
                this.endTransitionTime.startAnimation();
            }

            model.bipedRightArm.rotateAngleX = Math.max((float) Math.toRadians(90.0F - 90.0F * this.endTransitionTime.getFadeAmount()), model.bipedRightArm.rotateAngleZ);
            model.bipedLeftArm.rotateAngleX = Math.min((float) Math.toRadians(-270.0F - 90.0F * this.endTransitionTime.getFadeAmount()), model.bipedRightArm.rotateAngleZ);

            model.bipedRightArmwear.rotateAngleX = Math.max((float) Math.toRadians(90.0F - 90.0F * this.endTransitionTime.getFadeAmount()), model.bipedRightArmwear.rotateAngleZ);
            model.bipedLeftArmwear.rotateAngleX = Math.min((float) Math.toRadians(-270.0F - 90.0F * this.endTransitionTime.getFadeAmount()), model.bipedRightArmwear.rotateAngleZ);

        } else {

            player.setSneaking(true);
            model.bipedRightArm.rotateAngleX = (float) Math.toRadians(90.0);
            model.bipedLeftArm.rotateAngleX = (float) Math.toRadians(90.0);

            model.bipedRightArmwear.rotateAngleX = (float) Math.toRadians(90.0);
            model.bipedLeftArmwear.rotateAngleX = (float) Math.toRadians(90.0);

        }

    }

    public void tickEmote(AbstractClientPlayer player, float partialTicks) {
    }
}
