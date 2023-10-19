package com.cheatbreaker.client.module.impl.normal.hud.simple.module;

import com.cheatbreaker.client.module.impl.normal.hud.simple.AbstractSimpleHudModule;
import net.minecraft.client.Minecraft;

/**
 * @Module - ModuleFPS
 * @see AbstractSimpleHudModule
 *
 * This module shows the current frames per second you're getting within Minecraft.
 *
 * NOTE: We still use Minecraft.debugFPS for this, consider making it all in one central integer.
 */
public class SimpleModuleFPS extends AbstractSimpleHudModule {

    public SimpleModuleFPS() {
        super("FPS", "[144 FPS]");
        setDescription("Displays your frames per second.");
    }

    @Override
    public String getValueString() {
        return "" + Minecraft.debugFPS;
    }

    @Override
    public String getLabelString() {
        return "FPS";
    }
}
