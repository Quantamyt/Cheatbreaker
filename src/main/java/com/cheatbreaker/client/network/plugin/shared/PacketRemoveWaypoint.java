package com.cheatbreaker.client.network.plugin.shared;

import com.cheatbreaker.client.network.ByteBufWrapper;
import com.cheatbreaker.client.network.plugin.CBPacket;
import com.cheatbreaker.client.network.plugin.ICBNetHandler;
import com.google.common.base.Preconditions;

public class PacketRemoveWaypoint extends CBPacket {
    private String name;
    private String world;

    public PacketRemoveWaypoint() {
    }

    public PacketRemoveWaypoint(String string, String string2) {
        this.name = Preconditions.checkNotNull(string, "name");
        this.world = Preconditions.checkNotNull(string2, "world");
    }

    @Override
    public void write(ByteBufWrapper byteBufWrapper) {
        byteBufWrapper.writeString(this.name);
        byteBufWrapper.writeString(this.world);
    }

    @Override
    public void read(ByteBufWrapper byteBufWrapper) {
        this.name = byteBufWrapper.readString();
        this.world = byteBufWrapper.readString();
    }

    @Override
    public void process(ICBNetHandler iCBNetHandler) {
        iCBNetHandler.handleRemoveWaypoint(this);
    }

    public String getName() {
        return this.name;
    }

    public String getWorld() {
        return this.world;
    }
}
