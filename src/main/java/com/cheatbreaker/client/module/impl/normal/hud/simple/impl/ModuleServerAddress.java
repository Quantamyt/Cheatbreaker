package com.cheatbreaker.client.module.impl.normal.hud.simple.impl;

import com.cheatbreaker.client.module.data.CustomizationLevel;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.module.impl.normal.hud.simple.AbstractSimpleHudModule;
import com.cheatbreaker.client.module.impl.normal.hud.simple.data.SimpleHudModSize;
import net.minecraft.util.ResourceLocation;

/**
 * Displays the address of the current connected server.
 * @see AbstractSimpleHudModule
 */
public class ModuleServerAddress extends AbstractSimpleHudModule {
    private Setting hideInSingleplayer;

    public ModuleServerAddress() {
        super("Server Address", "[minehq.com]");
        this.setDescription("Displays the address of the current connected server.");
    }

    public String getValueString() {
        if (!this.mc.isIntegratedServerRunning() && this.mc.theWorld != null) {
            return mc.currentServerData.serverIP;
        } else if (!this.hideInSingleplayer.getBooleanValue()) {
            return "Singleplayer";
        }
        return null;
    }

    public String getPreviewString() {
        if (!this.mc.isSingleplayer()) {
            return mc.currentServerData.serverIP;
        }
        return "minehq.com";
    }

    public String getLabelString() {
        return "IP";
    }

    public String getDefaultFormatString() {
        return "%VALUE%";
    }

    public SimpleHudModSize backgroundDimensionValues() {
        return new SimpleHudModSize(10, 16, 64, 40, 56, 80);
    }

    public ResourceLocation getIconTexture() {
        if (!this.mc.isIntegratedServerRunning() && this.mc.theWorld != null) {
            return new ResourceLocation("servers/" + getValueString() + "/icon");
        } else if (!(Boolean) hideInSingleplayer.getValue()) {
            return new ResourceLocation("textures/misc/unknown_server.png");
        }
        return new ResourceLocation("client/icons/servers/" + getPreviewString() + ".png");
    }

    public void getExtraSettings() {
        this.hideInSingleplayer = new Setting(this, "Hide in Singleplayer", "Hides the mod in Singleplayer.").setValue(false).setCustomizationLevel(CustomizationLevel.MEDIUM);
    }
}

