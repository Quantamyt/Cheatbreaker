package com.cheatbreaker.client.ui.element.type.custom;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.config.GlobalSettings;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.module.data.SettingType;
import com.cheatbreaker.client.ui.element.AbstractModulesGuiElement;
import com.cheatbreaker.client.ui.theme.CBTheme;
import com.cheatbreaker.client.ui.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

public class NewSliderElement extends AbstractModulesGuiElement {
//    private final Setting setting;
    private float settingValue = -1;
    private float value;
    private boolean interacting = false;
    private final ResourceLocation sunIcon = new ResourceLocation("client/icons/sun-64.png");
    private final ResourceLocation moonIcon = new ResourceLocation("client/icons/moon-64.png");

    private final ResourceLocation volumeMuteIcon = new ResourceLocation("client/icons/volume-mute-64.png");
    private final ResourceLocation volumeUpIcon = new ResourceLocation("client/icons/volume-up-64.png");

    private final ResourceLocation circleIcon = new ResourceLocation("client/icons/circle-64.png");
    private final ResourceLocation circleHollowIcon = new ResourceLocation("client/icons/circle-hollow-64.png");

    private final ResourceLocation letterIcon = new ResourceLocation("client/icons/letter-t-64.png");

    private boolean hasIcon = false;
    private boolean hasCenterLabel = false;

    public NewSliderElement(Setting setting, float f) {
        super(f);
        this.setting = setting;
        this.height = 22;
        this.settingValue = Float.parseFloat("" + setting.getValue());
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY, int button) {
        int n4 = 170;
        int ff = 20;
        if (this.height == 12) ff = 10;
        boolean hovering = (float) mouseX > (float) (this.x + 170) * this.scale && (float) mouseX < (float) (this.x + 170 + 148 - 2) * this.scale && (float) mouseY > (float) (this.y + 4 + this.yOffset) * this.scale && (float) mouseY < (float) (this.y + ff + this.yOffset) * this.scale;
        if (button == 0 && hovering) {
            this.interacting = true;
        }
    }

    @Override
    public void handleDrawElement(int mouseX, int mouseY, float partialTicks) {
        float f2;
        float f3;
        int n3 = 148;
        if (this.interacting && !Mouse.isButtonDown(0)) {
            this.interacting = false;
        }
        float yPos = 8;
        float yPos2 = 16;
        float yPos3 = 17.25f;
        int ff = 20;


        if (!this.setting.getCenterLabel().isEmpty()) {
            CheatBreaker.getInstance().ubuntuMedium16px.drawCenteredString(this.setting.getCenterLabel(), this.x + 172 + n3 / 2, this.y - 2, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkTextColor2 : CBTheme.lightTextColor2);
            Gui.drawRect((float) (this.x + 172 + n3 / 2) - 0.5f, this.y + 8, (float) (this.x + 172 + n3 / 2) + 0.5f, this.y + 14, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkTextColor : CBTheme.lightTextColor);
            this.hasCenterLabel = true;
        }
        float darkOrLight = GlobalSettings.darkMode.getBooleanValue() ? 1.0F : 0.0F;
        GL11.glColor4f(darkOrLight, darkOrLight, darkOrLight, 0.43f);
        if (!this.setting.getLeftIcon().isEmpty() && !this.setting.getRightIcon().isEmpty()) {
            RenderUtil.renderIcon(new ResourceLocation("client/icons/" + this.setting.getLeftIcon() + ".png"), (float) (this.x + 180) - 5.0F, (float) (this.y + 3), 10.0f, 10.0f);
            Gui.drawRect((float) (this.x + 180) - 0.4509804f * 1.1086956f, this.y + 12, (float) (this.x + 180) + 0.45652175f * 1.0952381f, this.y + 14, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkTextColor : CBTheme.lightTextColor);
            RenderUtil.renderIcon(new ResourceLocation("client/icons/" + this.setting.getRightIcon() + ".png"), (float) (this.x + 170 + n3 - 10.0f) - (float) 5, (float) (this.y + 3), (float) 10.0f, 10.0f);
            Gui.drawRect((float) (this.x + 170 + n3 - 10) - 1.1875f * 0.42105263f, this.y + 12, (float) (this.x + 170 + n3 - 10) + 0.4673913f * 1.0697675f, this.y + 14, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkTextColor : CBTheme.lightTextColor);
            this.hasIcon = true;
        } else if (this.setting.getSettingName().endsWith("Opacity")) {
            RenderUtil.renderIcon(this.circleHollowIcon, (float) (this.x + 180) - 4.0f, (float) (this.y + 3), 7.5f, 7.5f);
            Gui.drawRect((float) (this.x + 180) - 0.4509804f * 1.1086956f, this.y + 12, (float) (this.x + 180) + 0.45652175f * 1.0952381f, this.y + 14, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkTextColor : CBTheme.lightTextColor);
            RenderUtil.renderIcon(this.circleIcon, (float) (this.x + 170 + n3 - 7.5f) - (float) 6.5f, (float) (this.y + 3), (float) 7.5f, 7.5f);
            Gui.drawRect((float) (this.x + 170 + n3 - 10) - 1.1875f * 0.42105263f, this.y + 12, (float) (this.x + 170 + n3 - 10) + 0.4673913f * 1.0697675f, this.y + 14, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkTextColor : CBTheme.lightTextColor);
            this.hasIcon = true;
        } else if (this.setting.getSettingName().endsWith("Volume")) {
            RenderUtil.renderIcon(this.volumeMuteIcon, (float) (this.x + 180) - 3.25f, (float) (this.y + 3), 7.5f, 7.5f);
            Gui.drawRect((float) (this.x + 180) - 0.4509804f * 1.1086956f, this.y + 12, (float) (this.x + 180) + 0.45652175f * 1.0952381f, this.y + 14, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkTextColor : CBTheme.lightTextColor);
            RenderUtil.renderIcon(this.volumeUpIcon, (float) (this.x + 170 + n3 - 7.5f) - (float) 5, (float) (this.y + 3), (float) 7.5f, 7.5f);
            Gui.drawRect((float) (this.x + 170 + n3 - 10) - 1.1875f * 0.42105263f, this.y + 12, (float) (this.x + 170 + n3 - 10) + 0.4673913f * 1.0697675f, this.y + 14, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkTextColor : CBTheme.lightTextColor);
            this.hasIcon = true;
        } else if (this.setting.getSettingName().equals("World Time")) {
            RenderUtil.renderIcon(this.moonIcon, (float) (this.x + 180) - 3.25f, (float) (this.y + 3), 7.5f, 7.5f);
            Gui.drawRect((float) (this.x + 180) - 0.4509804f * 1.1086956f, this.y + 12, (float) (this.x + 180) + 0.45652175f * 1.0952381f, this.y + 14, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkTextColor : CBTheme.lightTextColor);
            RenderUtil.renderIcon(this.sunIcon, (float) (this.x + 170 + n3 - 10) - (float) 5, (float) (this.y + 2), (float) 10, 10);
            Gui.drawRect((float) (this.x + 170 + n3 - 10) - 1.1875f * 0.42105263f, this.y + 12, (float) (this.x + 170 + n3 - 10) + 0.4673913f * 1.0697675f, this.y + 14, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkTextColor : CBTheme.lightTextColor);
            this.hasIcon = true;
        } if (this.setting.getSettingName().endsWith("Scale") || this.setting.getSettingName().endsWith("Scale Multiplier")) {
            RenderUtil.renderIcon(this.letterIcon, (float) (this.x + 180) - 3.0f, (float) (this.y + 4), 6.0f, 6.0f);
            Gui.drawRect((float) (this.x + 180) - 0.4509804f * 1.1086956f, this.y + 12, (float) (this.x + 180) + 0.45652175f * 1.0952381f, this.y + 14, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkTextColor : CBTheme.lightTextColor);
            RenderUtil.renderIcon(this.letterIcon, (float) (this.x + 170 + n3 - 10) - (float) 4, (float) (this.y + 2.5f), (float) 8.0f, 8.0f);
            Gui.drawRect((float) (this.x + 170 + n3 - 10) - 1.1875f * 0.42105263f, this.y + 12, (float) (this.x + 170 + n3 - 10) + 0.4673913f * 1.0697675f, this.y + 14, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkTextColor : CBTheme.lightTextColor);
            this.hasIcon = true;
        }
        if (!this.hasIcon && !this.hasCenterLabel) {
            this.height = 12;
            yPos = 2;
            yPos2 = 6;
            yPos3 = 7.25f;
            ff = 10;
        }

        CheatBreaker.getInstance().ubuntuMedium16px.drawString(this.setting.getSettingName().toUpperCase(), this.x + 10, (float) (this.y + yPos), GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkTextColor2 : CBTheme.lightTextColor2);
        if (this.setting.isShowValue()) {
            String string = this.setting.getValue().toString() + this.setting.getUnit();
            CheatBreaker.getInstance().ubuntuMedium16px.drawString(string, this.x + 169 - (float)CheatBreaker.getInstance().ubuntuMedium16px.getStringWidth(string), (float)(this.y + yPos), GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkTextColor2 : CBTheme.lightTextColor2);
        }

        boolean bl = (float) mouseX > (float) (this.x + 170) * this.scale && (float) mouseX < (float) (this.x + 170 + n3 - 2) * this.scale && (float) mouseY > (float) (this.y + 4 + this.yOffset) * this.scale && (float) mouseY < (float) (this.y + ff + this.yOffset) * this.scale;
        RenderUtil.drawRoundedRect(this.x + 174, this.y + yPos2, this.x + 170 + n3 - 4, this.y + yPos2 + 2, 1.0, bl ? GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkTextColor2 : CBTheme.lightTextColor2 : GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkTextColor : CBTheme.lightTextColor);
        double d = n3 - 18;
        if (this.setting.getMinimumValue() != null && this.setting.getMaximumValue() != null) {
            float f4 = Float.parseFloat("" + this.setting.getMinimumValue());
            float f5 = Float.parseFloat("" + this.setting.getMaximumValue());
            if (this.interacting) {
                this.value = (float) Math.round(((double) f4 + (double) ((float) mouseX - (float) (this.x + 180) * this.scale) * ((double) (f5 - f4) / (d * (double) this.scale))) * (double) 100) / (float) 100;
                if (this.setting.getType().equals(SettingType.INTEGER) || Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
                    this.value = Math.round(this.value);
                }
                if (this.value < f4) {
                    this.value = f4;
                } else if (this.value > f5) {
                    this.value = f5;
                }

                switch (this.setting.getType()) {
                    case INTEGER:
                        this.setting.setValue(Integer.parseInt((int) this.value + ""));
                        break;
                    case FLOAT:
                        this.setting.setValue(this.value);
                        break;
                    case DOUBLE:
                        this.setting.setValue(Double.parseDouble(this.value + ""));
                }
            }
            f3 = (f3 = Float.parseFloat(this.setting.getValue() + "")) < this.settingValue ? this.settingValue - f3 : (f3 -= this.settingValue);
            float f6 = ((f5 - f4) / (float) 20 + f3 * (float) 8) / (float) (Minecraft.debugFPS + 1);
            if ((double) f6 < (double) 0.18f * 5.555555334797621E-4) {
                f6 = 2.1333334f * 4.6874997E-5f;
            }
            if (this.settingValue < (f2 = Float.parseFloat(this.setting.getValue() + ""))) {
                this.settingValue = this.settingValue + f6 <= f2 ? (this.settingValue += f6) : f2;
            } else if (this.settingValue > f2) {
                this.settingValue = this.settingValue - f6 >= f2 ? (this.settingValue -= f6) : f2;
            }
            double d2 = (float) 100 * ((this.settingValue - f4) / (f5 - f4));
            RenderUtil.drawRoundedRect(this.x + 174, this.y + yPos2, (double) (this.x + 180) + d * d2 / (double) 100, this.y + yPos2 + 2, 4, -12418828);
            GL11.glColor4f(0.6666667f * 0.375f, 0.45f, 1.0f, 1.0f);
            RenderUtil.drawCircle((double) ((float) this.x + 1359.3749f * 0.13333334f) + d * d2 / (double) 100, (float) this.y + yPos3, 31.125001159496648 * 0.14457830786705017);
            GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
            RenderUtil.drawCircle((double) ((float) this.x + 39.5f * 4.588608f) + d * d2 / (double) 100, (float) this.y + yPos3, 1.6875000046566129 * (double) 1.6f);
            this.drawDescription(this.setting, mouseX, mouseY);
        }
    }
}
