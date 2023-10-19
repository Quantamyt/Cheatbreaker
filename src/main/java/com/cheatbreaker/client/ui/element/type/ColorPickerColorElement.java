package com.cheatbreaker.client.ui.element.type;

import com.cheatbreaker.client.config.GlobalSettings;
import com.cheatbreaker.client.ui.element.AbstractModulesGuiElement;
import com.cheatbreaker.client.ui.theme.CBTheme;
import net.minecraft.client.gui.Gui;

public class ColorPickerColorElement extends AbstractModulesGuiElement {
    public int color;
    private final float scaleFactor;

    public ColorPickerColorElement(float scaleFactor, int color, float scale) {
        super(scaleFactor);
        this.color = color;
        this.scaleFactor = scale;
    }

    @Override
    public void handleDrawElement(int mouseX, int n2, float partialTicks) {
        Gui.drawRectWithOutline(this.x, this.y, this.x + this.width, this.y + this.height, 1, GlobalSettings.darkMode.getBooleanValue() ? CBTheme.darkTextColor4 : CBTheme.lightTextColor4, 0);
        Gui.drawRect(this.x + 1, this.y + 1, this.x + this.width - 1, this.y + this.height - 1, this.color | 0xFF000000);
    }

    @Override
    public void handleMouseClick(int mouseX, int mouseY, int button) {
    }
}
