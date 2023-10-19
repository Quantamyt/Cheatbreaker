package com.cheatbreaker.client.util.voicechat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.util.EnumChatFormatting;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor @Getter
public class VoiceChannel {
    private final UUID uuid;
    private final String name;
    private final List<VoiceUser> users = new ArrayList<VoiceUser>();
    private final List<UUID> listeners = new ArrayList<UUID>();

    public VoiceUser createUser(UUID uuid, String name) {
        VoiceUser user = new VoiceUser(uuid, EnumChatFormatting.getTextWithoutFormattingCodes(name));
        if (!this.isUser(uuid)) {
            this.users.add(user);
        }
        return user;
    }

    public boolean removeUser(UUID var1) {
        return this.users.removeIf(var1x -> var1x.getPlayerUUID().equals(var1));
    }

    public void addListener(UUID uuid, String name) {
        if (this.isUser(uuid)) {
            this.listeners.add(uuid);
        }
    }

    public boolean removeListeners(UUID uuid) {
        return this.listeners.removeIf(var1x -> var1x.equals(uuid));
    }

    public boolean isListener(UUID uuid) {
        return this.listeners.stream().anyMatch(var1x -> var1x.equals(uuid));
    }

    public boolean isUser(UUID var1) {
        return this.users.stream().anyMatch(var1x -> var1x.getPlayerUUID().equals(var1));
    }
}
