package com.cheatbreaker.client.network.websocket.shared;

import com.cheatbreaker.client.network.websocket.WSNetHandler;
import com.cheatbreaker.client.network.websocket.WSPacket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraft.network.PacketBuffer;

import java.io.IOException;

/**
 * @WSPacket WSPacketFriendMessage
 * @see WSPacket
 *
 * This packet handles messages from a friend.
 */
@Getter @AllArgsConstructor @NoArgsConstructor
public class WSPacketFriendMessage extends WSPacket {
    private String playerId;
    private String message;

    @Override
    public void write(PacketBuffer packetBuffer) throws IOException {
        packetBuffer.writeString(this.playerId);
        packetBuffer.writeString(this.message);
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
}
