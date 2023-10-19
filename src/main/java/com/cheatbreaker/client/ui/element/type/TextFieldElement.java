package com.cheatbreaker.client.ui.element.type;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.config.GlobalSettings;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.ui.element.AbstractModulesGuiElement;
import com.cheatbreaker.client.ui.element.module.ModulesGuiButtonElement;
import com.cheatbreaker.client.ui.module.HudLayoutEditorGui;
import com.cheatbreaker.client.ui.module.setting.InputFieldElementPromptGui;
import com.cheatbreaker.client.ui.theme.CBTheme;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class TextFieldElement extends AbstractModulesGuiElement {

    @Getter
    private final ModulesGuiButtonElement keybindButton;
    private boolean usingKeybind = false;
    private boolean showKeybind = false;
    @Getter private final InputFieldElement textInputBar;

    public TextFieldElement(Setting setting, float scaleFactor) {
        super(scaleFactor);
        this.setting = setting;
        this.textInputBar = new InputFieldElement(CheatBreaker.getInstance().playBold18px, this.setting.getValue().toString(), 0, 0);
        this.textInputBar.setMaxStringLength(256);
        this.keybindButton = new ModulesGuiButtonElement(CheatBreaker.getInstance().playBold18px, null, setting.isHasMouseBind() ? "Button " + (setting.getKeyCode() + 1) : Keyboard.getKeyName(setting.getKeyCode()), this.x + this.width - 100, this.y, 50, 18, -9442858, scale, false);
        this.height = 18;

        if (setting.getSettingName().startsWith("Hot key")) {
            this.showKeybind = true;
        }
    }

    public void setDimensions(int x, int y, int width, int height) {
        super.setDimensions(x, y, width, height);
        this.textInputBar.setElementSize(this.x + (showKeybind ? 65 : 150), y + height - 14.0f, 166, 13.0f);
    }

    @Override
    public void handleDrawElement(int mouseX, int mouseY, float partialTicks) {
        if (this.usingKeybind && this.showKeybind && Keyboard.getEventKeyState()) {
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0f));
            CheatBreaker.getInstance().getModuleManager().autoTextMod.setActiveKeybindName(this.setting.getSettingName());

            for (Setting setting : CheatBreaker.getInstance().getModuleManager().autoTextMod.hotkeys) {
                // Check if the key bind is already bound to another.
                if (Keyboard.getEventKey() == setting.getKeyCode() || CheatBreaker.getInstance().getModuleManager().isBoundToAnother(this.setting, Keyboard.getEventKey())) {
                    this.clearKeybind();
                    break;
                }
            }

            // Backspace clears the key bind.
            if (Keyboard.getEventKey() == 14) {
                this.clearKeybind();
            }

            // Set the key bind.
            if (!this.keybindButton.optionString.equals("NONE")) {
                if (this.keybindButton.conflict)
                    this.keybindButton.setConflict(false);

                this.setting.setHasMouseBind(false);
                this.setting.setKeyCode(Keyboard.getEventKey());
                this.keybindButton.optionString = Keyboard.getKeyName(this.setting.getKeyCode());
                this.usingKeybind = false;
                CheatBreaker.getInstance().getModuleManager().autoTextMod.setActiveKeybindName("");
            }
        }

        if (this.usingKeybind && this.showKeybind && Mouse.getEventButton() != 0 && Mouse.getEventButtonState()) {
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0f));
            CheatBreaker.getInstance().getModuleManager().autoTextMod.setActiveKeybindName(this.setting.getSettingName());

            for (Setting setting : CheatBreaker.getInstance().getModuleManager().autoTextMod.hotkeys) {
                // Check if the key bind is already bound to another.
                if (Mouse.getEventButton() == setting.getKeyCode() || CheatBreaker.getInstance().getModuleManager().isBoundToAnother(this.setting, Mouse.getEventButton())) {
                    this.keybindButton.optionString = "NONE";
                    CheatBreaker.getInstance().getModuleManager().autoTextMod.setActiveKeybindName("");
                    this.setting.setHasMouseBind(false);
                    this.setting.setKeyCode(0);
                    this.usingKeybind = false;
                    break;
                }
            }

            // Backspace clears the key bind.
            if (Keyboard.getEventKey() == 14) {
                this.keybindButton.optionString = "NONE";
                CheatBreaker.getInstance().getModuleManager().autoTextMod.setActiveKeybindName("");
                this.setting.setHasMouseBind(false);
                this.setting.setKeyCode(0);
                this.usingKeybind = false;
            }

            // Set the key bind.
            if (!this.keybindButton.optionString.equals("NONE")) {
                if (this.keybindButton.conflict)
                    this.keybindButton.setConflict(false);
                this.setting.setKeyCode(Mouse.getEventButton());
                this.setting.setHasMouseBind(true);
                this.keybindButton.optionString = "Button " + (this.setting.getKeyCode() + 1);
                this.usingKeybind = false;
                CheatBreaker.getInstance().getModuleManager().autoTextMod.setActiveKeybindName("");
            }
        }

        if (this.showKeybind) {
            this.keybindButton.yOffset = this.yOffset;
            this.keybindButton.setDimensions(this.x + this.width - 110, this.y, 96, 18);
            this.keybindButton.handleDrawElement(mouseX, mouseY, partialTicks);
        }

        CheatBreaker.getInstance().ubuntuMedium16px.drawString(this.setting.getSettingName().toUpperCase(), this.x + 10, (float) (this.y + 4), GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkTextColor2 : CBTheme.lightTextColor2);
        Gui.drawRect(this.x + (showKeybind ? 65 : 148), this.y + 16, this.x + this.width - (showKeybind ? 120 : 16), this.y + 17, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkTextColor2 : CBTheme.lightTextColor2);

        this.textInputBar.setTextColor(GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkTextColor2 : CBTheme.lightTextColor2);
        this.textInputBar.setEnableBackgroundDrawing(false);
        this.textInputBar.drawElementHover(mouseX, mouseY, true);

        drawDescription(this.setting, mouseX, mouseY);
        handleElementUpdate();
    }

    private void clearKeybind() {
        CheatBreaker.getInstance().getModuleManager().autoTextMod.setActiveKeybindName("");
        this.keybindButton.optionString = "NONE";
        this.setting.setHasMouseBind(false);
        this.setting.setKeyCode(0);
        this.usingKeybind = false;
    }

    @Override
    public void handleElementUpdate() {
        this.textInputBar.handleElementUpdate();
    }

    @Override
    public void handleElementClose() {
        this.textInputBar.handleElementClose();
    }

    @Override
    public void keyTyped(char c, int n) {

        if (n == 1) {
            this.textInputBar.setFocused(false);
            return;
        }

        super.keyTyped(c, n);
        this.textInputBar.keyTyped(c, n);
    }

    @Override
    public boolean handleElementMouseClicked(float f, float f2, int n, boolean bl) {
        this.handleElementUpdate();
        this.textInputBar.handleElementMouseClicked(f, f2, n, bl);
        return false;
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY, int button) {
        if (this.keybindButton.isMouseInside(mouseX, mouseY) && this.showKeybind) {
            if (!CheatBreaker.getInstance().getModuleManager().autoTextMod.getActiveKeybindName().equals("")) {
                return;
            }

            CheatBreaker.getInstance().getModuleManager().autoTextMod.setActiveKeybindName(this.setting.getSettingName());

            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0f));
            this.usingKeybind = true;
            this.keybindButton.optionString = "<PRESS ANY KEY>";
        }

        boolean hoveringOnTextElement = (float) mouseX > (float) this.x * this.scale && (float) mouseX < (float) (this.x + this.width - (showKeybind ? 120 : 40)) * this.scale && (float) mouseY > (float) (this.y + this.yOffset) * this.scale && (float) mouseY < (float) (this.y + 18 + this.yOffset) * this.scale;
        if (hoveringOnTextElement) {
            Minecraft.getMinecraft().displayGuiScreen(new InputFieldElementPromptGui(this.setting, HudLayoutEditorGui.instance, this.scale));
            Minecraft.getMinecraft().getSoundHandler().playSound(PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0f));
        }
    }
}