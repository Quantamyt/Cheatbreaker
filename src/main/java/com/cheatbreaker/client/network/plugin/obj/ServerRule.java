package com.cheatbreaker.client.network.plugin.obj;

import lombok.Getter;

/**
 * @see com.cheatbreaker.client.network.plugin.server.CBPacketServerRule
 *
 * This defines the server rules for Voice Chat, and other things.
 */
public enum ServerRule {
    VOICE_ENABLED("voiceEnabled", Boolean.class),
    MINIMAP_STATUS("minimapStatus", String.class),
    SERVER_HANDLES_WAYPOINTS("serverHandlesWaypoints", Boolean.class),
    COMPETITIVE_GAMEMODE("competitiveGame", Boolean.class),

    // These are from Lunar Client, mainly to stop constant errors.
    LEGACY_ENCHANTING("legacyEnchanting", Boolean.class),
    LEGACY_COMBAT("legacyCombat", Boolean.class);

    @Getter private final String ruleName;
    @Getter private final Class<?> ruleValue;
    ServerRule(String ruleName, Class<?> ruleValue) {
        this.ruleName = ruleName;
        this.ruleValue = ruleValue;
    }

    public static ServerRule getRuleByName(String name) {
        ServerRule ruleToGet = null;
        for (ServerRule rule : ServerRule.values()) {
            if (!rule.getRuleName().equals(name)) continue;
            ruleToGet = rule;
        }
        return ruleToGet;
    }
}
