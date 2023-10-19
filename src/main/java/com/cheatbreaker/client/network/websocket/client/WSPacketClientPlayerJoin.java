package com.cheatbreaker.client.network.websocket.client;

import com.cheatbreaker.client.network.websocket.WSNetHandler;
import lombok.Getter;
import net.minecraft.network.PacketBuffer;
import com.cheatbreaker.client.network.websocket.WSPacket;

@Getter
public class WSPacketClientPlayerJoin extends WSPacket {
    private String playerId;

    public WSPacketClientPlayerJoin(String string) {
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
    }
}
