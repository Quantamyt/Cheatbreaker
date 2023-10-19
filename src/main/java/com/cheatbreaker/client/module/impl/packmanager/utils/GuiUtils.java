package com.cheatbreaker.client.module.impl.packmanager.utils;

import net.minecraft.client.Minecraft;

public class GuiUtils {
    private static final Minecraft MC = Minecraft.getMinecraft();

    public static String trimString(String text, int width) {
        if (text.endsWith(".zip")) {
            text = text.substring(0, text.length() - 4);
        }
        if (GuiUtils.MC.fontRenderer.getStringWidth(text) > width) {
            text = GuiUtils.MC.fontRenderer.trimStringToWidth(text, width - GuiUtils.MC.fontRenderer.getStringWidth("...")) + "...";
        }
        return text;
    }
}
