package com.cheatbreaker.client.util;

import net.minecraft.util.ChatComponentText;
import net.minecraft.client.Minecraft;

public class MessageUtils {

    public static void sendBan(String user, int num) {
        Minecraft minecraft = Minecraft.getMinecraft();
        if (minecraft != null && minecraft.ingameGUI != null && minecraft.ingameGUI.getChatGUI() != null) {
            Minecraft.getMinecraft().ingameGUI.getChatGUI().func_146227_a(new ChatComponentText("(CB) " + user + " has been CheatBreaker banned."));
        }
    }

}
