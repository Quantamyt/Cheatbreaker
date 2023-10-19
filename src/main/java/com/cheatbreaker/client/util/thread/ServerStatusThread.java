package com.cheatbreaker.client.util.thread;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.util.sessionserver.SessionServer;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

/**
 * Used to check if the Mojang status is good or not.
 * This will be displayed in the pause menu and in a com.cheatbreaker.client.module.impl.normal.hud.Notification.
 */
public class ServerStatusThread extends Thread {

    @Override
    public void run() {
        try {
            URLConnection uRLConnection = new URL(CheatBreaker.getInstance().getGlobalSettings().mojangStatusURL).openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(uRLConnection.getInputStream()));
            JsonArray jsonArray = new JsonParser().parse(bufferedReader).getAsJsonArray();

            for (int i = 0; i < jsonArray.size(); ++i) {
                JsonElement jsonElement2 = jsonArray.get(i);
                for (Map.Entry<String, JsonElement> entry : jsonElement2.getAsJsonObject().entrySet()) {
                    for (SessionServer sessionServer : CheatBreaker.getInstance().sessionServers) {
                        SessionServer.StatusColor sessionServerStatusColor;
                        if (!sessionServer.getUrl().equalsIgnoreCase(entry.getKey()) || (sessionServerStatusColor = SessionServer.StatusColor.getStatusByName(entry.getValue().getAsString())) == null)
                            continue;
                        if (sessionServerStatusColor != sessionServer.getStatus() && sessionServer.getStatus() !=
                                SessionServer.StatusColor.UNKNOWN && sessionServer.getStatus() != SessionServer.StatusColor.BUSY &&
                                (sessionServerStatusColor == SessionServer.StatusColor.DOWN || sessionServerStatusColor == SessionServer.StatusColor.UP)) {

                            EnumChatFormatting enumChatFormatting = sessionServerStatusColor == SessionServer.StatusColor.UP ? EnumChatFormatting.GREEN : EnumChatFormatting.RED;

                            String string = sessionServerStatusColor == SessionServer.StatusColor.UP ? "online" : "offline";
                            if (Minecraft.getMinecraft().theWorld != null) {
                                CheatBreaker.getInstance().getModuleManager().notificationsMod.send("info", "Minecraft " +
                                        EnumChatFormatting.AQUA + sessionServer.getName() +
                                        EnumChatFormatting.RESET + " server is now " +
                                        enumChatFormatting + string + EnumChatFormatting.RESET + ".", 7500L);
                            }
                        }
                        sessionServer.setStatus(sessionServerStatusColor);
                    }
                }
            }

            bufferedReader.close();
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
