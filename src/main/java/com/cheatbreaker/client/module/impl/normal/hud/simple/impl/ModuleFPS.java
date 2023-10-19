package com.cheatbreaker.client.module.impl.normal.hud.simple.impl;

import com.cheatbreaker.client.module.data.CustomizationLevel;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.module.impl.normal.hud.simple.AbstractSimpleHudModule;
import com.cheatbreaker.client.module.impl.normal.hud.simple.data.SimpleHudModExclusionRange;
import net.minecraft.client.Minecraft;
import net.minecraft.src.Config;

/**
 * Displays the player's frames per second.
 * @see AbstractSimpleHudModule
 */
public class ModuleFPS extends AbstractSimpleHudModule {
    public Setting minFPS;

    public ModuleFPS() {
        super("FPS",  "[144 FPS]");
        this.setDescription("Displays your frames per second.");
    }

    public String getValueString() {
        if (this.excludeArrayOptions(this.hideValue, Minecraft.debugFPS, this.hiddenValue.getIntegerValue())) {
            return null;
        }
        return this.getPreviewString();
    }

    public String getPreviewString() {
        return Minecraft.debugFPS + (this.minFPS.getBooleanValue() ? "/" + Config.getFpsMin() : "");
    }

    public String getLabelString() {
        return "FPS";
    }

    public void getExtraSettings() {
        this.minFPS = new Setting(this, "Show Minimum FPS").setValue(false).setCustomizationLevel(CustomizationLevel.SIMPLE);
    }

    public SimpleHudModExclusionRange exclusionRange() {
        return new SimpleHudModExclusionRange(0, 1000, 3000);
    }
}
