package com.cheatbreaker.client.network.websocket.shared;

import com.cheatbreaker.client.network.websocket.WSNetHandler;
import com.cheatbreaker.client.network.websocket.WSPacket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraft.network.PacketBuffer;

/**
 * @WSPacket WSPacketFriendRequest
 * @see WSPacket
 *
 * This packet handles friend request sending and receiving.
 */
@Getter @AllArgsConstructor @NoArgsConstructor
public class WSPacketFriendRequest extends WSPacket {
    private String username;
    private String playerId;

    @Override
    public void write(PacketBuffer packetBuffer) {
        packetBuffer.writeString(this.username);
        packetBuffer.writeString(this.playerId);
    }

    @Override
    public void read(PacketBuffer packetBuffer) {
        this.username = packetBuffer.readStringFromBuffer(52);
        this.playerId = packetBuffer.readStringFromBuffer(32);
    }

    @Override
    public void process(WSNetHandler wSNetHandler) {
        wSNetHandler.handleFriendRequest(this, false);
    }
}
