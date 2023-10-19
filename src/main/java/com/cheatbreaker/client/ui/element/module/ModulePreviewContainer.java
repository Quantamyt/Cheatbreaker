package com.cheatbreaker.client.ui.element.module;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.config.GlobalSettings;
import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.ui.element.AbstractScrollableElement;
import com.cheatbreaker.client.ui.theme.CBTheme;
import com.cheatbreaker.client.ui.util.RenderUtil;
import com.cheatbreaker.client.ui.util.font.CBFontRenderer;

import java.util.ArrayList;
import java.util.List;

public class ModulePreviewContainer extends AbstractScrollableElement {
    private final List<ModulePreviewElement> moduleList = new ArrayList<>();
//    private final ModulesGuiButtonElement compactModeButton;

    public ModulePreviewContainer(float scale, int n, int n2, int n3, int n4) {
        super(scale, n, n2, n3, n4);
        for (AbstractModule mods : CheatBreaker.getInstance().getModuleManager().playerMods) {
            if (mods == CheatBreaker.getInstance().getModuleManager().notificationsMod
                    || mods == CheatBreaker.getInstance().getModuleManager().miniMapMod
//                    || mods == CheatBreaker.getInstance().getModuleManager().playerListMod
            ) continue;
            ModulePreviewElement modulePreviewElement = new ModulePreviewElement(this, mods, scale);
            this.moduleList.add(modulePreviewElement);
        }

//        CBFontRenderer boldFont = CheatBreaker.getInstance().playBold18px;
//        this.compactModeButton = new ModulesGuiButtonElement(boldFont, null, "compact-64.png", this.x + 4, this.y + this.height - 20, this.x + this.width - 4, this.y + this.height - 6, -12418828, scale);
    }

    @Override
    public boolean hasSettings(AbstractModule module) {
        return false;
    }

    @Override
    public void handleModuleMouseClick(AbstractModule abstractModule) {
    }

    @Override
    public void handleDrawElement(int mouseX, int mouseY, float partialTicks) {
        super.handleDrawElement(mouseX, mouseY, partialTicks);

        boolean compact = CheatBreaker.getInstance().getGlobalSettings().compactMode.getValue().equals("Compact");
        int multiplier = (compact ? 20 : 112);
        int yIncreease = 0/*25*/;

        RenderUtil.drawRoundedRect(this.x, this.y, this.x + this.width, this.y + this.height + 2, 8, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkBackgroundColor4 : CBTheme.lightBackgroundColor4);
        this.onScroll(mouseX, mouseY);
        int n3 = 0;
        int n4 = 0;
        int addon = 0;
        for (ModulePreviewElement module : this.moduleList) {
            module.yOffset = this.scrollAmount;
            module.setDimensions(this.x + 4 + n3 * 120, this.y + 4 + n4 * multiplier + yIncreease, 116, compact ? 16 : 108);
            module.handleDrawElement(mouseX, mouseY, partialTicks);
            addon = (compact ? 20 : 112);
            if (++n3 != 3) continue;
            ++n4;
            n3 = addon = 0;
        }

        this.scrollHeight = 4 + n4 * multiplier + yIncreease + addon;

//        this.compactModeButton.setDimensions(this.x + 4, this.y + 5,20, 20);
//        this.compactModeButton.setMediumIcon(true);
//        this.compactModeButton.handleDrawElement(mouseX, mouseY, partialTicks);

        this.onGuiDraw(mouseX, mouseY);
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY, int button) {
        super.handleMouseClick(mouseX, mouseY, button);

//        if (this.compactModeButton.isMouseInside(mouseX, mouseY)) {
//            CheatBreaker.getInstance().getGlobalSettings().compactMode.setValue(!CheatBreaker.getInstance().getGlobalSettings().compactMode.getBooleanValue());
//            // This is needed to refresh the menu, sorry.
//            Minecraft.getMinecraft().displayGuiScreen(new HudLayoutEditorGui());
//        }

        for (ModulePreviewElement module : this.moduleList) {
            if (!module.isMouseInside(mouseX, mouseY)) continue;
            module.handleMouseClick(mouseX, mouseY, button);
        }
    }
}
