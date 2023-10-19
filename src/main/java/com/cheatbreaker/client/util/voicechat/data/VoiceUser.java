package com.cheatbreaker.client.util.voicechat.data;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class VoiceUser {
    private UUID playerUUID;
    private String playerName;
}
