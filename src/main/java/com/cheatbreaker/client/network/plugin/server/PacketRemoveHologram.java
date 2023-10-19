package com.cheatbreaker.client.network.plugin.server;

import com.cheatbreaker.client.network.plugin.client.ICBNetHandlerClient;
import com.cheatbreaker.client.network.ByteBufWrapper;
import com.cheatbreaker.client.network.plugin.CBPacket;
import com.cheatbreaker.client.network.plugin.ICBNetHandler;

import java.util.UUID;

public class PacketRemoveHologram extends CBPacket {
    private UUID uuid;

    public PacketRemoveHologram() {
    }

    public PacketRemoveHologram(UUID uUID) {
        this.uuid = uUID;
    }

    @Override
    public void write(ByteBufWrapper byteBufWrapper) {
        byteBufWrapper.writeUUID(this.uuid);
    }

    @Override
    public void read(ByteBufWrapper byteBufWrapper) {
        this.uuid = byteBufWrapper.readUUID();
    }

    @Override
    public void process(ICBNetHandler iCBNetHandler) {
        ((ICBNetHandlerClient)iCBNetHandler).handleRemoveHologram(this);
    }

    public UUID getUUID() {
        return this.uuid;
    }
}
