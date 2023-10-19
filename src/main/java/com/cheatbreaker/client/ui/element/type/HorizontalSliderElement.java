package com.cheatbreaker.client.ui.element.type;

import com.cheatbreaker.client.ui.fading.AbstractFade;
import com.cheatbreaker.client.ui.fading.MinMaxFade;
import com.cheatbreaker.client.ui.mainmenu.AbstractElement;
import net.minecraft.client.gui.Gui;
import com.cheatbreaker.client.module.data.Setting;
import org.lwjgl.input.Mouse;

public class HorizontalSliderElement extends AbstractElement {
    private final Setting setting;
    private final AbstractFade fadeTime;
    private Number value;

    public HorizontalSliderElement(Setting setting) {
        this.setting = setting;
        this.fadeTime = new MinMaxFade(300L);
        this.value = (Number)setting.getValue();
    }

    @Override
    protected void handleElementDraw(float f, float f2, boolean bl) {
        Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, -13158601);
        if (!this.fadeTime.isTimeNotAtZero()) {
            this.value = (Number)this.setting.getValue();
        }
        float f3 = ((Number)this.setting.getValue()).floatValue();
        float f4 = ((Number)this.setting.getMinimumValue()).floatValue();
        float f5 = ((Number)this.setting.getMaximumValue()).floatValue();
        float f6 = f3 - this.value.floatValue();
        float f7 = (float)100 * ((this.value.floatValue() + f6 * this.fadeTime.getFadeAmount() - f4) / (f5 - f4));
        Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width / (float)100 * f7, this.yPosition + this.height, -52429);
    }

    @Override
    public boolean handleElementMouseClicked(float f, float f2, int n, boolean bl) {
        if (!bl) {
            return false;
        }
        if (Mouse.isButtonDown(0) && this.isMouseInside(f, f2)) {
            this.fadeTime.startAnimation();
            this.value = (Number)this.setting.getValue();
            float f3 = ((Number)this.setting.getMinimumValue()).floatValue();
            float f4 = ((Number)this.setting.getMaximumValue()).floatValue();
            if (f - this.xPosition > this.width / 2.0f) {
                f += 2.0f;
            }
            float f5 = f3 + (f - this.xPosition) * ((f4 - f3) / this.width);
            switch (this.setting.getType()) {
                case INTEGER:
                    this.setting.setValue(this.setValue(Integer.parseInt((int)f5 + "")));
                    break;
                case FLOAT:
                    this.setting.setValue(this.setValue(f5));
                    break;
                case DOUBLE:
                    this.setting.setValue(this.setValue(Double.parseDouble((double)f5 + "")));
            }
        }
        return super.handleElementMouseClicked(f, f2, n, bl);
    }

    private Object setValue(Object object) {
        try {
            return object;
        } catch (ClassCastException classCastException) {
            return null;
        }
    }
}
