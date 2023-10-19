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

    /**
     * Gets all the information for the Discord RPC based on the server's name.
     */
    public String[] getRPCInfo(String name) {

        if (Minecraft.getMinecraft().isSingleplayer()) {
            return new String[]{"Singleplayer", "cb", "Playing in Singleplayer", null};
        } else if (name == null || name.isEmpty()) {
            return new String[]{"In Menus", "cb", "In the menus", null};
        }

        // Checks if the server is on the unsafe server list.
        for (String[] string : CheatBreaker.getInstance().getGlobalSettings().getUnsafeServers()) {
            if (name.endsWith(string[0].toLowerCase())) {
                return new String[]{"Unsafe Server", "unsafeserver", "Playing on an unsafe server", "Risking my security"};
            }
        }

        // Checks if the server is local or not.
        String serverName = getServerNameFromMapping(name, "name");
        if (serverName != null) {
            String isNetwork = serverName.endsWith("Network") ? "the " : "";
            return new String[]{serverName, getServerNameFromMapping(name, "id"), "Playing on " + isNetwork + serverName, null};
        } else if (name.contains("localhost") || name.equals("127.0.0.1") || name.startsWith("192.168") || name.equals("0") || name.equals("0.0.0.0")) {
            return new String[]{"Local Server", "cb", "Playing on a local server", null};
        } else if (isIp(name)) {
            return new String[]{"Numeric Server", "cb", "Playing on a numeric server", null};
        }

        return new String[]{"Private Server", "cb", "Playing on a private server", null};
    }

    private boolean isIp(String ip) {

        // Check if the string is not null
        if (ip == null) return false;

        // Get the parts of the ip
        String[] parts = ip.split("\\.");
        int index = 0;
        for (String part : parts) {
            try {
                int value = Integer.parseInt(part);

                // Checks if the value is out of range of being a valid IP address
                if (value < 0 || value > 255) {
                    return false;
                }
                index++;
            } catch (Exception e) {
                return false;
            }
        }
        return index == 4;
    }

    /**
     * Gets the server information.
     */
    private String getServerNameFromMapping(String ip, String jsonObj) {
        try {
            JsonArray serverMapping = new JsonParser().parse(new BufferedReader(new InputStreamReader(new URL("https://raw.githubusercontent.com/LunarClient/ServerMappings/master/servers.json").openStream(), StandardCharsets.UTF_8))).getAsJsonArray();

            for (JsonElement servers : serverMapping) {
                String strippedContent = servers.getAsJsonObject().get(jsonObj).toString().replaceAll("\"", "");
                JsonArray addresses = (JsonArray) servers.getAsJsonObject().get("addresses");

                for (JsonElement s : addresses) {
                    if (ip.endsWith(s.toString().replaceAll("\"", ""))) return strippedContent;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
