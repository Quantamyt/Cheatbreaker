package com.cheatbreaker.client.network.plugin.client;

import com.cheatbreaker.client.network.plugin.server.ICBNetHandlerServer;
import com.cheatbreaker.client.network.ByteBufWrapper;
import com.cheatbreaker.client.network.plugin.CBPacket;
import com.cheatbreaker.client.network.plugin.ICBNetHandler;

public class PacketClientVoice extends CBPacket {
    private byte[] data;

    public PacketClientVoice() {
    }

    public PacketClientVoice(byte[] array) {
        this.data = array;
    }

    @Override
    public void write(ByteBufWrapper byteBufWrapper) {
        this.writeBlob(byteBufWrapper, this.data);
    }

    @Override
    public void read(ByteBufWrapper byteBufWrapper) {
        this.data = this.readBlob(byteBufWrapper);
    }

    @Override
    public void process(ICBNetHandler iCBNetHandler) {
        ((ICBNetHandlerServer)iCBNetHandler).handleClientVoice(this);
    }

    public byte[] getData() {
        return this.data;
    }
}
