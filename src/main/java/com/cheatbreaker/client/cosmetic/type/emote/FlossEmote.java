package com.cheatbreaker.client.cosmetic.type.emote;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.cosmetic.Emote;
import com.cheatbreaker.client.ui.fading.CosineFade;
import com.cheatbreaker.client.ui.fading.ExponentialFade;
import com.cheatbreaker.client.ui.fading.FloatFade;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelPlayer;

public class FlossEmote extends Emote {
    private final ExponentialFade swapTransitionTime = new ExponentialFade(375L);
    private final CosineFade swingTransitionTime = new CosineFade(375L);
    private final CosineFade bodyRotationTransitionTime = new CosineFade(250L);
    boolean swap = false;
    private FlossStage flossStage;

    public FlossEmote() {
        super("Floss", new FloatFade(7500L));
        this.bodyRotationTransitionTime.startAnimation();
        this.flossStage = FlossStage.LEFT_TO_RIGHT;
    }

    @Override
    public void tickEmote(AbstractClientPlayer player, float partialTicks) {
    }

    @Override
    public void playEmote(AbstractClientPlayer player, ModelPlayer model, float partialTicks) {
        CheatBreaker.getInstance().getCosmeticManager().getModels().put(player.getUniqueID(), model);

        if (!this.swapTransitionTime.isTimeNotAtZero()) {
            if ((double) this.bodyRotationTransitionTime.getFadeAmount() >= 0.5) {
                this.swapTransitionTime.startAnimation();
                this.swingTransitionTime.startAnimation();
            }
        } else if (this.swapTransitionTime.isOver()) {
            this.swapTransitionTime.startAnimation();
            this.swingTransitionTime.startAnimation();
            this.flossStage = this.getFlossStage();
        }
        if (this.bodyRotationTransitionTime.isOver()) {
            this.swap = !this.swap;
            this.bodyRotationTransitionTime.startAnimation();
        }
        float f2 = this.swapTransitionTime.getFadeAmount();
        float f3 = this.swingTransitionTime.getFadeAmount();
        model.bipedRightArm.rotateAngleX = (float) Math.toRadians((float) (this.flossStage == FlossStage.RIGHT_TO_BACK ? 45 : -45) * f3);
        model.bipedLeftArm.rotateAngleX = (float) Math.toRadians((float) (this.flossStage == FlossStage.LEFT_TO_BACK ? 45 : -45) * f3);

        model.bipedRightArmwear.rotateAngleX = (float) Math.toRadians((float) (this.flossStage == FlossStage.RIGHT_TO_BACK ? 45 : -45) * f3);
        model.bipedLeftArmwear.rotateAngleX = (float) Math.toRadians((float) (this.flossStage == FlossStage.LEFT_TO_BACK ? 45 : -45) * f3);
        float f4 = 150.0f;
        float f5 = f4 / 2.0f;
        switch (this.flossStage) {

            case LEFT_TO_RIGHT:
                model.bipedRightArm.rotateAngleZ = (float) Math.toRadians(f4 * f2 - f5);
                model.bipedLeftArm.rotateAngleZ = (float) Math.toRadians(f4 * f2 - f5);

                model.bipedRightArmwear.rotateAngleZ = (float) Math.toRadians(f4 * f2 - f5);
                model.bipedLeftArmwear.rotateAngleZ = (float) Math.toRadians(f4 * f2 - f5);
                break;

            case RIGHT_TO_BACK:
                model.bipedRightArm.rotateAngleZ = (float) Math.toRadians(f5 - f5 * f3);
                model.bipedLeftArm.rotateAngleZ = (float) Math.toRadians(f5 - f5 * f3);

                model.bipedRightArmwear.rotateAngleZ = (float) Math.toRadians(f5 - f5 * f3);
                model.bipedLeftArmwear.rotateAngleZ = (float) Math.toRadians(f5 - f5 * f3);
                break;

            case RIGHT_TO_LEFT:
                model.bipedRightArm.rotateAngleZ = (float) Math.toRadians(-f4 * f2 + f5);
                model.bipedLeftArm.rotateAngleZ = (float) Math.toRadians(-f4 * f2 + f5);

                model.bipedRightArmwear.rotateAngleZ = (float) Math.toRadians(-f4 * f2 + f5);
                model.bipedLeftArmwear.rotateAngleZ = (float) Math.toRadians(-f4 * f2 + f5);
                break;

            case LEFT_TO_BACK:
                model.bipedRightArm.rotateAngleZ = (float) Math.toRadians(f5 * f3 - f5);
                model.bipedLeftArm.rotateAngleZ = (float) Math.toRadians(f5 * f3 - f5);

                model.bipedRightArmwear.rotateAngleZ = (float) Math.toRadians(f5 * f3 - f5);
                model.bipedLeftArmwear.rotateAngleZ = (float) Math.toRadians(f5 * f3 - f5);

        }

        f3 = this.bodyRotationTransitionTime.getFadeAmount();
        if (this.swap) {
            model.bipedBody.rotateAngleZ = (float) Math.toRadians(-15.0f * f3);
            model.bipedCape.rotateAngleZ = (float) Math.toRadians(15.0f * f3);
            model.bipedRightLeg.rotateAngleZ = (float) Math.toRadians(15.0f * f3);
            model.bipedLeftLeg.rotateAngleZ = (float) Math.toRadians(15.0f * f3);
            model.bipedLeftLeg.offsetX = 0.2f * f3;
            model.bipedRightLeg.offsetX = 0.2f * f3;

            model.bipedBodyWear.rotateAngleZ = (float) Math.toRadians(-15.0f * f3);
            model.bipedRightLegwear.rotateAngleZ = (float) Math.toRadians(15.0f * f3);
            model.bipedLeftLegwear.rotateAngleZ = (float) Math.toRadians(15.0f * f3);
            model.bipedLeftLegwear.offsetX = 0.2f * f3;
            model.bipedRightLegwear.offsetX = 0.2f * f3;
        } else {
            model.bipedBody.rotateAngleZ = (float) Math.toRadians(15.0f * f3);
            model.bipedCape.rotateAngleZ = (float) Math.toRadians(-15.0f * f3);
            model.bipedRightLeg.rotateAngleZ = (float) Math.toRadians(-15.0f * f3);
            model.bipedLeftLeg.rotateAngleZ = (float) Math.toRadians(-15.0f * f3);
            model.bipedLeftLeg.offsetX = -0.2f * f3;
            model.bipedRightLeg.offsetX = -0.2f * f3;

            model.bipedBodyWear.rotateAngleZ = (float) Math.toRadians(15.0f * f3);
            model.bipedRightLegwear.rotateAngleZ = (float) Math.toRadians(-15.0f * f3);
            model.bipedLeftLegwear.rotateAngleZ = (float) Math.toRadians(-15.0f * f3);
            model.bipedLeftLegwear.offsetX = -0.2f * f3;
            model.bipedRightLegwear.offsetX = -0.2f * f3;
        }
    }

    private FlossStage getFlossStage() {
        switch (this.flossStage) {
            default:
                return FlossStage.RIGHT_TO_BACK;
            case RIGHT_TO_BACK:
                return FlossStage.RIGHT_TO_LEFT;
            case RIGHT_TO_LEFT:
                return FlossStage.LEFT_TO_BACK;
            case LEFT_TO_BACK:
        }
        return FlossStage.LEFT_TO_RIGHT;
    }

    enum FlossStage {
        LEFT_TO_RIGHT, RIGHT_TO_LEFT, RIGHT_TO_BACK, LEFT_TO_BACK
    }
}

