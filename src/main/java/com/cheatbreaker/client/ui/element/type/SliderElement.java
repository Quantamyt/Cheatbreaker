package com.cheatbreaker.client.ui.element.type;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.ui.element.AbstractModulesGuiElement;
import com.cheatbreaker.client.ui.util.RenderUtil;
import net.minecraft.client.Minecraft;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.module.data.SettingType;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class SliderElement extends AbstractModulesGuiElement {
    private final Setting setting;
    private float settingValue = -1;
    private float value;
    private boolean interacting = false;

    public SliderElement(Setting setting, float scaleFactor) {
        super(scaleFactor);
        this.setting = setting;
        this.height = 14;
        this.settingValue = Float.parseFloat("" + setting.getValue());
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY, int button) {
        boolean bl = (float) mouseX > (float)(this.x + 170) * this.scale && (float) mouseX < (float)(this.x + 170 + 170 - 2) * this.scale && (float) mouseY > (float)(this.y + 4 + this.yOffset) * this.scale && (float) mouseY < (float)(this.y + 10 + this.yOffset) * this.scale;
        if (button == 0 && bl) {
            this.interacting = true;
        }
    }

    @Override
    public void handleDrawElement(int mouseX, int mouseY, float partialTicks) {
        float f2;
        float f3;
        int n3 = 148;
        CheatBreaker.getInstance().ubuntuMedium16px.drawString(this.setting.getSettingName().toUpperCase(), this.x + 10, (float)(this.y + 2), -1895825408);
        if (this.interacting && !Mouse.isButtonDown(0)) {
            this.interacting = false;
        }
        String string = this.setting.getValue().toString();
        CheatBreaker.getInstance().ubuntuMedium16px.drawString(string, this.x + 169 - (float)CheatBreaker.getInstance().ubuntuMedium16px.getStringWidth(string), (float)(this.y + 2), -1895825408);
        boolean bl = (float) mouseX > (float)(this.x + 172) * this.scale && (float) mouseX < (float)(this.x + 172 + n3 - 2) * this.scale && (float)mouseY > (float)(this.y + 4 + this.yOffset) * this.scale && (float)mouseY < (float)(this.y + 10 + this.yOffset) * this.scale;
        RenderUtil.drawRoundedRect(this.x + 174, this.y + 6, this.x + 170 + n3 - 4, this.y + 8, 1.0, bl ? -1895825408 : 0x6F000000);
        double d = n3 - 18;
        float minimumValue = Float.parseFloat("" + this.setting.getMinimumValue());
        float maximumValue = Float.parseFloat("" + this.setting.getMaximumValue());
        if (this.interacting) {
            this.value = (float)Math.round(((double)minimumValue + (double)((float) mouseX - (float)(this.x + 180) * this.scale) * ((double)(maximumValue - minimumValue) / (d * (double)this.scale))) * (double)100) / (float)100;
            if (this.setting.getType().equals(SettingType.INTEGER) || Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                this.value = Math.round(this.value);
            }
            if (this.value < minimumValue) {
                this.value = minimumValue;
            } else if (this.value > maximumValue) {
                this.value = maximumValue;
            }
            switch (this.setting.getType()) {
                case INTEGER:
                    this.setting.setValue(Integer.parseInt((int)this.value + ""));
                    break;
                case FLOAT:
                    this.setting.setValue(this.value);
                    break;
                case DOUBLE:
                    this.setting.setValue(Double.parseDouble(this.value + ""));
            }
            CheatBreaker.getInstance().getModuleManager().keystrokesMod.updateKeyElements();
            Minecraft.getMinecraft().ingameGUI.getChatGUI().func_146245_b();
        }
        f3 = (f3 = Float.parseFloat(this.setting.getValue() + "")) < this.settingValue ? this.settingValue - f3 : (f3 -= this.settingValue);
        float f6 = ((maximumValue - minimumValue) / (float)20 + f3 * (float)8) / (float)(Minecraft.debugFPS + 1);
        if ((double)f6 < 43.5 * 2.2988505747126437E-6) {
            f6 = 1.9523809f * 5.121951E-5f;
        }
        if (this.settingValue < (f2 = Float.parseFloat(this.setting.getValue() + ""))) {
            this.settingValue = Math.min(this.settingValue + f6, f2);
        } else if (this.settingValue > f2) {
            this.settingValue = Math.max(this.settingValue - f6, f2);
        }
        double d2 = (float)100 * ((this.settingValue - minimumValue) / (maximumValue - minimumValue));
        RenderUtil.drawRoundedRect(this.x + 174, this.y + 6, (double)(this.x + 180) + d * d2 / (double)100, this.y + 8, 4, -12418828);
        GL11.glColor4f(0.5714286f * 0.4375f, 0.45849055f * 0.9814815f, 1.0f, 1.0f);
        RenderUtil.drawCircle((double)((float)this.x + 543.75f * 0.33333334f) + d * d2 / (double)100, (float)this.y + 0.6666667f * 10.875f, 2.531249981140718 * 1.7777777910232544);
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        RenderUtil.drawCircle((double)((float)this.x + 0.8804348f * 205.8642f) + d * d2 / (double)100, (float)this.y + 0.13043478f * 55.583332f, 2.639325754971479 * 1.0229885578155518);
        drawDescription(this.setting, mouseX, mouseY);
    }
}
