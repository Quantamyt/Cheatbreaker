package com.cheatbreaker.client.network.websocket.client;

import com.cheatbreaker.client.network.websocket.WSNetHandler;
import net.minecraft.network.PacketBuffer;
import com.cheatbreaker.client.network.websocket.WSPacket;

public class WSPacketClientRequestsStatus extends WSPacket {
    private boolean acceptingRequests;

    public WSPacketClientRequestsStatus(boolean bl) {
        this.acceptingRequests = bl;
    }

    public WSPacketClientRequestsStatus() {
    }

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

    public boolean isAcceptingRequests() {
        return this.acceptingRequests;
    }
}
