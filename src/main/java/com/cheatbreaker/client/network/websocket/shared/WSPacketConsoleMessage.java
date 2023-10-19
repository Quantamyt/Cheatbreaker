package com.cheatbreaker.client.network.websocket.shared;

import com.cheatbreaker.client.network.websocket.WSNetHandler;
import com.cheatbreaker.client.network.websocket.WSPacket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraft.network.PacketBuffer;

/**
 * @WSPacket WSPacketConsoleMessage
 * @see WSPacket
 *
 * This packet is used for new console messages.
 */
@Getter @AllArgsConstructor @NoArgsConstructor
public class WSPacketConsoleMessage extends WSPacket {
    private String message;

    @Override
    public void write(PacketBuffer packetBuffer) {
        packetBuffer.writeString(this.message);
    }

    @Override
    public void read(PacketBuffer packetBuffer) {
        this.message = packetBuffer.readStringFromBuffer(32767);
    }

    @Override
    public void process(WSNetHandler wSNetHandler) {
        wSNetHandler.handleConsoleOutput(this);
    }
}
