package com.cheatbreaker.client.network.plugin.server;

import com.cheatbreaker.client.network.plugin.client.ICBNetHandlerClient;
import com.cheatbreaker.client.network.ByteBufWrapper;
import com.cheatbreaker.client.network.plugin.CBPacket;
import com.cheatbreaker.client.network.plugin.ICBNetHandler;

import java.beans.ConstructorProperties;

public class PacketServerUpdate extends CBPacket {
    private String server;

    @Override
    public void write(ByteBufWrapper byteBufWrapper) {
        byteBufWrapper.writeString(this.server);
    }

    @Override
    public void read(ByteBufWrapper byteBufWrapper) {
        this.server = byteBufWrapper.readString();
    }

    @Override
    public void process(ICBNetHandler iCBNetHandler) {
        ((ICBNetHandlerClient)iCBNetHandler).handleServerUpdate(this);
    }

    @ConstructorProperties(value={"server"})
    public PacketServerUpdate(String string) {
        this.server = string;
    }

    public PacketServerUpdate() {
    }

    public String getServer() {
        return this.server;
    }
}
