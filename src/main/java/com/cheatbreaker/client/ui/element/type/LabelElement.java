package com.cheatbreaker.client.ui.element.type;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.config.GlobalSettings;
import com.cheatbreaker.client.ui.element.AbstractModulesGuiElement;
import com.cheatbreaker.client.ui.theme.CBTheme;
import net.minecraft.client.gui.Gui;
import com.cheatbreaker.client.module.data.Setting;

public class LabelElement extends AbstractModulesGuiElement {

    public LabelElement(Setting label, float scaleFactor) {
        super(scaleFactor);
        this.setting = label;
        this.height = 12;
    }

    @Override
    public void handleDrawElement(int mouseX, int mouseY, float partialTicks) {
        CheatBreaker.getInstance().ubuntuMedium16px.drawString(((String)this.setting.getValue()).toUpperCase(), this.x + 2, (float)(this.y + 2), GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkTextColor : CBTheme.lightTextColor);
        Gui.drawRect(this.x + 2, this.y + this.height - 1, this.x + this.width / 2 - 20, this.y + this.height, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkDullTextColor3 : CBTheme.lightDullTextColor3);
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY, int button) {
    }
}
