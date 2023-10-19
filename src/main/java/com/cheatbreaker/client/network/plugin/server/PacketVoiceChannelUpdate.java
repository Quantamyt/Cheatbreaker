package com.cheatbreaker.client.network.plugin.server;

import com.cheatbreaker.client.network.plugin.client.ICBNetHandlerClient;
import com.cheatbreaker.client.network.ByteBufWrapper;
import com.cheatbreaker.client.network.plugin.CBPacket;
import com.cheatbreaker.client.network.plugin.ICBNetHandler;

import java.util.UUID;

public class PacketVoiceChannelUpdate extends CBPacket {
    public int status;
    private UUID channelUuid;
    private UUID uuid;
    private String name;

    public PacketVoiceChannelUpdate() {
    }

    public PacketVoiceChannelUpdate(int n, UUID uUID, UUID uUID2, String string) {
        this.status = n;
        this.channelUuid = uUID;
        this.uuid = uUID2;
        this.name = string;
    }

    @Override
    public void write(ByteBufWrapper byteBufWrapper) {
        byteBufWrapper.writeVarInt(this.status);
        byteBufWrapper.writeUUID(this.channelUuid);
        byteBufWrapper.writeUUID(this.uuid);
        byteBufWrapper.writeString(this.name);
    }

    @Override
    public void read(ByteBufWrapper byteBufWrapper) {
        this.status = byteBufWrapper.readVarInt();
        this.channelUuid = byteBufWrapper.readUUID();
        this.uuid = byteBufWrapper.readUUID();
        this.name = byteBufWrapper.readString();
    }

    @Override
    public void process(ICBNetHandler iCBNetHandler) {
        ((ICBNetHandlerClient)iCBNetHandler).handleVoiceChannelUpdate(this);
    }

    public int getStatus() {
        return this.status;
    }

    public UUID getChannelUUID() {
        return this.channelUuid;
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public String getName() {
        return this.name;
    }
}
