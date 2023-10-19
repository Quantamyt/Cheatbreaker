package com.cheatbreaker.client.util.friend;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor @Getter
public class FriendRequest {
    private final String username;
    private final String playerId;
    private boolean friend;

    public FriendRequest(String username, String playerId) {
        this.username = username;
        this.playerId = playerId;
    }

    public void setFriend(boolean friend) {
        this.friend = friend;
    }
}
