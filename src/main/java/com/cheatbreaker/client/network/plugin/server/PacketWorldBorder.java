package com.cheatbreaker.client.network.plugin.server;

import com.cheatbreaker.client.network.plugin.client.ICBNetHandlerClient;
import com.cheatbreaker.client.network.ByteBufWrapper;
import com.cheatbreaker.client.network.plugin.CBPacket;
import com.cheatbreaker.client.network.plugin.ICBNetHandler;

import java.beans.ConstructorProperties;

public class PacketWorldBorder extends CBPacket {
    private String id;
    private String world;
    private boolean cancelsExit;
    private boolean canShrinkExpand;
    private int color = -13421569;
    private double minX;
    private double minZ;
    private double maxX;
    private double maxZ;

    @Override
    public void write(ByteBufWrapper buf) {
        buf.writeOptional(this.id, o -> buf.writeString((String) o));
        buf.writeString(this.world);
        buf.buf().writeBoolean(this.cancelsExit);
        buf.buf().writeBoolean(this.canShrinkExpand);
        buf.buf().writeInt(this.color);
        buf.buf().writeDouble(this.minX);
        buf.buf().writeDouble(this.minZ);
        buf.buf().writeDouble(this.maxX);
        buf.buf().writeDouble(this.maxZ);
    }

    @Override
    public void read(ByteBufWrapper buf) {
        this.id = (String)buf.readOptional(buf::readString);
        this.world = buf.readString();
        this.cancelsExit = buf.buf().readBoolean();
        this.canShrinkExpand = buf.buf().readBoolean();
        this.color = buf.buf().readInt();
        this.minX = buf.buf().readDouble();
        this.minZ = buf.buf().readDouble();
        this.maxX = buf.buf().readDouble();
        this.maxZ = buf.buf().readDouble();
    }

    @Override
    public void process(ICBNetHandler iCBNetHandler) {
        ((ICBNetHandlerClient)iCBNetHandler).handleWorldBorder(this);
    }

    public String getId() {
        return this.id;
    }

    public String getWorld() {
        return this.world;
    }

    public boolean doesCancelExit() {
        return this.cancelsExit;
    }

    public boolean canShrinkExpand() {
        return this.canShrinkExpand;
    }

    public int getColor() {
        return this.color;
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

    public PacketWorldBorder() {
    }

    @ConstructorProperties(value={"id", "world", "cancelsExit", "canShrinkExpand", "color", "minX", "minZ", "maxX", "maxZ"})
    public PacketWorldBorder(String id, String world, boolean cancelsExit, boolean canShrinkExpand, int color, double minX, double minZ, double maxX, double maxZ) {
        this.id = id;
        this.world = world;
        this.cancelsExit = cancelsExit;
        this.canShrinkExpand = canShrinkExpand;
        this.color = color;
        this.minX = minX;
        this.minZ = minZ;
        this.maxX = maxX;
        this.maxZ = maxZ;
    }
}
