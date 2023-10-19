package com.cheatbreaker.client.module.impl.normal.misc;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.module.AbstractModule;
import com.cheatbreaker.client.module.data.Setting;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModuleAutoText extends AbstractModule {

    public final String[] hypixelCommands = new String[]{ "/skyblock", "/play", "/lobby", "/hub", "/spawn", "/main", "/leave", "/warp", "/rejoin" };

    @Setter @Getter private String activeKeybindName = "";
    public List<Setting> hotkeys = new ArrayList<>();
    public List<Long> messageCount = new ArrayList<>();
    public final int maxMessagesPerInterval = 3;

    public int amount = 1;

    public ModuleAutoText() {
        super("Auto Text");
        this.setDefaultState(false);
        this.setPreviewLabel("Auto Text", 1.0F);
        this.hotkeys.addAll(this.getSettingsList());
    }

    public void send(String value) {
        if (Minecraft.getMinecraft().isIntegratedServerRunning()) {
            Minecraft.getMinecraft().thePlayer.sendChatMessage(value);
            return;
        }

        if (Minecraft.getMinecraft().getCurrentServerData().serverIP.contains("hypixel")) {
            if (this.shouldBeRestricted(value)) {
                Minecraft.getMinecraft().thePlayer.sendChatMessage(value);
            } else {
                CheatBreaker.getInstance().getModuleManager().notificationsMod.send("error", EnumChatFormatting.RED + "\"" + value +"\" is not allowed on Hypixel.", 3000L);
            }
        } else {
            Minecraft.getMinecraft().thePlayer.sendChatMessage(value);
        }
    }

    /*
        Checks to make sure the command is allowed by Hypixel based on Badlion Client's Auto Text whitelist system.
    */
    public boolean shouldBeRestricted(String command) {
        for (String hypixelCommand : this.hypixelCommands) {
            if (command.startsWith(hypixelCommand)) {
                return true;
            }
        }
        return false;
    }

    public void addOne() {
        if (amount >= 50) {
            CheatBreaker.getInstance().getModuleManager().notificationsMod.send("error", EnumChatFormatting.RED + "You have hit the limit of 50 hotkeys.", 5000L);
            return;
        }

        Setting setting = new Setting(this,"Hot key " + amount).setValue("/Command").setKeyCode(0).setMouseBind(false);
        this.hotkeys.add(setting);
        amount++;
        for (Setting setting2 : this.getSettingsList()) {
            if (setting2.getSettingName().equalsIgnoreCase(setting.getSettingName())) {
                return;
            }
        }
        this.getSettingsList().add(setting);
        CheatBreaker.getInstance().getConfigManager().updateProfile();
    }
}
