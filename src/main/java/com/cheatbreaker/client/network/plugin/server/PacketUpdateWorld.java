package com.cheatbreaker.client.network.plugin.server;

import com.cheatbreaker.client.network.plugin.client.ICBNetHandlerClient;
import com.cheatbreaker.client.network.ByteBufWrapper;
import com.cheatbreaker.client.network.plugin.CBPacket;
import com.cheatbreaker.client.network.plugin.ICBNetHandler;

public class PacketUpdateWorld extends CBPacket {
    private String world;

    public PacketUpdateWorld() {
    }

    public PacketUpdateWorld(String world) {
        this.world = world;
    }

    @Override
    public void write(ByteBufWrapper buf) {
        buf.writeString(this.world);
    }

    @Override
    public void read(ByteBufWrapper byteBufWrapper) {
        this.world = byteBufWrapper.readString();
    }

    @Override
    public void process(ICBNetHandler iCBNetHandler) {
        ((ICBNetHandlerClient)iCBNetHandler).handleUpdateWorld(this);
    }

    public String getWorld() {
        return this.world;
    }
}
