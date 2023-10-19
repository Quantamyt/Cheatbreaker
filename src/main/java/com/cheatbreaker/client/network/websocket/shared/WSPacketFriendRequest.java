package com.cheatbreaker.client.network.websocket.shared;

import com.cheatbreaker.client.network.websocket.WSNetHandler;
import net.minecraft.network.PacketBuffer;
import com.cheatbreaker.client.network.websocket.WSPacket;

public class WSPacketFriendRequest extends WSPacket {
    private String username;
    private String playerId;

    public WSPacketFriendRequest() {
    }

    public WSPacketFriendRequest(String string, String string2) {
        this.username = string;
        this.playerId = string2;
    }

    @Override
    public void write(PacketBuffer packetBuffer) {
        packetBuffer.writeStringToBuffer(this.username);
        packetBuffer.writeStringToBuffer(this.playerId);
    }

    @Override
    public void read(PacketBuffer packetBuffer) {
        this.username = packetBuffer.readStringFromBuffer(52);
        this.playerId = packetBuffer.readStringFromBuffer(32);
    }

    @Override
    public void process(WSNetHandler wSNetHandler) {
        wSNetHandler.handleFriendRequest(this, false);
    }

    public String getMessage() {
        return this.username;
    }

    public String getPlayerId() {
        return this.playerId;
    }
}
