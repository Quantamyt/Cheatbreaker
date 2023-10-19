package com.cheatbreaker.client.ui.mainmenu;

import com.cheatbreaker.client.ui.fading.FloatFade;
import com.cheatbreaker.client.ui.fading.MinMaxFade;
import com.cheatbreaker.client.ui.util.RenderUtil;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.concurrent.ThreadLocalRandom;

public final class LunarClientLogoElement extends AbstractElement {
    private final ResourceLocation origLunarLogo = new ResourceLocation("client/animatedlogo/128/logo_128_no_stars.png");
    private static final ResourceLocation[] logoLocations = new ResourceLocation[8];
    private final TwinklingLunarLogo[] logos = new TwinklingLunarLogo[8];
    private boolean someBoolean = true;
    private final float[] someIntArray = new float[8];
    private final boolean shadow;

    public LunarClientLogoElement() {
        this(true);
    }

    public LunarClientLogoElement(boolean shadow) {
        for (int frame = 1; frame <= 8; ++frame) {
            if (logoLocations[frame - 1] != null) continue;
            LunarClientLogoElement.logoLocations[frame - 1] = new ResourceLocation("client/animatedlogo/128/logo_128_star_" + frame + ".png");
        }
        this.updateLogo();
        this.shadow = shadow;
    }

    private void updateLogo() {
        for (int i = 1; i <= 8; ++i) {
            if (this.logos[i - 1] != null && !this.logos[i - 1].isZeroOrLess()) {
                this.someBoolean = false;
            }
            if (this.logos[i - 1] != null && this.logos[i - 1].isZeroOrLess()) continue;
            long l = ThreadLocalRandom.current().nextLong(4000L, 12000L);
            if (this.someBoolean) {
                this.someIntArray[i - 1] = Math.max(ThreadLocalRandom.current().nextFloat(), 0.8f);
            }
            this.logos[i - 1] = new TwinklingLunarLogo(this, l);
        }
    }

    @Override
    public void setElementSize(float f, float f2, float f3, float f4) {
        super.setElementSize(f, f2, f3, f4);
    }

    @Override
    public void handleElementUpdate() {
        this.updateLogo();
    }

    @Override
    protected void handleElementDraw(float f, float f2, boolean bl) {
        GL11.glPushMatrix();
        if (this.shadow) {
            GL11.glColor4f(0.0f, 0.0f, 0.0f, 0.2f);
            RenderUtil.renderIcon(this.origLunarLogo, this.xPosition + 1.0f, this.yPosition + 1.0f, this.width, this.height);
        }
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderUtil.renderIcon(this.origLunarLogo, this.xPosition, this.yPosition, this.width, this.height);
        for (int i = 0; i < 8; ++i) {
            TwinklingLunarLogo twinklingLunarLogo = this.logos[i];
            if (!twinklingLunarLogo.isTimeNotAtZero()) {
                twinklingLunarLogo.startAnimation();
            }
            GL11.glPushMatrix();
            if (!twinklingLunarLogo.isZeroOrLess()) {
                this.updateLogo();
            }
            float f3 = twinklingLunarLogo.getRunTimeFadeLength();
            if (twinklingLunarLogo.isRunTimeOver() && this.someBoolean) {
                this.someBoolean = false;
            }
            if (this.someBoolean) {
                f3 = Math.max(f3, this.someIntArray[i]);
            }
            if (this.shadow) {
                GL11.glColor4f(0.0f, 0.0f, 0.0f, f3 / 5.0f);
                RenderUtil.renderIcon(logoLocations[i], this.xPosition + 1.0f, this.yPosition + 1.0f, this.width, this.height);
            }
            GL11.glColor4f(1.0f, 1.0f, 1.0f, f3);
            RenderUtil.renderIcon(logoLocations[i], this.xPosition, this.yPosition, this.width, this.height);
            GL11.glPopMatrix();
        }
        GL11.glPopMatrix();
    }

    static class TwinklingLunarLogo extends FloatFade {
        private final MinMaxFade runTime;
        private final MinMaxFade runLength;
        final LunarClientLogoElement loopAnimation;

        private TwinklingLunarLogo(LunarClientLogoElement lunarLogoElement, long l) {
            super((long) ((float) l));
            this.loopAnimation = lunarLogoElement;
            this.runTime = new MinMaxFade(Math.min((long) Math.min((float) l * 0.2f, 3000.0f), 1500L));
            this.runLength = new MinMaxFade(Math.min((long) ((float) l * 0.4f), 5000L));
        }

        @Override
        public void startAnimation() {
            super.startAnimation();
            if (!this.runTime.isTimeNotAtZero()) {
                this.runTime.startAnimation();
            }
        }

        boolean isRunTimeOver() {
            return this.runTime.isOver();
        }

        float getRunTimeFadeLength() {
            if (!this.runTime.isTimeNotAtZero()) {
                this.runTime.startAnimation();
            }
            if (this.runTime.isZeroOrLess()) {
                return Math.max(this.runTime.getFadeAmount(), 0.15f);
            }
            if (this.getRemainingTime() <= this.runLength.getDuration()) {
                if (!this.runLength.isTimeNotAtZero()) {
                    this.runLength.startAnimation();
                }
                return 1.0f - 0.85f * this.runLength.getFadeAmount();
            }
            return 1.0f;
        }
    }
}
