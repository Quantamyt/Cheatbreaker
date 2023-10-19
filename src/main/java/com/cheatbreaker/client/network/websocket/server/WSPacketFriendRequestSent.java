package com.cheatbreaker.client.network.websocket.server;

import com.cheatbreaker.client.network.websocket.WSNetHandler;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import net.minecraft.network.PacketBuffer;
import com.cheatbreaker.client.network.websocket.WSPacket;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class WSPacketFriendRequestSent extends WSPacket {
    private String playerId;
    private String name;
    private boolean friend;

    @Override
    public void write(PacketBuffer buffer) {
        buffer.writeStringToBuffer(this.playerId);
        buffer.writeStringToBuffer(this.name);
        buffer.writeBoolean(this.friend);
    }

    @Override
    public void read(PacketBuffer buffer) {
        this.playerId = buffer.readStringFromBuffer(52);
        this.name = buffer.readStringFromBuffer(32);
        this.friend = buffer.readBoolean();
    }

    @Override
    public void process(WSNetHandler wSNetHandler) {
        wSNetHandler.handleFriendRequest(this, true);
    }
}
