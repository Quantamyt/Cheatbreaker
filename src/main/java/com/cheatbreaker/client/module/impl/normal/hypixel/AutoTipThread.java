package com.cheatbreaker.client.module.impl.normal.hypixel;

import com.cheatbreaker.client.CheatBreaker;
import net.minecraft.client.Minecraft;

import java.util.TimerTask;

public class AutoTipThread extends TimerTask {
    @Override
    public void run() {
        CheatBreaker cb = CheatBreaker.getInstance();
        if (cb == null || cb.getModuleManager() == null && Minecraft.getMinecraft().thePlayer == null) return;

        if (cb.getModuleManager().hypixelMod.isEnabled() && cb.getModuleManager().hypixelMod.autoTip.getBooleanValue() && Minecraft.getMinecraft().getCurrentServerData() != null) {
            if (Minecraft.getMinecraft().getCurrentServerData().serverIP.toLowerCase().contains("hypixel")) {
                Minecraft.getMinecraft().thePlayer.sendChatMessage("/tip all");
            }
        }
    }
}
