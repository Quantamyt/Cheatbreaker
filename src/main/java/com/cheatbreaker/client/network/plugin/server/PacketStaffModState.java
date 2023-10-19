package com.cheatbreaker.client.network.plugin.server;

import com.cheatbreaker.client.network.plugin.client.ICBNetHandlerClient;
import com.cheatbreaker.client.network.ByteBufWrapper;
import com.cheatbreaker.client.network.plugin.CBPacket;
import com.cheatbreaker.client.network.plugin.ICBNetHandler;

public class PacketStaffModState extends CBPacket {
    private String id;
    private boolean state;

    public PacketStaffModState() {
    }

    public PacketStaffModState(String id, boolean state) {
        this.id = id;
        this.state = state;
    }

    @Override
    public void write(ByteBufWrapper byteBufWrapper) {
        byteBufWrapper.writeString(this.id);
        byteBufWrapper.buf().writeBoolean(this.state);
    }

    @Override
    public void read(ByteBufWrapper byteBufWrapper) {
        this.id = byteBufWrapper.readString();
        this.state = byteBufWrapper.buf().readBoolean();
    }

    @Override
    public void process(ICBNetHandler iCBNetHandler) {
        ((ICBNetHandlerClient)iCBNetHandler).handleStaffModState(this);
    }

    public String getId() {
        return this.id;
    }

    public boolean getState() {
        return this.state;
    }
}
