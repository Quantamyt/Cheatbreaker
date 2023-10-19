package com.cheatbreaker.client.cosmetic.type.emote;

import com.cheatbreaker.client.cosmetic.Emote;
import com.cheatbreaker.client.ui.fading.CosineFade;
import com.cheatbreaker.client.ui.fading.ExponentialFade;
import com.cheatbreaker.client.ui.fading.FloatFade;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;

public class SuperFacepalmEmote extends Emote {
    private final ExponentialFade startTransitionTime = new ExponentialFade(1000L);
    private final ExponentialFade endTransitionTime = new ExponentialFade(2000L);
    private final CosineFade headShakeTime = new CosineFade(100L);
    private final float headXRotationAngle = (float)Math.toRadians(45.0);
    private final float rightArmYRotationAngle = (float)Math.toRadians(-30.0);
    private final float rightArmXRotationAngle = (float)Math.toRadians(-100.0);

    public SuperFacepalmEmote() {
        super("Super Facepalm", new FloatFade(20000L));
    }

    public void playEmote(AbstractClientPlayer player, ModelBiped model, float partialTicks) {
        float var4 = this.startTransitionTime.getFadeAmount();
        if (!this.startTransitionTime.isTimeNotAtZero() && this.duration.llIIllIlIlllllIlIllIIlIll() >= 150L) {
            this.startTransitionTime.startAnimation();
        }

        if (this.startTransitionTime.isTimeNotAtZero()) {
            if (this.startTransitionTime.isOver() && !this.headShakeTime.isZeroOrLess() && !this.endTransitionTime.isTimeNotAtZero()) {
                this.headShakeTime.startAnimation();
            }

            float var5 = model.bipedHead.rotateAngleX;
            float var6 = model.bipedHead.rotateAngleY;
            model.bipedHead.rotateAngleZ = -((float)Math.toRadians(10.0F * this.headShakeTime.getFadeAmount()));
            model.bipedHeadwear.rotateAngleZ = -((float)Math.toRadians(10.0F * this.headShakeTime.getFadeAmount()));
            model.bipedHead.rotateAngleY = (float)Math.toRadians(10.0) * var4 - (float)Math.toRadians(10.0F * this.headShakeTime.getFadeAmount());
            model.bipedHeadwear.rotateAngleY = (float)Math.toRadians(10.0) * var4 - (float)Math.toRadians(10.0F * this.headShakeTime.getFadeAmount());
            model.bipedHead.rotateAngleX = this.headXRotationAngle * var4;
            model.bipedHeadwear.rotateAngleX = this.headXRotationAngle * var4;
            model.bipedRightArm.rotateAngleY = this.rightArmYRotationAngle * var4 - (this.endTransitionTime.isTimeNotAtZero() ? 0.0F : (float)Math.toRadians(10.0F * this.headShakeTime.getFadeAmount()));
            model.bipedRightArm.rotateAngleX = this.rightArmXRotationAngle * var4;
            if (!this.endTransitionTime.isTimeNotAtZero() && this.duration.IlIlllIIIIllIllllIllIIlIl() <= this.endTransitionTime.getDuration()) {
                this.endTransitionTime.startAnimation();
            }

            if (this.endTransitionTime.isTimeNotAtZero()) {
                var4 = this.endTransitionTime.getFadeAmount();
                model.bipedHead.rotateAngleY = var6 * var4;
                model.bipedHeadwear.rotateAngleY = var6 * var4;
                model.bipedHead.rotateAngleZ = 0.0F;
                model.bipedHeadwear.rotateAngleZ = 0.0F;
                model.bipedHead.rotateAngleX -= (this.headXRotationAngle - var5) * var4;
                model.bipedHeadwear.rotateAngleX -= (this.headXRotationAngle - var5) * var4;
                model.bipedRightArm.rotateAngleY -= this.rightArmYRotationAngle * var4;
                model.bipedRightArm.rotateAngleX -= this.rightArmXRotationAngle * var4;
            }

        }
    }

    public void tickEmote(AbstractClientPlayer player, float partialTicks) {
    }
}
