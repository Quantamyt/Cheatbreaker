package com.cheatbreaker.client.module.impl.normal.hud.simple.module;

import com.cheatbreaker.client.module.data.CustomizationLevel;
import com.cheatbreaker.client.module.data.Setting;
import com.cheatbreaker.client.module.impl.normal.hud.simple.AbstractSimpleHudModule;
import net.minecraft.src.Config;
import net.minecraft.util.ResourceLocation;

/**
 * @Module - ModuleServerAddress
 * @see AbstractSimpleHudModule
 *
 * This module shows information about the current server you're on.
 */
public class SimpleModuleServerAddress extends AbstractSimpleHudModule {
    private Setting hideinSingleplayer;

    public SimpleModuleServerAddress() {
        super("Server Address", "[minehq.com]");
        setDescription("Displays the server you are connected to.");
    }


    @Override
    public String getValueString() {
        if (!this.mc.isIntegratedServerRunning() && this.mc.theWorld != null) {
            return mc.currentServerData.serverIP;
        } else if (!(Boolean) hideinSingleplayer.getValue()) {
            return "Singleplayer";
        }
        return null;
    }

    public String getPreviewString() {
        if (!mc.isSingleplayer()) {
            return mc.currentServerData.serverIP;
        }
        return "minehq.com";
    }

    @Override
    public String getLabelString() {
        return "IP";
    }

    public String customString() {
        return "%VALUE%";
    }

    public boolean includeIcon() {
        return true;
    }

    public ResourceLocation getIconTexture() {
        if (!this.mc.isIntegratedServerRunning() && this.mc.theWorld != null) {
            return new ResourceLocation("servers/" + getValueString() + "/icon");
        } else if (!(Boolean) hideinSingleplayer.getValue()) {
            if (Config.MC_VERSION.equals("1.7.10")) {
                return new ResourceLocation("textures/misc/unknown_pack.png");
            } else {
                return new ResourceLocation("textures/misc/unknown_server.png");
            }
        }
        return new ResourceLocation("client/icons/servers/" + getPreviewString() + ".png");
    }

    public void getExtraSettings() {
        this.hideinSingleplayer = new Setting(this, "Hide in Singleplayer", "Hides the mod in Singleplayer.").setValue(false).setCustomizationLevel(CustomizationLevel.MEDIUM);
    }
}

