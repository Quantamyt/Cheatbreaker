package com.cheatbreaker.client.network.websocket.shared;

import com.cheatbreaker.client.network.websocket.WSNetHandler;
import net.minecraft.network.PacketBuffer;
import com.cheatbreaker.client.network.websocket.WSPacket;

import java.io.IOException;

public class WSPacketServerUpdate extends WSPacket {
    private String server;
    private String playerId;

    public WSPacketServerUpdate() {
    }

    public WSPacketServerUpdate(String playerId, String server) {
        this.playerId = playerId;
        this.server = server;
    }

    @Override
    public void write(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeStringToBuffer(this.playerId);
        packetBuffer.writeStringToBuffer(this.server);
    }

    @Override
    public void read(PacketBuffer packetBuffer) throws IOException {
        this.playerId = packetBuffer.readStringFromBuffer(52);
        this.server = packetBuffer.readStringFromBuffer(100);
    }

    @Override
    public void process(WSNetHandler wSNetHandler) {
        wSNetHandler.handleServerUpdate(this);
    }

    public String getServer() {
        return this.server;
    }

    public String getPlayerId() {
        return this.playerId;
    }
}
