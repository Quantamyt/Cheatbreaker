package com.cheatbreaker.client.ui.mainmenu;

import com.cheatbreaker.client.CheatBreaker;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.util.ResourceLocation;

@AllArgsConstructor @Getter
public class CBAccount {
    private String clientToken;
    private String username;
    private String accessToken;
    private String displayName;
    private String uuid;
    private final ResourceLocation headIcon;

    public CBAccount(String username, String clientToken, String accessToken, String displayName, String uuid) {
        this.username = username;
        this.clientToken = clientToken;
        this.accessToken = accessToken;
        this.displayName = displayName;
        this.uuid = uuid;
        this.headIcon = CheatBreaker.getInstance().getHeadIcon(displayName, uuid);
    }

    public void setAccessToken(String string) {
        this.accessToken = string;
    }
}
