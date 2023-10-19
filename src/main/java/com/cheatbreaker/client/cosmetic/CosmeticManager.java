package com.cheatbreaker.client.cosmetic;

import com.cheatbreaker.client.CheatBreaker;
import com.cheatbreaker.client.cosmetic.profile.ProfileHandler;
import lombok.Getter;
import lombok.Setter;
import net.minecraft.client.Minecraft;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class CosmeticManager {

    private final List<Cosmetic> capes = new ArrayList<>();
    private final List<Cosmetic> wings = new ArrayList<>();
    private final ProfileHandler profileHandler = CheatBreaker.getInstance().getProfileHandler();

    public List<Cosmetic> getFullCosmeticList() {
        ArrayList<Cosmetic> cosmetics = new ArrayList<>();

        // Adds the wings first so that they are at the front of the list always.
        for (Cosmetic wing : this.wings) {
            if (!wing.getPlayerId().equals(Minecraft.getMinecraft().getSession().getPlayerID())) continue;
            cosmetics.add(wing);
        }

        for (Cosmetic cape : this.capes) {
            if (!cape.getPlayerId().equals(Minecraft.getMinecraft().getSession().getPlayerID())) continue;
            cosmetics.add(cape);
        }

        return cosmetics;
    }

    public Cosmetic getActiveCape(UUID uuid) {
        for (Cosmetic cape : this.getCapes()) {
            if (cape.isEquipped() && uuid.toString().equals(cape.getPlayerId())) {
                return cape;
            }
        }
        return null;
    }

    public Cosmetic getActiveWings(UUID uuid) {
        for (Cosmetic wing : this.getWings()) {
            if (wing.isEquipped() && uuid.toString().equals(wing.getPlayerId())) {
                return wing;
            }
        }
        return null;
    }

    public void clearCosmetics(String uuid) {
        this.getCapes().removeIf(cape -> cape.getPlayerId().equals(uuid));
        this.getWings().removeIf(wings -> wings.getPlayerId().equals(uuid));
    }
}
