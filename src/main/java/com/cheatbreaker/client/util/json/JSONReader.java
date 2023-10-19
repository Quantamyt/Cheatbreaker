package com.cheatbreaker.client.util.json;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class JSONReader {

    private static String getContent(Reader fileReader) throws IOException {
        int i;
        StringBuilder sb = new StringBuilder();

        while ((i = fileReader.read()) != -1) {
            sb.append((char) i);
        }

        return sb.toString();
    }

    public static JSONObject getJSONObjectFromURL(String url) throws IOException, JSONException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openStream(), StandardCharsets.UTF_8));
        return new JSONObject(getContent(reader));
    }

    public void test() {

    }

    public static JSONArray getJSONArrayFromURL(String url) throws IOException, JSONException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(new URL(url).openStream(), StandardCharsets.UTF_8));
        return new JSONArray(getContent(reader));
    }

    public static InputStream readAsBrowser(URL url, boolean useCustomAgent) {
        try {
            HttpURLConnection httpcon = (HttpURLConnection) url.openConnection();
            httpcon.addRequestProperty("User-Agent", (useCustomAgent ? "CheatBreaker-Client" : "Mozilla/4.0"));

            return httpcon.getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
