package com.cheatbreaker.client.network.websocket.shared;

import com.cheatbreaker.client.network.websocket.WSNetHandler;
import com.cheatbreaker.client.network.websocket.WSPacket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraft.network.PacketBuffer;

/**
 * @WSPacket WSPacketFriendAcceptOrDeny
 * @see WSPacket
 *
 * This packet accepts or denies a friend request.
 */
@Getter @AllArgsConstructor @NoArgsConstructor
public class WSPacketFriendAcceptOrDeny extends WSPacket {
    private boolean added;
    private String playerId;

    @Override
    public void write(PacketBuffer packetBuffer) {
        packetBuffer.writeBoolean(this.added);
        packetBuffer.writeString(this.playerId);
    }

    @Override
    public void read(PacketBuffer packetBuffer) {
        this.added = packetBuffer.readBoolean();
        this.playerId = packetBuffer.readStringFromBuffer(52);
    }

    @Override
    public void process(WSNetHandler wSNetHandler) {
        wSNetHandler.handlePacketFriendAcceptOrDeny(this);
    }
}
