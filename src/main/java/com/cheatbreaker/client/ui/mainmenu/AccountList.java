package com.cheatbreaker.client.ui.mainmenu;


import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.ui.element.type.ScrollableElement;
import com.cheatbreaker.client.ui.fading.ColorFade;
import com.cheatbreaker.client.ui.fading.MinMaxFade;
import com.cheatbreaker.client.ui.util.RenderUtil;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class AccountList extends AbstractElement {
    @Setter private ResourceLocation resourceLocation;
    @Setter private String username;
    private final ColorFade outline = new ColorFade(0x4FFFFFFF, -1353670564);
    private final ColorFade topGradient = new ColorFade(444958085, 1063565678);
    private final ColorFade bottomGradient = new ColorFade(444958085, 1062577506);
    private final MinMaxFade fadeTime = new MinMaxFade(300L);
    private float height2;
    private boolean interacting;
    private final MainMenuBase mainMenuBase;
    private final ScrollableElement scrollbar = new ScrollableElement(this);
    private float accountSelectionBoxHeight;

    public AccountList(MainMenuBase mainMenuBase, String username, ResourceLocation resourceLocation) {
        this.mainMenuBase = mainMenuBase;
        this.resourceLocation = resourceLocation;
        this.username = username;
    }

    @Override
    public void setElementSize(float f, float y, float width, float height) {
        super.setElementSize(f, y, width, height);
        if (this.height2 == 0.0f) {
            this.height2 = height;
        }
        this.accountSelectionBoxHeight = Math.min(this.mainMenuBase.getAccountsList().size() * 16 + 12, 120);
        this.scrollbar.setElementSize(f + width - 5.0f, y + this.height2 + 6.0f, 4.0f, this.accountSelectionBoxHeight - 7.0F);
        this.scrollbar.setScrollAmount(this.mainMenuBase.getAccountsList().size() * 16 + 4);
    }

    @Override
    public void handleElementMouse() {
        this.scrollbar.handleElementMouse();
    }

    @Override
    protected void handleElementDraw(float f, float f2, boolean bl) {
        boolean bl2 = false; //bl && this.isMouseInside(f, f2);

        RenderUtil.drawGradientRectWithOutline(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height2, this.outline.getColor(bl2).getRGB(), this.topGradient.getColor(bl2).getRGB(), this.bottomGradient.getColor(bl2).getRGB());

        float f3 = 6;
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);

        RenderUtil.renderIcon(this.resourceLocation, f3, this.xPosition + 4.0f, this.yPosition + this.height2 / 2.0f - f3);
        CheatBreaker.getInstance().robotoRegular13px.drawString(this.username, this.xPosition + 22.0f, this.yPosition + 4.5f, -1342177281);

        float f4 = this.fadeTime.inOutFade(this.isMouseInside(f, f2) && bl);

        if (this.fadeTime.isZeroOrLess()) {
            this.setElementSize(this.xPosition, this.yPosition, this.width, this.height2 + this.accountSelectionBoxHeight * f4);
            this.interacting = false;//true; - removed until we redo account system
        } else if (!this.fadeTime.isZeroOrLess() && !this.isMouseInside(f, f2)) {
            this.interacting = false;
        }

        if (this.interacting) {
            float f5 = 0.5f;
            float f6 = this.yPosition + this.height + f5;
            float f7 = this.yPosition + 5.0f + this.height2;
            if (f6 > f7) {
                Gui.drawBoxWithOutLine(this.xPosition + 1.0f, f7, this.xPosition + this.width - 1.0f, f6, f5, 0x4FFFFFFF, 444958085);
            }
            GL11.glPushMatrix();
            GL11.glEnable(3089);
            RenderUtil.startScissorBox((int) this.xPosition, (int) (this.yPosition + this.height2), (int) (this.xPosition + this.width), (int) (this.yPosition + this.height2 + 7.0f + (this.height - this.height2 - 6.0f) * f4), (float) ((int) ((float) this.mainMenuBase.getScaledResolution().getScaleFactor() * this.mainMenuBase.getScaleFactor())), (int) this.mainMenuBase.getScaledHeight());
            this.scrollbar.drawScrollable(f, f2, bl);
            int n = 1;
            for (CBAccount account : this.mainMenuBase.getAccountsList()) {
                float f8 = this.xPosition;
                float f9 = this.xPosition + this.width;
                float f10 = this.yPosition + this.height2 + (float) (n * 16) - 8.0f;
                float f11 = f10 + 16.0f;
                boolean bl3 = f > f8 && f < f9 && f2 - this.scrollbar.getPosition() >
                        f10 && f2 - this.scrollbar.getPosition() < f11 && bl &&
                        !this.scrollbar.isMouseInside(f, f2) && !this.scrollbar.isButtonHeld();
                GL11.glColor4f(1.0f, 1.0f, 1.0f, bl3 ? 1.0f : 0.8148148f * 0.8590909f);
                RenderUtil.renderIcon(account.getHeadIcon(), f3, this.xPosition + 4.0f, f10 + 8.0f - f3);
                CheatBreaker.getInstance().robotoRegular13px.drawString(account.getDisplayName(), this.xPosition + 22.0f, f10 + 4.0f, bl3 ? -1 : -1342177281);
                ++n;
            }
            this.scrollbar.handleElementDraw(f, f2, bl);
            GL11.glDisable(3089);
            GL11.glPopMatrix();
        }
    }

    public float setWidthFromAccountString(float stringWidth) {
        return 22.0F + stringWidth + 10.0F;
    }

    @Override
    public boolean handleElementMouseClicked(float f, float f2, int n, boolean bl) {
        if (!bl) {
            return false;
        }
//        if (this.fadeTime.isHovered()) {
//            this.scrollbar.handleElementMouseClicked(f, f2, n, bl);
//            int n2 = 1;
//            for (CBAccount account : this.mainMenuBase.getAccountsList()) {
//                float f3 = this.xPosition;
//                float f4 = this.xPosition + this.width;
//                float f5 = this.yPosition + this.height2 + (float) (n2 * 16) - 8.0f;
//                float f6 = f5 + 16.0f;
//                boolean bl2 = f > f3 && f < f4 && f2 - this.scrollbar.getPosition() > f5 && f2 - this.scrollbar.getPosition() < f6 && bl && !this.scrollbar.isMouseInside(f, f2) && !this.scrollbar.isButtonHeld();
//                if (bl2) {
//                    Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0f));
//                    this.mainMenuBase.updateAccountElementAndLogin(account.getDisplayName());
//                }
//                ++n2;
//            }
//        }
        return false;
    }
}
