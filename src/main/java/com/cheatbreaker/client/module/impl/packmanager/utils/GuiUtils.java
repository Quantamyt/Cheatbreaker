package com.cheatbreaker.client.module.impl.packmanager.utils;

import net.minecraft.client.Minecraft;

public class GuiUtils {
    private static final Minecraft mc = Minecraft.getMinecraft();

    public static String trimString(String text, int width) {
        if (text.endsWith(".zip")) {
            text = text.substring(0, text.length() - 4);
        }
        if (GuiUtils.mc.fontRendererObj.getStringWidth(text) > width) {
            text = GuiUtils.mc.fontRendererObj.trimStringToWidth(text, width - GuiUtils.mc.fontRendererObj.getStringWidth("...")) + "...";
        }
        return text;
    }
}
