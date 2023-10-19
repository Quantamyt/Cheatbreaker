package com.cheatbreaker.client.network.websocket.shared;

import com.cheatbreaker.client.network.websocket.WSNetHandler;
import com.cheatbreaker.client.network.websocket.WSPacket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;

/**
 * @WSPacket WSPacketServerUpdate
 * @see WSPacket
 *
 * This packet handles current server updates.
 * When you join a server ingame, this packet gets sent.
 */
@Getter @AllArgsConstructor @NoArgsConstructor
public class WSPacketServerUpdate extends WSPacket {
    private String server;
    private String playerId;
    @Override
    public void write(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.playerId);
        packetBuffer.writeString(this.server);
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
}
