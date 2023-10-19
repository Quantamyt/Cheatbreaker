package com.cheatbreaker.client.ui.mainmenu;

import com.cheatbreaker.client.ui.fading.FloatFade;
import com.cheatbreaker.client.ui.fading.MinMaxFade;
import com.cheatbreaker.client.ui.util.RenderUtil;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.util.concurrent.ThreadLocalRandom;

public final class LunarNetworkLogoElement extends AbstractElement {
    private final ResourceLocation baseLogo;
    private final ResourceLocation[] star = new ResourceLocation[8];
    private final IIlllIIIlllIlIlIlllllIIIl[] IlIIIIIIIlIlllIIIlIlIIIIl = new IIlllIIIlllIlIlIlllllIIIl[8];
    private boolean llIIllIlIlllllIlIllIIlIll = true;
    private final float[] lIllllIllIIIlIlllIIlIllll = new float[8];
    public transient int llIlIIlIIlIIIlIIIllIIllIl;

    public LunarNetworkLogoElement() {
        this.baseLogo = new ResourceLocation("client/animatedlogo/64/logo_64_no_stars.png");
        for (int i = 1; i <= 8; ++i) {
            this.star[i - 1] = new ResourceLocation("client/animatedlogo/64/logo_64_star_" + i + ".png");
        }
        this.IllIIIlIllIllIIlIIIlllIII();
    }

    private void IllIIIlIllIllIIlIIIlllIII() {
        for (int i = 1; i <= 8; ++i) {
            if (this.IlIIIIIIIlIlllIIIlIlIIIIl[i - 1] != null && !this.IlIIIIIIIlIlllIIIlIlIIIIl[i - 1].isZeroOrLess()) {
                this.llIIllIlIlllllIlIllIIlIll = false;
            }
            if (this.IlIIIIIIIlIlllIIIlIlIIIIl[i - 1] != null && this.IlIIIIIIIlIlllIIIlIlIIIIl[i - 1].isZeroOrLess())
                continue;
            long l = ThreadLocalRandom.current().nextLong(4000L, 12000L);
            if (this.llIIllIlIlllllIlIllIIlIll) {
                this.lIllllIllIIIlIlllIIlIllll[i - 1] = Math.max(ThreadLocalRandom.current().nextFloat(), 0.8f);
            }
            this.IlIIIIIIIlIlllIIIlIlIIIIl[i - 1] = new IIlllIIIlllIlIlIlllllIIIl(this, l);
        }
    }

    @Override
    public void setElementSize(float f, float f2, float f3, float f4) {
        super.setElementSize(f, f2, f3, f4);
    }

    @Override
    public void handleElementUpdate() {
        ++this.llIlIIlIIlIIIlIIIllIIllIl;
        this.IllIIIlIllIllIIlIIIlllIII();
    }

    @Override
    protected void handleElementDraw(float f, float f2, boolean bl) {
        GL11.glPushMatrix();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderUtil.renderIcon(this.baseLogo, this.xPosition, this.yPosition, this.width, this.height);
        for (int i = 0; i < 8; ++i) {
            IIlllIIIlllIlIlIlllllIIIl iIlllIIIlllIlIlIlllllIIIl = this.IlIIIIIIIlIlllIIIlIlIIIIl[i];
            if (!iIlllIIIlllIlIlIlllllIIIl.isTimeNotAtZero()) {
                iIlllIIIlllIlIlIlllllIIIl.startAnimation();
            }
            GL11.glPushMatrix();
            if (!iIlllIIIlllIlIlIlllllIIIl.isZeroOrLess()) {
                this.IllIIIlIllIllIIlIIIlllIII();
            }
            float f3 = iIlllIIIlllIlIlIlllllIIIl.lIlIlllllIllIllIIlIllIIll();
            if (iIlllIIIlllIlIlIlllllIIIl.IllIlIlIIIIIlllllllllIlII() && this.llIIllIlIlllllIlIllIIlIll) {
                this.llIIllIlIlllllIlIllIIlIll = false;
            }
            if (this.llIIllIlIlllllIlIllIIlIll) {
                f3 = Math.max(f3, this.lIllllIllIIIlIlllIIlIllll[i]);
            }
            GL11.glColor4f(1.0f, 1.0f, 1.0f, f3);
            RenderUtil.renderIcon(this.star[i], this.xPosition, this.yPosition, this.width, this.height);
            GL11.glPopMatrix();
        }
        GL11.glPopMatrix();
    }

    class IIlllIIIlllIlIlIlllllIIIl
            extends FloatFade {
        private final MinMaxFade runTime;
        private final MinMaxFade runLength;
        final LunarNetworkLogoElement loopAnimation;

        private IIlllIIIlllIlIlIlllllIIIl(LunarNetworkLogoElement lunarNetworkLogoElement, long duration) {
            super(duration);
            this.loopAnimation = lunarNetworkLogoElement;
            this.runTime = new MinMaxFade(Math.min((long) Math.min((float) duration * 0.2f, 3000.0f), 1500L));
            this.runLength = new MinMaxFade(Math.min((long) ((float) duration * 0.4f), 5000L));
        }

        @Override
        public void startAnimation() {
            super.startAnimation();
            if (!this.runTime.isTimeNotAtZero()) {
                this.runTime.startAnimation();
            }
        }

        boolean IllIlIlIIIIIlllllllllIlII() {
            return this.runTime.isOver();
        }

        float lIlIlllllIllIllIIlIllIIll() {
            if (!this.runTime.isTimeNotAtZero()) {
                this.runTime.startAnimation();
            }
            if (this.runTime.isZeroOrLess()) {
                return Math.max(1.0f * this.runTime.getFadeAmount(), 0.15f);
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
