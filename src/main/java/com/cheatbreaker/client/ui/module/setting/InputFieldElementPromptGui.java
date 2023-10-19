package com.cheatbreaker.client.ui.module.setting;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.module.ModuleManager;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.ui.element.module.ModulePreviewElement;
import com.cheatbreaker.client.ui.module.HudLayoutEditorGui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class InputFieldElementPromptGui extends GuiScreen {
    private final GuiScreen selectedButton;

    private GuiTextField textField = null;

    private final float scale;

    private boolean showGui;

    private Setting setting;

    public InputFieldElementPromptGui(Setting setting, GuiScreen guiScreen, float scale) {
        this(guiScreen, scale);
        this.setting = setting;
    }

    public InputFieldElementPromptGui(GuiScreen guiScreen, float scale) {
        this.selectedButton = guiScreen;
        this.scale = scale;
        this.showGui = true;
    }

    @Override
    public void updateScreen() {
        this.textField.updateCursorCounter();
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    @Override
    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        if (!this.showGui) {
            this.mc.displayGuiScreen(this.selectedButton);
        } else {
            this.showGui = false;
            this.textField = new GuiTextField(299, this.mc.fontRendererObj, this.width / 2 - 70, this.height / 2 - 6, 140, 10);
            if (this.setting != null) {
                this.textField.setText(this.setting.getStringValue());
            }
            this.textField.setFocused(true);
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.selectedButton.drawScreen(mouseX, mouseY, partialTicks);
        this.drawDefaultBackground();

        drawRect(this.width / 2 - 73, this.height / 2 - 19, this.width / 2 + 73, this.height / 2 + 8, -11250604);
        drawRect(this.width / 2 - 72, this.height / 2 - 18, this.width / 2 + 72, this.height / 2 + 7, -3881788);

        GL11.glPushMatrix();
        GL11.glScalef(this.scale, this.scale, this.scale);

        int n3 = (int) ((float) this.width / this.scale);
        int n4 = (int) ((float) this.height / this.scale);
        CheatBreaker.getInstance().ubuntuMedium16px.drawString(this.setting.getSettingName(), (float) (n3 / 2) - (float) 70 / this.scale, (float) (n4 / 2) - (float) 17 / this.scale, 0x6F000000);

        GL11.glPopMatrix();

        this.textField.setMaxStringLength(64);
        this.textField.drawTextBox();
    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);
        this.textField.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    public void mouseReleased(int n, int n2, int n3) {
        super.mouseReleased(n, n2, n3);
    }

    @Override
    public void keyTyped(char c, int n) {
        ModuleManager moduleManager = CheatBreaker.getInstance().getModuleManager();

        switch (n) {
            case 1:
                this.mc.displayGuiScreen(this.selectedButton);
                ((HudLayoutEditorGui)this.selectedButton).currentScrollableElement = ((HudLayoutEditorGui)this.selectedButton).modulesElement;
                CheatBreaker.getInstance().getUiManager().sendBackToModList(moduleManager.currentModule);
                break;

            case 28:
                this.setting.setValue(this.textField.getText());
                this.mc.displayGuiScreen(this.selectedButton);
                CheatBreaker.getInstance().getModuleManager().notificationsMod.send("info", EnumChatFormatting.GREEN + "Updated custom string value successfully.", 5000L);
                ((HudLayoutEditorGui)this.selectedButton).currentScrollableElement = ((HudLayoutEditorGui)this.selectedButton).modulesElement;
                CheatBreaker.getInstance().getUiManager().sendBackToModList(moduleManager.currentModule);
                break;

            default:
                this.textField.textboxKeyTyped(c, n);
        }
    }
}

