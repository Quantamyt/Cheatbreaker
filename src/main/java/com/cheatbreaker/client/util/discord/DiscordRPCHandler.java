package com.cheatbreaker.client.util.discord;

import com.cheatbreaker.client.CheatBreaker;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.minecraft.client.Minecraft;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class DiscordRPCHandler {
    private static String serversURL = "https://raw.githubusercontent.com/LunarClient/ServerMappings/master/servers.json";

    public static String[] getServerName(String name) {
        if (Minecraft.getMinecraft().isSingleplayer()) {
            return new String[]{"Singleplayer", "cb", "Playing in Singleplayer", null};
        } else if (name == "" || name == null) {
            return new String[]{"In Menus", "cb", "In the menus", null};
        }
        // Checks if the server is on the unsafe server list.
        for (String[] string : CheatBreaker.getInstance().getGlobalSettings().getUnsafeServers()) {
            if (name.endsWith(string[0].toLowerCase())) {
                return new String[]{"Unsafe Server", "unsafeserver", "Playing on an unsafe server", "Risking my security"};
            }
        }
        String serverName = getServerNameFromMapping(name, "name");
        if (serverName != null) {
            String isNetwork = serverName.endsWith("Network") ? "the " : "";
            return new String[]{serverName, getServerNameFromMapping(name, "id"), "Playing on " + isNetwork + serverName, null};
        } else if (name.contains("localhost") || name.equals("127.0.0.1") || name.startsWith("192.168")) {
            return new String[]{"Local Server", "cb", "Playing on a local server", null};
        }
        return new String[]{"Private Server", "cb", "Playing on a private server", null};
    }

    private static String getServerNameFromMapping(String ip, String object) {
        try {
            JsonArray jsonObject = new JsonParser().parse(new BufferedReader(new InputStreamReader(new URL(serversURL).openStream(), StandardCharsets.UTF_8))).getAsJsonArray();
            for (JsonElement s2 : jsonObject) {
                String s3 = s2.getAsJsonObject().get(object).toString().replaceAll("\"", "");
                JsonArray jsonArray = (JsonArray) s2.getAsJsonObject().get("addresses");
                for (JsonElement s : jsonArray) {
                    if (ip.endsWith(s.toString().replaceAll("\"", ""))) return s3;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
