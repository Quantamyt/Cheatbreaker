package com.cheatbreaker.client.network.websocket.shared;

import com.cheatbreaker.client.network.websocket.WSNetHandler;
import net.minecraft.network.PacketBuffer;
import com.cheatbreaker.client.network.websocket.WSPacket;

import java.io.IOException;

public class WSPacketFriendMessage extends WSPacket {
    private String playerId;
    private String message;

    public WSPacketFriendMessage() {
    }

    public WSPacketFriendMessage(String playerId, String message) {
        this.playerId = playerId;
        this.message = message;
    }

    @Override
    public void write(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeStringToBuffer(this.playerId);
        packetBuffer.writeStringToBuffer(this.message);
    }

    @Override
    public void read(PacketBuffer packetBuffer) throws IOException {
        this.playerId = packetBuffer.readStringFromBuffer(52);
        this.message = packetBuffer.readStringFromBuffer(1024);
    }

    @Override
    public void process(WSNetHandler wSNetHandler) {
        wSNetHandler.handleMessage(this);
    }

    public String getPlayerId() {
        return this.playerId;
    }

    public String getMessage() {
        return this.message;
    }
}
