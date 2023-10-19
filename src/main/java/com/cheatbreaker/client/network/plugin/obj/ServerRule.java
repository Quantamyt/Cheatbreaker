package com.cheatbreaker.client.network.plugin.obj;

public enum ServerRule {
    VOICE_ENABLED("voiceEnabled", Boolean.class),
    MINIMAP_STATUS("minimapStatus", String.class),
    SERVER_HANDLES_WAYPOINTS("serverHandlesWaypoints", Boolean.class),
    COMPETITIVE_GAMEMODE("competitiveGame", Boolean.class),

    LEGACY_ENCHANTING("legacyEnchanting", Boolean.class), //Lunar
    LEGACY_COMBAT("legacyCombat", Boolean.class); // Lunar To stop erroring
    private final String rule;
    private final Class value;

    ServerRule(String var3, Class var4) {
        this.rule = var3;
        this.value = var4;
    }

    public static ServerRule getRule(String string) {
        ServerRule serverRule = null;
        for (ServerRule serverRule2 : ServerRule.values()) {
            if (!serverRule2.getCommandName().equals(string)) continue;
            serverRule = serverRule2;
        }
        return serverRule;
    }

    public String getCommandName() {
        return this.rule;
    }

    public Class getValue() {
        return this.value;
    }
}
