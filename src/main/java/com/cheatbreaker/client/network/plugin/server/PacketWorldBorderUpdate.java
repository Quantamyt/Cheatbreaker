package com.cheatbreaker.client.network.plugin.server;

import com.cheatbreaker.client.network.plugin.client.ICBNetHandlerClient;
import com.cheatbreaker.client.network.ByteBufWrapper;
import com.cheatbreaker.client.network.plugin.CBPacket;
import com.cheatbreaker.client.network.plugin.ICBNetHandler;

import java.beans.ConstructorProperties;

public class PacketWorldBorderUpdate extends CBPacket {
    private String id;
    private double minX;
    private double minZ;
    private double maxX;
    private double maxZ;
    private int durationTicks;

    @Override
    public void write(ByteBufWrapper byteBufWrapper) {
        byteBufWrapper.writeString(this.id);    
        byteBufWrapper.buf().writeDouble(this.minX);
        byteBufWrapper.buf().writeDouble(this.minZ);
        byteBufWrapper.buf().writeDouble(this.maxX);
        byteBufWrapper.buf().writeDouble(this.maxZ);
        byteBufWrapper.buf().writeInt(this.durationTicks);
    }

    @Override
    public void read(ByteBufWrapper byteBufWrapper) {
        this.id = byteBufWrapper.readString();
        this.minX = byteBufWrapper.buf().readDouble();
        this.minZ = byteBufWrapper.buf().readDouble();
        this.maxX = byteBufWrapper.buf().readDouble();
        this.maxZ = byteBufWrapper.buf().readDouble();
        this.durationTicks = byteBufWrapper.buf().readInt();
    }

    @Override
    public void process(ICBNetHandler iCBNetHandler) {
        ((ICBNetHandlerClient)iCBNetHandler).handleWorldBorderUpdate(this);
    }

    public String getId() {
        return this.id;
    }

    public double getMinX() {
        return this.minX;
    }

    public double getMinZ() {
        return this.minZ;
    }

    public double getMaxX() {
        return this.maxX;
    }

    public double getMaxZ() {
        return this.maxZ;
    }

    public int getDurationTicks() {
        return this.durationTicks;
    }

    public PacketWorldBorderUpdate() {
    }

    @ConstructorProperties(value={"id", "minX", "minZ", "maxX", "maxZ", "durationTicks"})
    public PacketWorldBorderUpdate(String id, double minX, double minZ, double maxX, double maxZ, int durationTicks) {
        this.id = id;
        this.minX = minX;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxZ = maxZ;
        this.durationTicks = durationTicks;
    }
}
