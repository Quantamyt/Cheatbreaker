package com.cheatbreaker.client.cosmetic.type.emote;

import com.cheatbreaker.client.cosmetic.Emote;
import com.cheatbreaker.client.ui.fading.CosineFade;
import com.cheatbreaker.client.ui.fading.FloatFade;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;

public class WaveEmote extends Emote {
    private final FloatFade name = new FloatFade(250L);
    private final FloatFade resourceLoc = new FloatFade(250L);
    private final CosineFade waveTransitionTime = new CosineFade(500L);

    public WaveEmote() {
        super("Wave", new FloatFade(2000L));
        this.name.startAnimation();
    }

    public void tickEmote(AbstractClientPlayer player, float partialTicks) {
    }

    public void playEmote(AbstractClientPlayer player, ModelBiped model, float partialTicks) {
        float var4 = 1.0F;
        float var5 = 0.5F;
        if (this.name.getDuration() > this.duration.llIIllIlIlllllIlIllIIlIll()) {
            var4 = this.name.getFadeAmount();
        } else if (this.duration.IlIlllIIIIllIllllIllIIlIl() <= this.resourceLoc.getDuration()) {
            if (!this.resourceLoc.isTimeNotAtZero()) {
                this.resourceLoc.startAnimation();
            }

            var4 = 1.0F - this.resourceLoc.getFadeAmount();
        } else {
            if (!this.waveTransitionTime.isTimeNotAtZero()) {
                this.waveTransitionTime.startAnimationFromStartOrEnd(125.0F);
                this.waveTransitionTime.loopAnimation();
            }

            var5 = this.waveTransitionTime.getFadeAmount();
        }

        model.bipedLeftArm.rotateAngleX = (float)Math.toRadians(-150.0F * var4);
        model.bipedLeftArm.rotateAngleZ = (float)Math.toRadians(40.0F * var5 - 20.0F);
    }
}

