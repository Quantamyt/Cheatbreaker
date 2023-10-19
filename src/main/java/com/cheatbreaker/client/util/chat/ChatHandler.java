package com.cheatbreaker.client.util.chat;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;

public class ChatHandler {

    public static void sendBrandedChatMessage(String message) {
        Minecraft minecraft = Minecraft.getMinecraft();

        if (minecraft != null && minecraft.ingameGUI != null && minecraft.ingameGUI.getChatGUI() != null) {
            ChatComponentText textComponent = new ChatComponentText(message);
            textComponent.setBranded(true);
            minecraft.ingameGUI.getChatGUI().func_146227_a(textComponent);
        }
    }
}
