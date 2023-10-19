package com.cheatbreaker.client.module.impl.normal.hud.simple.module;

import com.cheatbreaker.client.module.impl.normal.hud.simple.AbstractSimpleHudModule;

/**
 * @Module - ModuleMemoryUsage
 * @see AbstractSimpleHudModule
 *
 * This module shows how much memory is being used by the game.
 */
public class SimpleModuleMemoryUsage extends AbstractSimpleHudModule {

    public SimpleModuleMemoryUsage() {
        super("Memory Usage", "[Mem: 19%]");
        setDescription("Displays how much memory the game is using.");
    }

    public String getValueString() {
        return (Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()) * 100L / Runtime.getRuntime().maxMemory() + "%";
    }

    public String getLabelString() {
        return "Mem";
    }

    public String customString() {
        return "%LABEL%: %VALUE%";
    }
}
