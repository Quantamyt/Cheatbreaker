package com.cheatbreaker.client.network.websocket.client;

import com.cheatbreaker.client.network.websocket.WSNetHandler;
import com.cheatbreaker.client.network.websocket.WSPacket;
import lombok.Getter;
import net.minecraft.network.PacketBuffer;

/**
 * @WSPacket WSPacketClientPlayerJoin
 * @see WSPacket
 *
 * This packet is used when a player joins.
 */
@Getter
public class WSPacketClientPlayerJoin extends WSPacket {
    private String playerId;

    public WSPacketClientPlayerJoin(String string) {
        this.playerId = string;
    }

    @Override
    public void write(PacketBuffer packetBuffer) {
        packetBuffer.writeString(this.playerId);
    }

    @Override
    public void read(PacketBuffer packetBuffer) {
        this.playerId = packetBuffer.readStringFromBuffer(52);
    }

    @Override
    public void process(WSNetHandler wSNetHandler) {
    }
}
