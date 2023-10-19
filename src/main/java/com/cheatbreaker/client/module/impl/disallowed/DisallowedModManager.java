package com.cheatbreaker.client.module.impl.disallowed;

import lombok.Getter;

import java.util.*;

@Getter
public class DisallowedModManager {

    public final Map<String, List<String>> moduleBlockedServers = new HashMap<>();

    public boolean disallows(String serverIp, String module) {
        for (Map.Entry<String, List<String>> entries : moduleBlockedServers.entrySet())
        {
            if (serverIp.contains(entries.getKey())) {

                return entries.getValue().stream()
                        .filter(m -> m.contains(module))
                        .findFirst()
                        .orElse(null) != null;
            }
        }

        return false;
    }

    /*
        Pretty bad way of doing this but until we have a websocket function to pull from
        I'm doing it like this cry about it
     */
    public void startup() {
        moduleBlockedServers.put("hypixel", Arrays.asList("freelook", "auto text"));
    }
}
