package com.cheatbreaker.client.ui.element.type;


import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.config.GlobalSettings;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.ui.element.AbstractModulesGuiElement;
import com.cheatbreaker.client.ui.module.HudLayoutEditorGui;
import com.cheatbreaker.client.ui.theme.CBTheme;
import com.cheatbreaker.client.ui.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ChoiceElement extends AbstractModulesGuiElement {
//    private final Setting setting;
    private final ResourceLocation leftArrowIcon = new ResourceLocation("client/icons/left.png");
    private final ResourceLocation rightArrowIcon = new ResourceLocation("client/icons/right.png");
    private int optionValueIndex = 0; // From what I can tell, this only matters in terms of showing the new option value
    private float animationSpeed = 0.0f;
    private String optionValue;

    public ChoiceElement(Setting setting, float scaleFactor) {
        super(scaleFactor);
        this.setting = setting;
        this.height = 12;
//        if (this.shouldHide(this.setting)) {
//            this.height = 0;
//        } else {
//            this.height = 12;
//        }
    }

    @Override
    public void handleDrawElement(int mouseX, int mouseY, float partialTicks) {
//        if (!this.shouldHide(this.setting)) {
            boolean yrrr = (float)mouseY > (float)(this.y + this.yOffset) * this.scale && (float)mouseY < (float)(this.y + 10 + this.yOffset) * this.scale;
            boolean isXHovering = (float) mouseX > (float)(this.x + this.width - 48) * this.scale && (float) mouseX < (float)(this.x + this.width - 10) * this.scale && yrrr;
            boolean isYHovering = (float) mouseX > (float)(this.x + this.width - 92) * this.scale && (float) mouseX < (float)(this.x + this.width - 48) * this.scale && yrrr;
            CheatBreaker.getInstance().ubuntuMedium16px.drawString(this.setting.getSettingName().toUpperCase(), this.x + 10, (float)(this.y + 2), isYHovering || isXHovering ? GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkTextColor3 : CBTheme.lightTextColor3 : GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkTextColor2 : CBTheme.lightTextColor2);
            boolean endsWithColor = this.setting.getSettingName().toLowerCase().endsWith("color");

//            if (this.optionValue != null) {
//                System.out.println(this.optionValueIndex);
//                System.out.println(this.optionValue);
//            }

            if (!endsWithColor) {
                if (this.optionValueIndex == 0) {
                    CheatBreaker.getInstance().ubuntuMedium16px.drawCenteredString((String)this.setting.getValue(), this.x + this.width - 48, this.y + 2, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkTextColor2 : CBTheme.lightTextColor2);
                } else {
                    boolean valIsOne = this.optionValueIndex == 1;
                    CheatBreaker.getInstance().ubuntuMedium16px.drawCenteredString(this.optionValue, (float)(this.x + this.width - 48) - (valIsOne ? -this.animationSpeed : this.animationSpeed), this.y + 2, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkTextColor2 : CBTheme.lightTextColor2);

                    if (valIsOne) {
                        CheatBreaker.getInstance().ubuntuMedium16px.drawCenteredString((String)this.setting.getValue(), (float)(this.x + this.width - 98) + this.animationSpeed, this.y + 2, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkTextColor2 : CBTheme.lightTextColor2);
                    } else {
                        CheatBreaker.getInstance().ubuntuMedium16px.drawCenteredString((String)this.setting.getValue(), (float)(this.x + this.width + 2) - this.animationSpeed, this.y + 2, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkTextColor2 : CBTheme.lightTextColor2);
                    }

                    if (this.animationSpeed >= (float)50) {
                        this.optionValueIndex = 0;
                        this.animationSpeed = 0.0f;
                    } else {
                        float speed = HudLayoutEditorGui.getFPSTransitionSpeed((float)50 + this.animationSpeed * (float)15);
                        this.animationSpeed = Math.min(this.animationSpeed + speed, (float) 50);
                    }

                    Gui.drawRect(this.x + this.width - 130, this.y + 2, this.x + this.width - 72, this.y + 12, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkBackgroundColor4 : CBTheme.lightBackgroundColor);
                    Gui.drawRect(this.x + this.width - 22, this.y + 2, this.x + this.width + 4, this.y + 12, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkBackgroundColor4 : CBTheme.lightBackgroundColor);
                }
            } else if (this.optionValueIndex == 0) {
                float f3 = CheatBreaker.getInstance().ubuntuMedium16px.getStringWidth((String)this.setting.getValue());
                CheatBreaker.getInstance().ubuntuMedium16px.drawString((String)this.setting.getValue(), (float)(this.x + this.width) - 44.738373f * 1.0617284f - f3 / 2.0f, (float)this.y + 1.74f * 1.4367816f, -16777216);
                CheatBreaker.getInstance().ubuntuMedium16px.drawString("§" + this.setting.getValue() + this.setting.getValue(), (float)(this.x + this.width - 48) - f3 / 2.0f, (float)(this.y + 2), -16777216);
            } else {
                boolean bl5 = this.optionValueIndex == 1;
                CheatBreaker.getInstance().ubuntuMedium16px.drawCenteredString(this.optionValue, (float)(this.x + this.width - 48) - (bl5 ? -this.animationSpeed : this.animationSpeed), this.y + 2, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkTextColor2 : CBTheme.lightTextColor2);
                float f4 = CheatBreaker.getInstance().ubuntuMedium16px.getStringWidth((String)this.setting.getValue());
                if (bl5) {
                    CheatBreaker.getInstance().ubuntuMedium16px.drawString((String)this.setting.getValue(), (float)(this.x + this.width) - 110.21739f * 0.88461536f - f4 / 2.0f + this.animationSpeed, (float)this.y + 0.8095238f * 3.0882351f, -16777216);
                    CheatBreaker.getInstance().ubuntuMedium16px.drawString("§" + this.setting.getValue() + this.setting.getValue(), (float)(this.x + this.width - 98) - f4 / 2.0f + this.animationSpeed, (float)(this.y + 2), -16777216);
                } else {
                    CheatBreaker.getInstance().ubuntuMedium16px.drawString((String)this.setting.getValue(), (float)(this.x + this.width) - 2.6296296f * 0.57042253f - f4 / 2.0f - this.animationSpeed, (float)this.y + 0.040983606f * 61.0f, -16777216);
                    CheatBreaker.getInstance().ubuntuMedium16px.drawString("§" + this.setting.getValue() + this.setting.getValue(), (float)(this.x + this.width - 2) - f4 / 2.0f - this.animationSpeed, (float)(this.y + 2), -16777216);
                }
                if (this.animationSpeed >= (float)50) {
                    this.optionValueIndex = 0;
                    this.animationSpeed = 0.0f;
                } else {
                    float animationSpeed = HudLayoutEditorGui.getFPSTransitionSpeed((float)50 + this.animationSpeed * (float)15);
                    this.animationSpeed = Math.min(this.animationSpeed + animationSpeed, (float) 50);
                }
                Gui.drawRect(this.x + this.width - 130, this.y + 2, this.x + this.width - 72, this.y + 12, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkBackgroundColor4 : CBTheme.lightBackgroundColor);
                Gui.drawRect(this.x + this.width - 22, this.y + 2, this.x + this.width + 4, this.y + 12, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkBackgroundColor4 : CBTheme.lightBackgroundColor);
            }

            float darkOrLight = GlobalSettings.darkMode.getBooleanValue() ? 1.0F : 0.0F;
            GL11.glColor4f(darkOrLight, darkOrLight, darkOrLight, isYHovering ? 0.6857143f * 1.1666666f : 0.5416667f * 0.8307692f);
            RenderUtil.renderIcon(this.leftArrowIcon, (float)4, (float)(this.x + this.width - 82), (float)(this.y + 3));
            GL11.glColor4f(darkOrLight, darkOrLight, darkOrLight, isXHovering ? 0.82580644f * 0.96875f : 3.3793104f * 0.13316326f);
            RenderUtil.renderIcon(this.rightArrowIcon, (float)4, (float)(this.x + this.width - 22), (float)(this.y + 3));
            drawDescription(this.setting, mouseX, mouseY);
//        }
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY, int button) {
        boolean bl2 = (float) mouseX > (float)(this.x + this.width - 48) * this.scale && (float) mouseX < (float)(this.x + this.width - 10) * this.scale && (float) mouseY > (float)(this.y + this.yOffset) * this.scale && (float) mouseY < (float)(this.y + 10 + this.yOffset) * this.scale;
        boolean bl = (float) mouseX > (float)(this.x + this.width - 92) * this.scale && (float) mouseX < (float)(this.x + this.width - 48) * this.scale && (float) mouseY > (float)(this.y + this.yOffset) * this.scale && (float) mouseY < (float)(this.y + 10 + this.yOffset) * this.scale;
        if ((bl || bl2) && this.optionValueIndex == 0) {
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
            for (int i = 0; i < this.setting.getAcceptedStringValues().length; ++i) {
                if (!((String[])this.setting.getAcceptedStringValues())[i].toLowerCase().equalsIgnoreCase((String)this.setting.getValue())) continue;
                this.optionValue = (String)this.setting.getValue();
                if (bl2) {
                    if (i + 1 >= this.setting.getAcceptedStringValues().length) {
                        this.optionValueIndex = 2;
                        this.setting.setValue(((String[])this.setting.getAcceptedStringValues())[0]);
                        break;
                    }
                    this.optionValueIndex = 2;
                    this.setting.setValue(((String[])this.setting.getAcceptedStringValues())[i + 1]);
                    break;
                }
                if (!bl) continue;
                if (i - 1 < 0) {
                    this.optionValueIndex = 1;
                    this.setting.setValue(((String[])this.setting.getAcceptedStringValues())[this.setting.getAcceptedStringValues().length - 1]);
                    break;
                }
                this.optionValueIndex = 1;
                this.setting.setValue(((String[])this.setting.getAcceptedStringValues())[i - 1]);
                break;
            }
        }
    }
}
