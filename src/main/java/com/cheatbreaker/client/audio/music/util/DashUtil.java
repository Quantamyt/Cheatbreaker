package com.cheatbreaker.client.audio.music.util;

import com.cheatbreaker.client.audio.music.audio.DashAudioDevice;
import com.cheatbreaker.client.audio.music.audio.DashHook;
import com.cheatbreaker.client.audio.music.data.Station;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;


import javazoom.jl.decoder.JavaLayerUtils;
import javazoom.jl.player.Player;

public class DashUtil {
    private static final String dashApiStations = "https://dash-api.com/api/v3/allData.php";
    private static Player player;
    private static boolean playing;
    private static DashAudioDevice dashPlayer = new DashAudioDevice();

    public static List<Station> dashHelpers() {
        JavaLayerUtils.setHook(new DashHook());
        ArrayList<Station> arrayList = new ArrayList<>();
        try {
            JsonObject jsonObject = new JsonParser().parse(DashUtil.get(dashApiStations)).getAsJsonObject();
            if (jsonObject.has("stations")) {
                JsonArray jsonArray = jsonObject.getAsJsonArray("stations");
                for (JsonElement array : jsonArray) {
                    JsonObject object = array.getAsJsonObject();
                    String name = object.get("name").getAsString();
                    String genre = object.get("genre").getAsString();
                    String square_logo_url = object.get("square_logo_url").getAsString();
                    String current_song_url = object.get("current_song_url").getAsString();
                    String stream_url = object.get("stream_url").getAsString();
                    Station station = new Station(name, square_logo_url, genre, current_song_url, stream_url);
                    arrayList.add(station);
                }
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return arrayList;
    }

    public static String get(String string) {
        try {
            URLConnection uRLConnection = new URL(string).openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(uRLConnection.getInputStream()));
            return bufferedReader.readLine();
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    public static void end() {
        if (player != null) {
            player.close();
            player = null;
        }
        playing = false;
    }

    public static boolean isActive() {
        return player != null;
    }

    public static void end(String string) {
        if (playing) {
            return;
        }
        playing = true;
        if (player != null) {
            player.close();
            player = null;
            return;
        }
        new Thread(() -> {
            try {
                URL uRL = new URL(string);
                InputStream inputStream = uRL.openStream();
                dashPlayer = new DashAudioDevice();
                player = new Player(inputStream, dashPlayer);
                player.play();
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }).start();
    }

    public static DashAudioDevice getPlayer() {
        return dashPlayer;
    }
}
