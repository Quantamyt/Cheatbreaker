package com.cheatbreaker.client.network.websocket.client;

import com.cheatbreaker.client.network.websocket.WSNetHandler;
import com.cheatbreaker.client.network.websocket.WSPacket;
import net.minecraft.network.PacketBuffer;

public class WSPacketClientFriendRemove extends WSPacket {
    private String playerId;

    public WSPacketClientFriendRemove() {
    }

    public WSPacketClientFriendRemove(String string) {
        this.playerId = string;
    }

    @Override
    public void write(PacketBuffer packetBuffer) {
        packetBuffer.writeStringToBuffer(this.playerId);
    }

    @Override
    public void read(PacketBuffer packetBuffer) {
        this.playerId = packetBuffer.readStringFromBuffer(52);
    }

    @Override
    public void process(WSNetHandler wSNetHandler) {
        wSNetHandler.handleFriendRemove(this);
    }

    public String getPlayerId() {
        return this.playerId;
    }
}
