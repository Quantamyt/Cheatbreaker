package com.cheatbreaker.client.cosmetic.type.emote;

import com.cheatbreaker.client.cosmetic.Emote;
import com.cheatbreaker.client.ui.fading.MinMaxFade;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;

public class InfiniteDabEmote extends Emote {
    private final MinMaxFade name = new MinMaxFade(250L);
    private final MinMaxFade resourceLoc = new MinMaxFade(250L);
    private boolean left = false;

    public InfiniteDabEmote() {
        super("Dab", new MinMaxFade(7500L));
        this.name.startAnimation();
        switchInt = 1000;
    }

    public void tickEmote(AbstractClientPlayer player, float partialTicks) {}

    private int switchInt = 10;

    public void playEmote(AbstractClientPlayer player, ModelBiped model, float partialTicks) {
        float var4 = 1.0F;
        if (this.name.getDuration() > this.duration.llIIllIlIlllllIlIllIIlIll()) {
            var4 = this.name.getFadeAmount();
        } else if (this.duration.IlIlllIIIIllIllllIllIIlIl() <= this.resourceLoc.getDuration()) {
            if (!this.resourceLoc.isTimeNotAtZero()) {
                this.resourceLoc.startAnimation();
            }

            var4 = 1.0F - this.resourceLoc.getFadeAmount();
        }

        if(switchInt == 0) {
            left = true;
        } else if(switchInt == 1000) {
            left = false;
        }

        if (left) {
            model.bipedRightArm.rotateAngleX = (float)Math.toRadians(90.0F * var4);
            model.bipedRightArm.rotateAngleY = (float)Math.toRadians(-35.0F * var4);
            model.bipedLeftArm.rotateAngleX = (float)Math.toRadians(-15.0F * var4);
            model.bipedLeftArm.rotateAngleY = (float)Math.toRadians(15.0F * var4);
            model.bipedLeftArm.rotateAngleZ = (float)Math.toRadians(110.0F * var4);
            float var5 = player.rotationPitch;
            float var6 = player.prevRenderYawOffset - player.rotationYaw;
            model.bipedHead.rotateAngleX = (float)Math.toRadians(-var5 * var4) + (float)Math.toRadians(-45.0F * var4 + var5);
            model.bipedHead.rotateAngleY = (float)Math.toRadians(var6 * var4) + (float)Math.toRadians(35.0F * var4 - var6);
            model.bipedHeadwear.rotateAngleX = (float)Math.toRadians(-var5 * var4) + (float)Math.toRadians(-45.0F * var4 + var5);
            model.bipedHeadwear.rotateAngleY = (float)Math.toRadians(var6 * var4) + (float)Math.toRadians(35.0F * var4 - var6);
            switchInt++;
        } else {
            model.bipedRightArm.rotateAngleX = (float)Math.toRadians(-90.0F * var4);
            model.bipedRightArm.rotateAngleY = (float)Math.toRadians(-35.0F * var4);
            model.bipedLeftArm.rotateAngleX = (float)Math.toRadians(15.0F * var4);
            model.bipedLeftArm.rotateAngleY = (float)Math.toRadians(15.0F * var4);
            model.bipedLeftArm.rotateAngleZ = (float)Math.toRadians(-110.0F * var4);
            float var5 = player.rotationPitch;
            float var6 = player.prevRenderYawOffset - player.rotationYaw;
            model.bipedHead.rotateAngleX = (float)Math.toRadians(-var5 * var4) + (float)Math.toRadians(45.0F * var4 + var5);
            model.bipedHead.rotateAngleY = (float)Math.toRadians(var6 * var4) + (float)Math.toRadians(35.0F * var4 - var6);
            model.bipedHeadwear.rotateAngleX = (float)Math.toRadians(-var5 * var4) + (float)Math.toRadians(45.0F * var4 + var5);
            model.bipedHeadwear.rotateAngleY = (float)Math.toRadians(var6 * var4) + (float)Math.toRadians(35.0F * var4 - var6);
            switchInt--;
        }
    }
}
