package com.cheatbreaker.client.ui.element.type;

import java.awt.Color;


import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.ui.element.AbstractModulesGuiElement;
import com.cheatbreaker.client.ui.util.RenderUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.Minecraft;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.module.data.SettingType;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

/** This shouldn't be called Old Slider element for a few reasons:
 * This is merely a color rainbow slider - probably used for testing
 * This only allowed floats.
 */
public class ColorSliderElement extends AbstractModulesGuiElement {
    private final Setting lIIIIlIIllIIlIIlIIIlIIllI;
    private float IllIIIIIIIlIlIllllIIllIII = -1;
    private float lIIIIllIIlIlIllIIIlIllIlI;
    private boolean IlllIllIlIIIIlIIlIIllIIIl = false;

    public ColorSliderElement(Setting setting, float f) {
        super(f);
        this.lIIIIlIIllIIlIIlIIIlIIllI = setting;
        this.height = 14;
        this.IllIIIIIIIlIlIllllIIllIII = Float.parseFloat("" + setting.getValue());
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY, int button) {
        int n4 = 170;
        boolean bl = (float) mouseX > (float)(this.x + 170) * this.scale && (float) mouseX < (float)(this.x + 170 + n4 - 2) * this.scale && (float) mouseY > (float)(this.y + 4 + this.yOffset) * this.scale && (float) mouseY < (float)(this.y + 10 + this.yOffset) * this.scale;
        if (button == 0 && bl) {
            this.IlllIllIlIIIIlIIlIIllIIIl = true;
        }
    }

    @Override
    public void handleDrawElement(int mouseX, int n2, float partialTicks) {
        float f2;
        float f3;
        int n3 = 148;
        float f4 = n3 - 18;
        CheatBreaker.getInstance().ubuntuMedium16px.drawString(this.lIIIIlIIllIIlIIlIIIlIIllI.getSettingName().toUpperCase(), this.x + 10, (float)(this.y + 2), -1895825408);
        if (this.IlllIllIlIIIIlIIlIIllIIIl && !Mouse.isButtonDown(0)) {
            this.IlllIllIlIIIIlIIlIIllIIIl = false;
        }
        Gui.drawRect((float)this.x + 0.13043478f * 1376.1666f, (float)this.y + 2.2962964f * 2.3951612f, (float)this.x + 0.28169015f * 640.77496f + f4, (float)this.y + 1.4333333f * 5.930233f, -822083584);
        int n4 = 0;
        while ((float)n4 < f4) {
            int n5 = Color.HSBtoRGB((float)n4 / f4, 1.0f, 1.0f);
            Gui.drawRect(this.x + 180 + n4, this.y + 6, this.x + 181 + n4, this.y + 8, n5);
            ++n4;
        }
        float f5 = Float.parseFloat("" + this.lIIIIlIIllIIlIIlIIIlIIllI.getMinimumValue());
        float f6 = Float.parseFloat("" + this.lIIIIlIIllIIlIIlIIIlIIllI.getMaximumValue());
        if (this.IlllIllIlIIIIlIIlIIllIIIl) {
            this.lIIIIllIIlIlIllIIIlIllIlI = (float)((double)(f5 + ((float) mouseX - (float)(this.x + 180) * this.scale) * ((f6 - f5) / (f4 * this.scale))) * (double)100) / (float)100;
            if (this.lIIIIlIIllIIlIIlIIIlIIllI.getType().equals(SettingType.INTEGER)) {
                this.lIIIIllIIlIlIllIIIlIllIlI = Math.round(this.lIIIIllIIlIlIllIIIlIllIlI);
            }
            if (this.lIIIIllIIlIlIllIIIlIllIlI < f5) {
                this.lIIIIllIIlIlIllIIIlIllIlI = f5;
            } else if (this.lIIIIllIIlIlIllIIIlIllIlI > f6) {
                this.lIIIIllIIlIlIllIIIlIllIlI = f6;
            }
            switch (this.lIIIIlIIllIIlIIlIIIlIIllI.getType()) {
                case INTEGER:
                    this.lIIIIlIIllIIlIIlIIIlIIllI.setValue(Integer.parseInt((int)this.lIIIIllIIlIlIllIIIlIllIlI + ""));
                    break;
                case FLOAT:
                    this.lIIIIlIIllIIlIIlIIIlIIllI.setValue(Float.valueOf(this.lIIIIllIIlIlIllIIIlIllIlI));
                    break;
                case DOUBLE:
                    this.lIIIIlIIllIIlIIlIIIlIIllI.setValue(Double.parseDouble(this.lIIIIllIIlIlIllIIIlIllIlI + ""));
            }
        }
        f3 = (f3 = Float.parseFloat(this.lIIIIlIIllIIlIIlIIIlIIllI.getValue() + "")) < this.IllIIIIIIIlIlIllllIIllIII ? this.IllIIIIIIIlIlIllllIIllIII - f3 : (f3 -= this.IllIIIIIIIlIlIllllIIllIII);
        float f7 = ((f6 - f5) / (float)20 + f3 * (float)8) / (float)(Minecraft.debugFPS + 1);
        if ((double)f7 < (double)0.1590909f * 6.285714392759364E-4) {
            f7 = 3.163265E-5f * 3.1612904f;
        }
        if (this.IllIIIIIIIlIlIllllIIllIII < (f2 = Float.parseFloat(this.lIIIIlIIllIIlIIlIIIlIIllI.getValue() + ""))) {
            this.IllIIIIIIIlIlIllllIIllIII = this.IllIIIIIIIlIlIllllIIllIII + f7 <= f2 ? (this.IllIIIIIIIlIlIllllIIllIII += f7) : f2;
        } else if (this.IllIIIIIIIlIlIllllIIllIII > f2) {
            this.IllIIIIIIIlIlIllllIIllIII = this.IllIIIIIIIlIlIllllIIllIII - f7 >= f2 ? (this.IllIIIIIIIlIlIllllIIllIII -= f7) : f2;
        }
        double d = (float)100 * ((this.IllIIIIIIIlIlIllllIIllIII - f5) / (f6 - f5));
        GL11.glColor4f(0.38f * 0.65789473f, 0.4318182f * 1.0421052f, 1.0f, 1.0f);
        RenderUtil.drawCircle((double)((float)this.x + 0.90217394f * 200.90361f) + (double)f4 * d / (double)100, (float)this.y + 1.5890411f * 4.5625f, 6.333333492279053 * 0.7105262979576137);
        float f8 = (Float) this.lIIIIlIIllIIlIIlIIIlIIllI.getValue();
        int n6 = f8 == 0.0f ? -1 : Color.HSBtoRGB(f8, 1.0f, 1.0f);
        GL11.glColor4f((float)(n6 >> 16 & 0xFF) / (float)255, (float)(n6 >> 8 & 0xFF) / (float)255, (float)(n6 & 0xFF) / (float)255, 1.0f);
        RenderUtil.drawCircle((double)((float)this.x + 1.5f * 120.833336f) + (double)f4 * d / 100.0, (float)this.y + 0.2962963f * 24.46875f, 11.137499864771966 * 0.24242424964904785);
    }
}
