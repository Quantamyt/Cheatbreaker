package com.cheatbreaker.client.network.plugin.server;

import com.cheatbreaker.client.network.plugin.client.ICBNetHandlerClient;
import com.cheatbreaker.client.network.ByteBufWrapper;
import com.cheatbreaker.client.network.plugin.CBPacket;
import com.cheatbreaker.client.network.plugin.ICBNetHandler;

import java.util.UUID;

public class PacketDeleteVoiceChannel extends CBPacket {
    private UUID channelId;

    public PacketDeleteVoiceChannel() {
    }

    public PacketDeleteVoiceChannel(UUID uUID) {
        this.channelId = uUID;
    }

    @Override
    public void write(ByteBufWrapper byteBufWrapper) {
        byteBufWrapper.writeUUID(this.channelId);
    }

    @Override
    public void read(ByteBufWrapper byteBufWrapper) {
        this.channelId = byteBufWrapper.readUUID();
    }

    @Override
    public void process(ICBNetHandler iCBNetHandler) {
        ((ICBNetHandlerClient)iCBNetHandler).handleDeleteVoiceChannel(this);
    }

    public UUID getChannelId() {
        return this.channelId;
    }
}
