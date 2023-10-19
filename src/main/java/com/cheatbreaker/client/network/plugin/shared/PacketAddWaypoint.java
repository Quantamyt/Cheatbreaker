package com.cheatbreaker.client.network.plugin.shared;

import com.cheatbreaker.client.network.ByteBufWrapper;
import com.cheatbreaker.client.network.plugin.CBPacket;
import com.cheatbreaker.client.network.plugin.ICBNetHandler;
import com.google.common.base.Preconditions;

public class PacketAddWaypoint extends CBPacket {
    private String name;
    private String world;
    private int color;
    private int x;
    private int y;
    private int z;
    private boolean forced;
    private boolean visible;

    public PacketAddWaypoint() {
    }

    public PacketAddWaypoint(String name, String world, int color, int x, int y, int z, boolean forced, boolean visible) {
        this.name = Preconditions.checkNotNull(name, "name");
        this.world = Preconditions.checkNotNull(world, "world");
        this.color = color;
        this.x = x;
        this.y = y;
        this.z = z;
        this.forced = forced;
        this.visible = visible;
    }

    @Override
    public void write(ByteBufWrapper byteBufWrapper) {
        byteBufWrapper.writeString(this.name);
        byteBufWrapper.writeString(this.world);
        byteBufWrapper.buf().writeInt(this.color);
        byteBufWrapper.buf().writeInt(this.x);
        byteBufWrapper.buf().writeInt(this.y);
        byteBufWrapper.buf().writeInt(this.z);
        byteBufWrapper.buf().writeBoolean(this.forced);
        byteBufWrapper.buf().writeBoolean(this.visible);
    }

    @Override
    public void read(ByteBufWrapper byteBufWrapper) {
        this.name = byteBufWrapper.readString();
        this.world = byteBufWrapper.readString();
        this.color = byteBufWrapper.buf().readInt();
        this.x = byteBufWrapper.buf().readInt();
        this.y = byteBufWrapper.buf().readInt();
        this.z = byteBufWrapper.buf().readInt();
        this.forced = byteBufWrapper.buf().readBoolean();
        this.visible = byteBufWrapper.buf().readBoolean();
    }

    @Override
    public void process(ICBNetHandler iCBNetHandler) {
        iCBNetHandler.handleAddWaypoint(this);
    }

    public String getName() {
        return this.name;
    }

    public String getWorld() {
        return this.world;
    }

    public int getColor() {
        return this.color;
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getZ() {
        return this.z;
    }

    public boolean isForced() {
        return this.forced;
    }

    public boolean isVisible() {
        return this.visible;
    }
}
