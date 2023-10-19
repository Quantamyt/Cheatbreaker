package com.cheatbreaker.client.network.websocket.client;

import com.cheatbreaker.client.network.websocket.WSNetHandler;
import com.cheatbreaker.client.network.websocket.WSPacket;
import net.minecraft.network.PacketBuffer;

/**
 * @WSPacket WSPacketClientProfilesExist
 * @see WSPacket
 *
 * This packet checks if a mod configuration profile exists.
 */
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
