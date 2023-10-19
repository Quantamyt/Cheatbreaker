package com.cheatbreaker.client.network.plugin.client;

import com.cheatbreaker.client.network.plugin.server.ICBNetHandlerServer;
import com.cheatbreaker.client.network.ByteBufWrapper;
import com.cheatbreaker.client.network.plugin.CBPacket;
import com.cheatbreaker.client.network.plugin.ICBNetHandler;

import java.util.UUID;

public class PacketVoiceMute extends CBPacket {
    private UUID muting;

    public PacketVoiceMute() {
    }

    public PacketVoiceMute(UUID uUID) {
        this.muting = uUID;
    }

    @Override
    public void write(ByteBufWrapper byteBufWrapper) {
        byteBufWrapper.writeUUID(this.muting);
    }

    @Override
    public void read(ByteBufWrapper byteBufWrapper) {
        this.muting = byteBufWrapper.readUUID();
    }

    @Override
    public void process(ICBNetHandler iCBNetHandler) {
        ((ICBNetHandlerServer)iCBNetHandler).handleVoiceMute(this);
    }

    public UUID getMuting() {
        return this.muting;
    }
}
