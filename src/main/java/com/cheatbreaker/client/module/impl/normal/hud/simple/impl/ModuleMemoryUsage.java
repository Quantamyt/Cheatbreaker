package com.cheatbreaker.client.module.impl.normal.hud.simple.impl;

import com.cheatbreaker.client.module.impl.normal.hud.simple.AbstractSimpleHudModule;
import com.cheatbreaker.client.module.impl.normal.hud.simple.data.SimpleHudModExclusionRange;

/**
 * Displays how much memory the game is using.
 * @see AbstractSimpleHudModule
 */
public class ModuleMemoryUsage extends AbstractSimpleHudModule {

    public ModuleMemoryUsage() {
        super("Memory Usage",  "[Mem: 19%]");
        setDescription("Displays how much memory the game is using.");
    }

    public String getValueString() {
        long memory = (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) * 100L / Runtime.getRuntime().maxMemory();
        if (this.excludeArrayOptions(this.hideValue, (int) memory, this.hiddenValue.getIntegerValue())) {
            return null;
        }
        return memory + "%";
    }

    public String getPreviewString() {
        return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) * 100L / Runtime.getRuntime().maxMemory() + "%";
    }

    public String getLabelString() {
        return "Mem";
    }

    public String getDefaultFormatString() {
        return "%LABEL%: %VALUE%";
    }

    public SimpleHudModExclusionRange exclusionRange() {
        return new SimpleHudModExclusionRange(0, 50, 100);
    }
}
