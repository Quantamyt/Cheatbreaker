package com.cheatbreaker.client.network.websocket.client;

import com.cheatbreaker.client.network.websocket.WSNetHandler;
import com.cheatbreaker.client.network.websocket.WSPacket;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraft.network.PacketBuffer;

/**
 * @WSPacket WSPacketClientRequestsStatus
 * @see WSPacket
 *
 * This packet updates the player's friend request accepting status. (true or false)
 */
@Getter @AllArgsConstructor @NoArgsConstructor
public class WSPacketClientRequestsStatus extends WSPacket {
    private boolean acceptingRequests;

    @Override
    public void write(PacketBuffer packetBuffer) {
        packetBuffer.writeBoolean(this.acceptingRequests);
    }

    @Override
    public void read(PacketBuffer packetBuffer) {
        this.acceptingRequests = packetBuffer.readBoolean();
    }

    @Override
    public void process(WSNetHandler wSNetHandler) {
    }
}
