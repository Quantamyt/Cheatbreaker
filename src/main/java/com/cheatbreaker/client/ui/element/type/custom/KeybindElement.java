package com.cheatbreaker.client.ui.element.type.custom;


import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.config.GlobalSettings;
import com.cheatbreaker.client.ui.element.AbstractModulesGuiElement;
import com.cheatbreaker.client.ui.element.module.ModulesGuiButtonElement;
import com.cheatbreaker.client.ui.theme.CBTheme;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import com.cheatbreaker.client.module.data.Setting;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import javax.management.Notification;
import java.util.Set;

public class KeybindElement extends AbstractModulesGuiElement {
    //    private final Setting setting;
    @Getter private final ModulesGuiButtonElement button;
    private boolean using = false;

    public KeybindElement(Setting setting, float scale) {
        super(scale);
        this.setting = setting;
        this.height = 15;
        this.button = new ModulesGuiButtonElement(CheatBreaker.getInstance().playBold18px, null, setting.isHasMouseBind() ? "Button " + (setting.getIntegerValue() + 1) : Keyboard.getKeyName(setting.getIntegerValue()), this.x + this.width - 100, this.y, 96, 18, -9442858, scale, false);
    }

    @Override
    public void handleDrawElement(int mouseX, int mouseY, float partialTicks) {
        CheatBreaker.getInstance().ubuntuMedium16px.drawString(this.setting.getSettingName().toUpperCase(), this.x + 10, (float)(this.y + 4), GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkTextColor2 : CBTheme.lightTextColor2);

        if (this.using && Keyboard.getEventKeyState()) {
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0f));

            if (Keyboard.getKeyName(Keyboard.getEventKey()).equalsIgnoreCase("Back") || CheatBreaker.getInstance().getModuleManager().isBoundToAnother(this.setting, Keyboard.getEventKey())) {
                this.setting.setValue(0);
                this.button.optionString = "NONE";
                this.setting.setHasMouseBind(false);
                this.using = false;
                return;
            }

            this.setting.setValue(Keyboard.getEventKey());
            this.setting.setHasMouseBind(false);
            this.button.optionString = Keyboard.getKeyName((Integer)this.setting.getValue());
            this.using = false;
        }

        if (this.using && Mouse.getEventButton() != 0 && Mouse.getEventButtonState()) {
            if (CheatBreaker.getInstance().getModuleManager().isBoundToAnother(this.setting, Mouse.getEventButton())) {
                this.setting.setValue(0);
                this.button.optionString = "NONE";
                this.setting.setHasMouseBind(false);
                this.using = false;
                return;
            }

            this.setting.setValue(Mouse.getEventButton());
            this.setting.setHasMouseBind(true);
            this.button.optionString = "Button " + (this.setting.getIntegerValue() + 1);
            this.using = false;
        }

        this.button.yOffset = this.yOffset;
        this.button.setDimensions(this.x + this.width - 110, this.y, 96, 18);
        this.button.handleDrawElement(mouseX, mouseY, partialTicks);
        drawDescription(this.setting, mouseX, mouseY);
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY, int button) {
        if (this.button.isMouseInside(mouseX, mouseY)) {
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0f));
            this.using = true;
            this.button.optionString = "<PRESS ANY KEY>";
        }
    }
}
