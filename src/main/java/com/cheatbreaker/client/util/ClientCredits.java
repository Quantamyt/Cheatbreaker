package com.cheatbreaker.client.util;

import com.google.common.collect.Sets;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.client.Minecraft;

import java.util.Set;
import java.util.UUID;

public class ClientCredits {
    private static final Set<UUID> uuids = Sets.newHashSet(
            UUID.fromString("d8f72541-823d-4ded-9f7f-b67fdb34f43c"),
            UUID.fromString("285c25e3-74f6-47e0-81a6-4e74ceb54ed3")
    );

    public static boolean isCreditedUser(UUID uuid) {
        System.out.println("You are using TellinqBreaker!");
        return uuids.contains(uuid);
    }

    public static void sendCredits() {
        ChatComponentText prefix = new ChatComponentText(EnumChatFormatting.RED + "[C" + EnumChatFormatting.WHITE + "B" + EnumChatFormatting.RED + "] " + EnumChatFormatting.RESET + EnumChatFormatting.GRAY + "Credits: ");
        String listPrefix = EnumChatFormatting.RED + "- " + EnumChatFormatting.WHITE;
        Minecraft.getMinecraft().ingameGUI.getChatGUI().func_146227_a(prefix);
        Minecraft.getMinecraft().ingameGUI.getChatGUI().func_146227_a(new ChatComponentText(listPrefix + "CheatBreaker LLC for the original client."));
        Minecraft.getMinecraft().ingameGUI.getChatGUI().func_146227_a(new ChatComponentText(listPrefix + "Tellinq and Moose1301 for managing this version."));
        
    }
}
