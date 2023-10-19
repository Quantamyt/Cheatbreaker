package com.cheatbreaker.client.network.websocket.server;

import com.cheatbreaker.client.network.websocket.WSNetHandler;
import com.cheatbreaker.client.network.websocket.WSPacket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraft.network.PacketBuffer;

/**
 * @WSPacket WSPacketNotification
 * @see WSPacket
 *
 * This packet sends a notification to the client, and can write it for use in the overlay console.
 */
@Getter @NoArgsConstructor @AllArgsConstructor
public class WSPacketNotification extends WSPacket {
    private String title;
    private String content;

    @Override
    public void write(PacketBuffer packetBuffer) {
        packetBuffer.writeString(this.title);
        packetBuffer.writeString(this.content);
    }

    @Override
    public void read(PacketBuffer packetBuffer) {
        this.title = packetBuffer.readStringFromBuffer(128);
        this.content = packetBuffer.readStringFromBuffer(512);
    }

    @Override
    public void process(WSNetHandler wSNetHandler) {
        wSNetHandler.handleFormattedConsoleOutput(this);
    }
}
