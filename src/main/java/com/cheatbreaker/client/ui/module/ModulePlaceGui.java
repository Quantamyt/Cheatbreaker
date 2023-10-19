package com.cheatbreaker.client.ui.module;


import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.ui.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class ModulePlaceGui extends GuiScreen {
    private final AbstractModule module;
    private final HudLayoutEditorGui eventButton;

    public ModulePlaceGui(HudLayoutEditorGui cBModulesGui, AbstractModule abstractModule) {
        abstractModule.setState(true);
        this.module = abstractModule;
        this.eventButton = cBModulesGui;
    }

    @Override
    public void updateScreen() {
    }

    @Override
    public void initGui() {
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
//        if ((Boolean) CheatBreaker.getInstance().getGlobalSettings().useBackgroundOverlay.getValue()) {
//            this.drawDefaultBackground();
//        } else {
        this.renderBlur();
//        }
        RenderUtil.drawRoundedRect(0.0, this.height / 3, this.width, (float) (this.height / 3) + 2.1086957f * 0.23711339f, 0.0, 0x6F000000);
        RenderUtil.drawRoundedRect(0.0, this.height / 3 * 2, this.width, (float) (this.height / 3 * 2) + 1.1388888f * 0.43902442f, 0.0, 0x6F000000);
        RenderUtil.drawRoundedRect(this.width / 3, 0.0, (float) (this.width / 3) + 0.42073172f * 1.1884058f, this.height, 0.0, 0x6F000000);
        RenderUtil.drawRoundedRect(this.width / 3 * 2, 0.0, (float) (this.width / 3 * 2) + 0.28070176f * 1.78125f, this.height, 0.0, 0x6F000000);
        RenderUtil.drawRoundedRect(this.width / 3 + this.width / 6, this.height / 3 * 2, (float) (this.width / 3 + this.width / 6) + 6.7000003f * 0.07462686f, this.height, 0.0, 0x6F000000);
        float f2 = 1.0f / CheatBreaker.getScaleFactor() / this.module.masterScale();
//        float f3 = (float)(CheatBreaker.getInstance().ubuntuMedium16px.getStringWidth(this.selectedButton.getName()) + 6) * f2;
//        if (this.selectedButton.width < f3) {
//            this.selectedButton.width = (int)f3;
//        }
//        if (this.selectedButton.height < (float)18) {
//            this.selectedButton.height = 18;
//        }
        ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        float[] arrf = AnchorHelper.getPositions(mouseX, mouseY, scaledResolution);
        GuiAnchor cBGuiAnchor = AnchorHelper.getAnchor(mouseX, mouseY, scaledResolution);
        if (cBGuiAnchor != GuiAnchor.MIDDLE_MIDDLE) {
            if (cBGuiAnchor == GuiAnchor.MIDDLE_BOTTOM_LEFT || cBGuiAnchor == GuiAnchor.MIDDLE_BOTTOM_RIGHT) {
                Gui.drawRect(arrf[0], arrf[1], arrf[0] + (float) (scaledResolution.getScaledWidth() / 6), arrf[1] + (float) (scaledResolution.getScaledHeight() / 3), 0x2F000000);
            } else {
                Gui.drawRect(arrf[0], arrf[1], arrf[0] + (float) (scaledResolution.getScaledWidth() / 3), arrf[1] + (float) (scaledResolution.getScaledHeight() / 3), 0x2F000000);
            }
        }
        int n3 = scaledResolution.getScaledWidth();
        int n4 = scaledResolution.getScaledHeight();
        float[] arrf2 = AnchorHelper.getPositions(this.module, mouseX, mouseY, scaledResolution);
        if (cBGuiAnchor != this.module.getGuiAnchor()) {
            this.module.setAnchor(cBGuiAnchor);
            this.module.setTranslations(0.0f, 0.0f);
        }
        if (!Mouse.isButtonDown(1)) {
            RenderUtil.drawRoundedRect(2, 0.0, 1.8636363192038112 * 1.3414634466171265, n4, 0.0, -15599126);
            RenderUtil.drawRoundedRect((float) n3 - 1.1197916f * 2.2325583f, 0.0, n3 - 2, n4, 0.0, -15599126);
            RenderUtil.drawRoundedRect(0.0, 2, n3, 0.4375 * 5.714285714285714, 0.0, -15599126);
            RenderUtil.drawRoundedRect(0.0, (float) n4 - 0.557971f * 6.2727275f, n3, n4 - 3, 0.0, -15599126);
        }
        float f4 = (float) mouseX - arrf[0] - arrf2[0];
        float f5 = (float) mouseY - arrf[1] - arrf2[1];
        if (!Mouse.isButtonDown(1)) {
            float[] arrf3 = this.module.getScaledPoints(scaledResolution, false);
            f4 = this.lIIIIlIIllIIlIIlIIIlIIllI(this.module, f4, arrf3, (float) ((int) (this.module.width * this.module.masterScale())), false);
            f5 = this.lIIIIIIIIIlIllIIllIlIIlIl(this.module, f5, arrf3, (float) ((int) (this.module.height * this.module.masterScale())), false);
        }
        this.module.setTranslations(f4, f5);
        GL11.glPushMatrix();
        this.module.scaleAndTranslate(scaledResolution);
        RenderUtil.drawRoundedRect(-2, -2, this.module.width + 2.0f, this.module.height + 2.0f, 4, 551805923);
        GL11.glPushMatrix();
        GL11.glScalef(f2, f2, f2);
        if ((Boolean) CheatBreaker.getInstance().getGlobalSettings().showModName.getValue()) {
            float v = mouseY < 9 ? module.height / f2 : (float) (-CheatBreaker.getInstance().ubuntuMedium16px.getHeight() - 4);
            switch (module.getPosition()) {
                case LEFT:
                    float f6 = 0.0f;
                    CheatBreaker.getInstance().ubuntuMedium16px.drawStringWithShadow(module.getName(), f6, v, -1);
                    break;
                case CENTER:
                    float f7 = module.width / f2 / 2.0f;
                    CheatBreaker.getInstance().ubuntuMedium16px.drawCenteredStringWithShadow(module.getName(), f7, v, -1);
                    break;
                case RIGHT:
                    float f8 = module.width / f2 - (float) CheatBreaker.getInstance().ubuntuMedium16px.getStringWidth(module.getName());
                    CheatBreaker.getInstance().ubuntuMedium16px.drawStringWithShadow(module.getName(), f8, v, -1);
            }
        }
        GL11.glPopMatrix();
        GL11.glPopMatrix();
    }

    private float lIIIIlIIllIIlIIlIIIlIIllI(AbstractModule abstractModule, float f, float[] arrf, float f2, boolean bl) {
        int n;
        float f3 = f;
        int n2 = n = bl ? 0 : 3;
        if (f3 + arrf[0] < (float) n) {
            f3 = -arrf[0] + (float) n;
        } else if (f3 + arrf[0] * abstractModule.masterScale() + f2 > (float) (this.width - n)) {
            f3 = (int) ((float) this.width - arrf[0] * abstractModule.masterScale() - f2 - (float) n);
        }
        return f3;
    }

    private float lIIIIIIIIIlIllIIllIlIIlIl(AbstractModule abstractModule, float f, float[] arrf, float f2, boolean bl) {
        int n;
        float f3 = f;
        int n2 = n = bl ? 0 : 2;
        if (f3 + arrf[1] < (float) n) {
            f3 = -arrf[1] + (float) n;
        } else if (f3 + arrf[1] * abstractModule.masterScale() + f2 > (float) (this.height - n)) {
            f3 = (int) ((float) this.height - arrf[1] * abstractModule.masterScale() - f2 - (float) n);
        }
        return f3;
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (mouseButton != 0) {
            return;
        }
        ScaledResolution scaledResolution = new ScaledResolution(this.mc);
        GuiAnchor cBGuiAnchor = AnchorHelper.getAnchor(mouseX, mouseY, scaledResolution);
        this.module.setAnchor(cBGuiAnchor);
        Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0f));
        this.module.setState(true);
        HudLayoutEditorGui editorGui = new HudLayoutEditorGui();
        this.mc.displayGuiScreen(editorGui);
        editorGui.currentScrollableElement = editorGui.modulesElement;
        editorGui.currentScrollableElement.bottom = false;
        editorGui.currentScrollableElement.scrollAmount = this.eventButton.modulesElement.scrollAmount;
        editorGui.currentScrollableElement.yOffset = 0;
    }

    @Override
    public void mouseReleased(int n, int n2, int n3) {
    }

    @Override
    public void keyTyped(char c, int n) {
    }
}
