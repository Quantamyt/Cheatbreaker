package com.cheatbreaker.client.network.plugin.server;

import com.cheatbreaker.client.network.plugin.client.ICBNetHandlerClient;
import com.cheatbreaker.client.network.ByteBufWrapper;
import com.cheatbreaker.client.network.plugin.CBPacket;
import com.cheatbreaker.client.network.plugin.ICBNetHandler;

import java.util.UUID;

public class PacketVoice extends CBPacket {
    private UUID uuid;
    private byte[] data;

    public PacketVoice() {
    }

    public PacketVoice(UUID uuid, byte[] data) {
        this.uuid = uuid;
        this.data = data;
    }

    @Override
    public void write(ByteBufWrapper byteBufWrapper) {
        byteBufWrapper.writeUUID(this.uuid);
        this.writeBlob(byteBufWrapper, this.data);
    }

    @Override
    public void read(ByteBufWrapper byteBufWrapper) {
        this.uuid = byteBufWrapper.readUUID();
        this.data = this.readBlob(byteBufWrapper);
    }

    @Override
    public void process(ICBNetHandler iCBNetHandler) {
        ((ICBNetHandlerClient)iCBNetHandler).handleVoice(this);
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public byte[] getData() {
        return this.data;
    }
}
