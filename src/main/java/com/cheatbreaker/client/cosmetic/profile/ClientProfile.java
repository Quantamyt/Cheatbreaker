package com.cheatbreaker.client.cosmetic.profile;

import lombok.*;

/**
 * Defines the CheatBreaker Client's player profile.
 * This is currently only used for Player Icons, however will probably be used for more later.
 */
@Getter
@AllArgsConstructor
public class ClientProfile {
    @Setter private String username;
    private int color;
    private int color2;
}
