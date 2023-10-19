package com.cheatbreaker.client.network.plugin.client;

import com.cheatbreaker.client.network.plugin.server.ICBNetHandlerServer;
import com.cheatbreaker.client.network.ByteBufWrapper;
import com.cheatbreaker.client.network.plugin.CBPacket;
import com.cheatbreaker.client.network.plugin.ICBNetHandler;

import java.util.UUID;

public class PacketVoiceChannelSwitch extends CBPacket {
    private UUID switchingTo;

    public PacketVoiceChannelSwitch() {
    }

    public PacketVoiceChannelSwitch(UUID uUID) {
        this.switchingTo = uUID;
    }

    @Override
    public void write(ByteBufWrapper byteBufWrapper) {
        byteBufWrapper.writeUUID(this.switchingTo);
    }

    @Override
    public void read(ByteBufWrapper byteBufWrapper) {
        this.switchingTo = byteBufWrapper.readUUID();
    }

    @Override
    public void process(ICBNetHandler iCBNetHandler) {
        ((ICBNetHandlerServer)iCBNetHandler).handleVoiceChannelSwitch(this);
    }

    public UUID getSwitchingTo() {
        return this.switchingTo;
    }
}
