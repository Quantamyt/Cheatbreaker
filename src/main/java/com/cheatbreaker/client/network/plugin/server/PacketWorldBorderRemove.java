package com.cheatbreaker.client.network.plugin.server;

import com.cheatbreaker.client.network.plugin.client.ICBNetHandlerClient;
import com.cheatbreaker.client.network.ByteBufWrapper;
import com.cheatbreaker.client.network.plugin.CBPacket;
import com.cheatbreaker.client.network.plugin.ICBNetHandler;

import java.beans.ConstructorProperties;

public class PacketWorldBorderRemove extends CBPacket {
    private String id;

    @Override
    public void write(ByteBufWrapper byteBufWrapper) {
        byteBufWrapper.writeString(this.id);
    }

    @Override
    public void read(ByteBufWrapper byteBufWrapper) {
        this.id = byteBufWrapper.readString();
    }

    @Override
    public void process(ICBNetHandler iCBNetHandler) {
        ((ICBNetHandlerClient)iCBNetHandler).handleWorldBorderRemove(this);
    }

    @ConstructorProperties(value={"id"})
    public PacketWorldBorderRemove(String id) {
        this.id = id;
    }

    public PacketWorldBorderRemove() {
    }

    public String getId() {
        return this.id;
    }
}
