package com.cheatbreaker.client.network.plugin.server;

import com.cheatbreaker.client.network.plugin.client.ICBNetHandlerClient;
import com.cheatbreaker.client.network.ByteBufWrapper;
import com.cheatbreaker.client.network.plugin.CBPacket;
import com.cheatbreaker.client.network.plugin.ICBNetHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PacketUpdateHologram extends CBPacket {
    private UUID uuid;
    private List<String> lines;

    public PacketUpdateHologram() {
    }

    public PacketUpdateHologram(UUID uUID, List list) {
        this.uuid = uUID;
        this.lines = list;
    }

    @Override
    public void write(ByteBufWrapper byteBufWrapper) {
        byteBufWrapper.writeUUID(this.uuid);
        byteBufWrapper.writeVarInt(this.lines.size());
        for (String string : this.lines) {
            byteBufWrapper.writeString(string);
        }
    }

    @Override
    public void read(ByteBufWrapper byteBufWrapper) {
        this.uuid = byteBufWrapper.readUUID();
        int n = byteBufWrapper.readVarInt();
        this.lines = new ArrayList();
        for (int i = 0; i < n; ++i) {
            this.lines.add(byteBufWrapper.readString());
        }
    }

    @Override
    public void process(ICBNetHandler iCBNetHandler) {
        ((ICBNetHandlerClient)iCBNetHandler).handleUpdateHologram(this);
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public List<String> getLines() {
        return this.lines;
    }
}
