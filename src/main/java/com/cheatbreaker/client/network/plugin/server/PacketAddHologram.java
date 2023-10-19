package com.cheatbreaker.client.network.plugin.server;

import com.cheatbreaker.client.network.plugin.client.ICBNetHandlerClient;
import com.cheatbreaker.client.network.ByteBufWrapper;
import com.cheatbreaker.client.network.plugin.CBPacket;
import com.cheatbreaker.client.network.plugin.ICBNetHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class PacketAddHologram extends CBPacket {
    private UUID uuid;
    private double x;
    private double y;
    private double z;
    private List<String> lines;

    public PacketAddHologram() {
    }

    public PacketAddHologram(UUID uuid, double x, double y, double z, List<String> lines) {
        this.uuid = uuid;
        this.x = x;
        this.y = y;
        this.z = z;
        this.lines = lines;
    }

    @Override
    public void write(ByteBufWrapper byteBufWrapper) {
        byteBufWrapper.writeUUID(this.uuid);
        byteBufWrapper.buf().writeDouble(this.x);
        byteBufWrapper.buf().writeDouble(this.y);
        byteBufWrapper.buf().writeDouble(this.z);
        byteBufWrapper.writeVarInt(this.lines.size());
        for (Object string : this.lines) {
            byteBufWrapper.writeString((String) string);
        }
    }

    @Override
    public void read(ByteBufWrapper byteBufWrapper) {
        this.uuid = byteBufWrapper.readUUID();
        this.x = byteBufWrapper.buf().readDouble();
        this.y = byteBufWrapper.buf().readDouble();
        this.z = byteBufWrapper.buf().readDouble();
        int n = byteBufWrapper.readVarInt();
        this.lines = new ArrayList();
        for (int i = 0; i < n; ++i) {
            this.lines.add(byteBufWrapper.readString());
        }
    }

    @Override
    public void process(ICBNetHandler iCBNetHandler) {
        ((ICBNetHandlerClient)iCBNetHandler).handleAddHologram(this);
    }

    public UUID getUUID() {
        return this.uuid;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public List<String> getLines() {
        return this.lines;
    }
}
