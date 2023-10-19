package com.cheatbreaker.client.network.websocket.shared;

import com.cheatbreaker.client.network.websocket.WSNetHandler;
import com.cheatbreaker.client.network.websocket.WSPacket;
import net.minecraft.network.PacketBuffer;

public class WSPacketFriendAcceptOrDeny extends WSPacket {
    private boolean added;
    private String playerId;

    public WSPacketFriendAcceptOrDeny() {
    }

    public WSPacketFriendAcceptOrDeny(boolean bl, String string) {
        this.added = bl;
        this.playerId = string;
    }

    @Override
    public void write(PacketBuffer packetBuffer) {
        packetBuffer.writeBoolean(this.added);
        packetBuffer.writeStringToBuffer(this.playerId);
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

    public boolean isAdd() {
        return this.added;
    }

    public String getPlayerId() {
        return this.playerId;
    }
}
