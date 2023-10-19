package com.cheatbreaker.client.cosmetic.profile;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.module.impl.normal.hypixel.ModuleNickHider;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Handles everything to do with ClientProfiles.
 */
public class ProfileHandler {
    @Getter private final Map<UUID, ClientProfile> wsOnlineUsers = new HashMap<>();

    /**
     * Returns a ClientProfile based on a player's UUID.
     */
    public ClientProfile getProfile(String playerId) {
        return this.getWsOnlineUsers().get(UUID.fromString(playerId));
    }

    /**
     * Returns a dashed UUID since 1.7 is weird as fuck.
     */
    public String recompileUUID(String uuidIn) {
        return UUID.fromString(uuidIn.replaceFirst (
                "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)",
                "$1-$2-$3-$4-$5"
        )).toString();
    }

    public boolean validate(String target, boolean username) {
        ModuleNickHider nickHider = CheatBreaker.getInstance().getModuleManager().nickHiderMod;

        if (username) {
            try {
                for (ClientProfile profile : this.wsOnlineUsers.values()) {
                    if (target.contains(profile.getUsername()) || (nickHider.hideRealName.getBooleanValue() && target.contains(nickHider.customNameString.getStringValue()))) {
                        return true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                for (UUID playerId : this.wsOnlineUsers.keySet()) {
//                    System.out.println(target);
//                    System.out.println(playerId);
                    if (UUID.fromString(target).equals(playerId)) {
                        return true;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
