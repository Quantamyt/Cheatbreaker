package com.cheatbreaker.client.network.websocket.server;

import com.cheatbreaker.client.network.websocket.WSNetHandler;
import net.minecraft.network.PacketBuffer;
import com.cheatbreaker.client.network.websocket.WSPacket;

public class WSPacketForceCrash extends WSPacket {
    @Override
    public void write(PacketBuffer packetBuffer) {
    }

    @Override
    public void read(PacketBuffer packetBuffer) {
    }

    @Override
    public void process(WSNetHandler handler) {
        handler.handleForceCrash(this);
    }
}
