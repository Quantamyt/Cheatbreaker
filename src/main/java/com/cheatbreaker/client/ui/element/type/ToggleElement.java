package com.cheatbreaker.client.ui.element.type;

import com.cheatbreaker.client.config.GlobalSettings;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.ui.element.AbstractModulesGuiElement;
import com.cheatbreaker.client.ui.module.HudLayoutEditorGui;
import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.ui.theme.CBTheme;
import com.cheatbreaker.client.ui.util.RenderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ToggleElement extends AbstractModulesGuiElement {
//    private final Setting setting;
    private final ResourceLocation rightArrowIcon = new ResourceLocation("client/icons/left.png");
    private final ResourceLocation leftArrowIcon = new ResourceLocation("client/icons/right.png");
    private int optionValueIndex = 0;
    private float animationSpeed = 0.0f;
    private String optionValue;

    public ToggleElement(Setting setting, float scale) {
        super(scale);
        this.setting = setting;
        this.height = 12;
    }

    @Override
    public void handleDrawElement(int mouseX, int mouseY, float partialTicks) {
        boolean isXHovering = (float) mouseX > (this.x + this.width - 48.0f) * this.scale && (float) mouseX < (this.x + this.width - 10.0f) * this.scale && (float)mouseY > (float)(this.y + this.yOffset) * this.scale && (float)mouseY < (this.y + 10.0f + this.yOffset) * this.scale;
        boolean isYHovering = (float) mouseX > (this.x + this.width - 92.0f) * this.scale && (float) mouseX < (this.x + this.width - 48.0f) * this.scale && (float)mouseY > (float)(this.y + this.yOffset) * this.scale && (float)mouseY < (this.y + 10.0f + this.yOffset) * this.scale;
        CheatBreaker.getInstance().ubuntuMedium16px.drawString(this.setting.getSettingName().toUpperCase(), this.x + 10, (float)(this.y + 2), isYHovering || isXHovering ? GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkTextColor3 : CBTheme.lightTextColor3 : GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkTextColor2 : CBTheme.lightTextColor2);

        if (this.optionValueIndex == 0) {
            CheatBreaker.getInstance().ubuntuMedium16px.drawCenteredString((Boolean) this.setting.getValue() ? "ON" : "OFF", this.x + this.width - 48, this.y + 2, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkTextColor2 : CBTheme.lightTextColor2);
        } else {
            boolean isOne = this.optionValueIndex == 1;
            CheatBreaker.getInstance().ubuntuMedium16px.drawCenteredString(this.optionValue, this.x + this.width - 48.0f - (isOne ? -this.animationSpeed : this.animationSpeed), this.y + 2, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkTextColor2 : CBTheme.lightTextColor2);
            if (isOne) {
                CheatBreaker.getInstance().ubuntuMedium16px.drawCenteredString((Boolean) this.setting.getValue() ? "ON" : "OFF", (float)(this.x + this.width - 98) + this.animationSpeed, this.y + 2, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkTextColor2 : CBTheme.lightTextColor2);
            } else {
                CheatBreaker.getInstance().ubuntuMedium16px.drawCenteredString((Boolean) this.setting.getValue() ? "ON" : "OFF", (float)(this.x + this.width + 2) - this.animationSpeed, this.y + 2, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkTextColor2 : CBTheme.lightTextColor2);
            }

            if (this.animationSpeed >= 50.0f) {
                this.optionValueIndex = 0;
                this.animationSpeed = 0.0f;
            } else {
                float f2 = HudLayoutEditorGui.getFPSTransitionSpeed(50.0f + this.animationSpeed * 15.0f);
                this.animationSpeed = Math.min(this.animationSpeed + f2, 50.0f);
            }

            Gui.drawRect(this.x + this.width - 130, this.y + 2, this.x + this.width - 72, this.y + 12, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkBackgroundColor4 : CBTheme.lightBackgroundColor);
            Gui.drawRect(this.x + this.width - 22, this.y + 2, this.x + this.width + 4, this.y + 12, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkBackgroundColor4 : CBTheme.lightBackgroundColor);
        }

        float darkOrLight = GlobalSettings.darkMode.getBooleanValue() ? 1.0F : 0.0F;
        GL11.glColor4f(darkOrLight, darkOrLight, darkOrLight, isYHovering ? 0.8f : 0.45f);
        RenderUtil.renderIcon(this.rightArrowIcon, 4.0f, this.x + this.width - 82.0f, this.y + 3.0f);
        GL11.glColor4f(darkOrLight, darkOrLight, darkOrLight, isXHovering ? 0.8f : 0.45f);
        RenderUtil.renderIcon(this.leftArrowIcon, 4.0f, this.x + this.width - 22.0f, this.y + 3.0f);
        drawDescription(this.setting, mouseX, mouseY);
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY, int button) {
        boolean bl = (float) mouseX > (this.x + this.width - 92.0f) * this.scale && (float) mouseX < (this.x + this.width - 48.0f) * this.scale && (float) mouseY > (float)(this.y + this.yOffset) * this.scale && (float) mouseY < (this.y + 10.0f + this.yOffset) * this.scale;
        boolean bl2 = (float) mouseX > (this.x + this.width - 48.0f) * this.scale && (float) mouseX < (this.x + this.width - 10.0f) * this.scale && (float) mouseY > (float)(this.y + this.yOffset) * this.scale && (float) mouseY < (this.y + 10.0f + this.yOffset) * this.scale;

        if ((bl || bl2) && this.optionValueIndex == 0) {
            this.optionValueIndex = bl ? 1 : 2;
            this.animationSpeed = 0.0f;
            this.optionValue = (Boolean) this.setting.getValue() ? "ON" : "OFF";
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.func_147674_a(new ResourceLocation("gui.button.press"), 1.0f));
            this.setting.setValue(!((Boolean) this.setting.getValue()));
            if (this.setting == CheatBreaker.getInstance().getGlobalSettings().enableTeamView && !(Boolean) CheatBreaker.getInstance().getGlobalSettings().enableTeamView.getValue()) {
                CheatBreaker.getInstance().getModuleManager().teammatesMod.setEnabled(false);
            }
        }
    }
}
