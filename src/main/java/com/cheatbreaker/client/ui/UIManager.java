package com.cheatbreaker.client.ui;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.ModuleManager;
import com.cheatbreaker.client.ui.element.module.ModuleListElement;
import com.cheatbreaker.client.ui.element.module.ModulePreviewElement;
import com.cheatbreaker.client.ui.module.HudLayoutEditorGui;
import net.minecraft.client.Minecraft;

public class UIManager {

    /*
        Sets the user back into the correct module settings menu.
    */
    public void sendBackToModList(String modName) {
        ModuleManager moduleManager = CheatBreaker.getInstance().getModuleManager();

        moduleManager.keystrokesMod.updateKeyElements();
        HudLayoutEditorGui hudEditor = new HudLayoutEditorGui();
        Minecraft.getMinecraft().displayGuiScreen(hudEditor);
        (hudEditor).currentScrollableElement = (hudEditor).modulesElement;
        this.setIntoSettingsElement(moduleManager.getModByName(modName));
    }

    public void sendBackToCBSettings() {
        HudLayoutEditorGui hudEditor = new HudLayoutEditorGui();
        Minecraft.getMinecraft().displayGuiScreen(hudEditor);
        hudEditor.currentScrollableElement = hudEditor.settingsElement;
        ((ModuleListElement) HudLayoutEditorGui.instance.settingsElement).isCheatBreakerSettings = true;
        HudLayoutEditorGui.instance.settingsElement.scrollAmount = CheatBreaker.getInstance().getModuleManager().lastSettingScrollPos;
    }

    /*
        Makes sure the user is going to the right place.
    */
    public void setIntoSettingsElement(AbstractModule module) {
        ((ModuleListElement) HudLayoutEditorGui.instance.settingsElement).isCheatBreakerSettings = false;
        ((ModuleListElement) HudLayoutEditorGui.instance.settingsElement).scrollableElement = ModulePreviewElement.instance.scrollableElement;
        ((ModuleListElement) HudLayoutEditorGui.instance.settingsElement).module = module;
        (HudLayoutEditorGui.instance.settingsElement).scrollAmount = CheatBreaker.getInstance().getModuleManager().lastSettingScrollPos;
        HudLayoutEditorGui.instance.currentScrollableElement = HudLayoutEditorGui.instance.settingsElement;
    }
}
