package com.cheatbreaker.client.util.thread;

import com.cheatbreaker.client.ui.element.AliasesElement;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.minecraft.util.EnumChatFormatting;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Map;

/**
 * Used to get all past names of a player.
 */
public class AliasesThread extends Thread {
    private final AliasesElement aliasesElement;
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

    public AliasesThread(AliasesElement aliasesElement) {
        this.aliasesElement = aliasesElement;
    }

    @Override
    public void run() {
        try {
            URL uRL = new URL("https://api.mojang.com/user/profiles/" +
                    this.aliasesElement.getFriend().getPlayerId().replaceAll("-", "") + "/names");
            URLConnection uRLConnection = uRL.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(uRLConnection.getInputStream()));
            String string = bufferedReader.readLine();
            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement = jsonParser.parse("{\"Names\": " + string + "}");
            for (Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                if (!entry.getKey().equalsIgnoreCase("Names")) continue;
                for (JsonElement jsonElement2 : entry.getValue().getAsJsonArray()) {
                    String string2 = jsonElement2.getAsJsonObject().get("name").getAsString();
                    if (jsonElement2.getAsJsonObject().has("changedToAt")) {
                        long l = jsonElement2.getAsJsonObject().get("changedToAt").getAsLong();
                        LocalDateTime localDateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(l), ZoneId.systemDefault());
                        this.aliasesElement.getAliases().add(EnumChatFormatting.GRAY + localDateTime.format(this.dateFormatter) + EnumChatFormatting.RESET + " " + string2);
                        continue;
                    }
                    this.aliasesElement.getAliases().add(string2);
                }
            }
            Collections.reverse(this.aliasesElement.getAliases());
            this.aliasesElement.setElementSize(this.aliasesElement.getXPosition(), this.aliasesElement.getYPosition(),
                    this.aliasesElement.getWidth(), this.aliasesElement.getHeight() +
                            (float) (this.aliasesElement.getAliases().size() * 10) - (float) 10);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
