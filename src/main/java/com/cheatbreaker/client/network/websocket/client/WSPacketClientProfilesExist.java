package com.cheatbreaker.client.network.websocket.client;

import com.cheatbreaker.client.network.websocket.WSNetHandler;
import net.minecraft.network.PacketBuffer;
import com.cheatbreaker.client.network.websocket.WSPacket;

public class WSPacketClientProfilesExist extends WSPacket {
    @Override
    public void write(PacketBuffer packetBuffer) {
    }

    @Override
    public void read(PacketBuffer packetBuffer) {
    }

    @Override
    public void process(WSNetHandler wSNetHandler) {
        wSNetHandler.handleProfilesExist(this);
    }
}
