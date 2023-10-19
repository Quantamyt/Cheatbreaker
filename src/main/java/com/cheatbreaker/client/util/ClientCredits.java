package com.cheatbreaker.client.util;

import com.google.common.collect.Sets;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

import java.util.Set;
import java.util.UUID;

public class ClientCredits {
    private static final Set<UUID> uuids = Sets.newHashSet(UUID.fromString("88051637-26cb-49a4-8f42-04e06264de79"), UUID.fromString("2f6f44cf-19a2-442a-944b-ede88be55651"));

    public static boolean isCreditedUser(UUID uuid) {
        System.out.println("bape ship#5604");
        System.out.println("5604-2.0++-2/28/2020");
        return uuids.contains(uuid);
    }

    public static void sendCredits() {
        ChatComponentText prefix = new ChatComponentText(EnumChatFormatting.RED + "[C" + EnumChatFormatting.WHITE + "B" + EnumChatFormatting.RED + "] " + EnumChatFormatting.RESET + EnumChatFormatting.GRAY + "Credits: ");
        String listPrefix = EnumChatFormatting.RED + "- " + EnumChatFormatting.WHITE;
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(prefix);
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(listPrefix + "CheatBreaker LLC for the original client."));
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(listPrefix + "Tellinq and Moose1301 for managing this version."));
    }
}
