package com.cheatbreaker.client.network.websocket.server;

import com.cheatbreaker.client.network.websocket.WSNetHandler;
import com.cheatbreaker.client.network.websocket.WSPacket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraft.network.PacketBuffer;

/**
 * @WSPacket WSPacketKeyRequest
 * @see WSPacket
 *
 * This packet is used when requesting the public key.
 */
@Getter @AllArgsConstructor @NoArgsConstructor
public class WSPacketKeyRequest extends WSPacket {
    private byte[] publicKey;

    @Override
    public void write(PacketBuffer buffer) {
        this.writeBlob(buffer, this.publicKey);
    }

    @Override
    public void read(PacketBuffer buffer) {
        this.publicKey = this.readBlob(buffer);
    }

    @Override
    public void process(WSNetHandler handler) {
        handler.handleKeyRequest(this);
    }
}
