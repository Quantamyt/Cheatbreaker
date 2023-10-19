package com.cheatbreaker.client.cosmetic.type.emote;

import com.cheatbreaker.client.cosmetic.Emote;
import com.cheatbreaker.client.ui.fading.CosineFade;
import com.cheatbreaker.client.ui.fading.ExponentialFade;
import com.cheatbreaker.client.ui.fading.FloatFade;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelPlayer;

public class FacepalmEmote extends Emote {
    private final ExponentialFade startTransitionTime = new ExponentialFade(150L);
    private final ExponentialFade endTransitionTime = new ExponentialFade(200L);
    private final CosineFade headShakeTime = new CosineFade(300L);
    private final float headXRotationAngle = (float) Math.toRadians(45.0);
    private final float rightArmYRotationAngle = (float) Math.toRadians(-30.0);
    private final float rightArmXRotationAngle = (float) Math.toRadians(-100.0);

    public FacepalmEmote() {
        super("Facepalm", new FloatFade(2000L));
    }

    public void playEmote(AbstractClientPlayer player, ModelPlayer model, float partialTicks) {
        float fadeAmount = this.startTransitionTime.getFadeAmount();
        if (!this.startTransitionTime.isTimeNotAtZero() && this.duration.llIIllIlIlllllIlIllIIlIll() >= 150L) {
            this.startTransitionTime.startAnimation();
        }

        if (this.startTransitionTime.isTimeNotAtZero()) {
            if (this.startTransitionTime.isOver() && !this.headShakeTime.isZeroOrLess() && !this.endTransitionTime.isTimeNotAtZero()) {
                this.headShakeTime.startAnimation();
            }

            float var5 = model.bipedHead.rotateAngleX;
            float var6 = model.bipedHead.rotateAngleY;

            model.bipedHead.rotateAngleZ = -((float) Math.toRadians(10.0F * this.headShakeTime.getFadeAmount()));
            model.bipedHeadwear.rotateAngleZ = -((float) Math.toRadians(10.0F * this.headShakeTime.getFadeAmount()));
            model.bipedHead.rotateAngleY = (float) Math.toRadians(10.0) * fadeAmount - (float) Math.toRadians(10.0F * this.headShakeTime.getFadeAmount());
            model.bipedHeadwear.rotateAngleY = (float) Math.toRadians(10.0) * fadeAmount - (float) Math.toRadians(10.0F * this.headShakeTime.getFadeAmount());
            model.bipedHead.rotateAngleX = this.headXRotationAngle * fadeAmount;
            model.bipedHeadwear.rotateAngleX = this.headXRotationAngle * fadeAmount;
            model.bipedRightArm.rotateAngleY = this.rightArmYRotationAngle * fadeAmount - (this.endTransitionTime.isTimeNotAtZero() ? 0.0F : (float) Math.toRadians(10.0F * this.headShakeTime.getFadeAmount()));
            model.bipedRightArm.rotateAngleX = this.rightArmXRotationAngle * fadeAmount;

            model.bipedRightArmwear.rotateAngleY = this.rightArmYRotationAngle * fadeAmount - (this.endTransitionTime.isTimeNotAtZero() ? 0.0F : (float) Math.toRadians(10.0F * this.headShakeTime.getFadeAmount()));
            model.bipedRightArmwear.rotateAngleX = this.rightArmXRotationAngle * fadeAmount;

            if (!this.endTransitionTime.isTimeNotAtZero() && this.duration.getRemainingTime() <= this.endTransitionTime.getDuration()) {
                this.endTransitionTime.startAnimation();
            }

            if (this.endTransitionTime.isTimeNotAtZero()) {
                fadeAmount = this.endTransitionTime.getFadeAmount();
                model.bipedHead.rotateAngleY = var6 * fadeAmount;
                model.bipedHeadwear.rotateAngleY = var6 * fadeAmount;
                model.bipedHead.rotateAngleZ = 0.0F;
                model.bipedHeadwear.rotateAngleZ = 0.0F;
                model.bipedHead.rotateAngleX -= (this.headXRotationAngle - var5) * fadeAmount;
                model.bipedHeadwear.rotateAngleX -= (this.headXRotationAngle - var5) * fadeAmount;

                model.bipedRightArm.rotateAngleY -= this.rightArmYRotationAngle * fadeAmount;
                model.bipedRightArm.rotateAngleX -= this.rightArmXRotationAngle * fadeAmount;

                model.bipedRightArmwear.rotateAngleY -= this.rightArmYRotationAngle * fadeAmount;
                model.bipedRightArmwear.rotateAngleX -= this.rightArmXRotationAngle * fadeAmount;
            }

        }
    }

    public void tickEmote(AbstractClientPlayer player, float partialTicks) {
    }
}
