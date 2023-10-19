package com.cheatbreaker.client.cosmetic;

import lombok.Getter;
import lombok.Setter;
import net.minecraft.util.ResourceLocation;

/**
 * This defines the CheatBreaker Client's cosmetic Object.
 */
@Getter
@Setter
public class Cosmetic {
    private String playerId;
    private String name;

    private CosmeticType type;

    private float scale;

    private int emoteId;

    private long lastUpdate;

    private boolean equipped;

    private ResourceLocation location;
    private ResourceLocation previewLocation;

    /**
     * This should be used for animated capes.
     */
    public Cosmetic(long time, String playerId, String name, CosmeticType type, float scale, boolean equipped, String location) {
        this.lastUpdate = time;
        this.playerId = playerId;
        this.name = name;
        this.type = type;
        this.scale = scale;
        this.equipped = equipped;
        this.location = new ResourceLocation(location);
        this.previewLocation = new ResourceLocation("client/preview/" + location.replaceAll("client/", ""));
    }

    /**
     * This is the normal capes/wings
     */
    public Cosmetic(String playerId, String name, CosmeticType type, float scale, boolean equipped, String location) {
        this.playerId = playerId;
        this.name = name;
        this.type = type;
        this.scale = scale;
        this.equipped = equipped;
        this.location = new ResourceLocation(location);
        this.previewLocation = new ResourceLocation("client/preview/" + location.replace("client/", ""));
    }

    /**
     * This is for emotes, this will be setup at a later time.
     */
    public Cosmetic(String playerId, int emoteId, CosmeticType type) {
        this.playerId = playerId;
        this.emoteId = emoteId;
        this.type = type;
    }

    /**
     * This defines the type of cosmetic that the player has.
     */
    public enum CosmeticType {
        WINGS("dragon_wings"),
        CAPE("cape"),
        EMOTE("emote");

        @Getter private final String typeName;
        CosmeticType(String typeName) {
            this.typeName = typeName;
        }

        public static CosmeticType get(String name) {
            for (Cosmetic.CosmeticType cosmetic : CosmeticType.values()) {
                if (cosmetic.getTypeName().equals(name)) {
                    return cosmetic;
                }
            }
            return null;
        }
    }
}
