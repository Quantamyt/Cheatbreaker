package com.cheatbreaker.client.network.websocket.client;

import com.cheatbreaker.client.network.websocket.WSNetHandler;
import com.cheatbreaker.client.network.websocket.WSPacket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraft.network.PacketBuffer;

/**
 * @WSPacket WSPacketClientFriendRemove
 * @see WSPacket
 *
 * This packet removes a player from the player's friends list.
 */
@Getter @AllArgsConstructor @NoArgsConstructor
public class WSPacketClientFriendRemove extends WSPacket {
    private String playerId;

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
        wSNetHandler.handleFriendRemove(this);
    }
}
