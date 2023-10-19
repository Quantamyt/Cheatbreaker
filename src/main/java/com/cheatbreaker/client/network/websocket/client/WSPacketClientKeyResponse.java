package com.cheatbreaker.client.network.websocket.client;

import com.cheatbreaker.client.network.websocket.WSNetHandler;
import com.cheatbreaker.client.network.websocket.WSPacket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraft.network.PacketBuffer;

/**
 * @WSPacket WSPacketClientKeyResponse
 * @see WSPacket
 *
 * This packet writes and receives responses from sending the client key.
 */
@Getter @NoArgsConstructor @AllArgsConstructor
public class WSPacketClientKeyResponse extends WSPacket {
    private byte[] data;

    @Override
    public void write(PacketBuffer packetBuffer) {
        this.writeBlob(packetBuffer, this.data);
    }

    @Override
    public void read(PacketBuffer packetBuffer) {
        this.data = this.readBlob(packetBuffer);
    }

    @Override
    public void process(WSNetHandler wSNetHandler) {
    }
}
