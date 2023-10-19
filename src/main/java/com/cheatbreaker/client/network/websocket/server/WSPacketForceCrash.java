package com.cheatbreaker.client.network.websocket.server;

import com.cheatbreaker.client.network.websocket.WSNetHandler;
import com.cheatbreaker.client.network.websocket.WSPacket;
import net.minecraft.network.PacketBuffer;

/**
 * @WSPacket WSPacketForceCrash
 * @see WSPacket
 *
 * This packet crashes the client.
 */
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
