package com.cheatbreaker.client.network.plugin.server;

import com.cheatbreaker.client.network.plugin.client.ICBNetHandlerClient;
import com.cheatbreaker.client.network.ByteBufWrapper;
import com.cheatbreaker.client.network.plugin.CBPacket;
import com.cheatbreaker.client.network.plugin.ICBNetHandler;

import java.beans.ConstructorProperties;

public class PacketCooldown extends CBPacket {
    private String message;
    private long durationMs;
    private int iconId;

    public PacketCooldown() {
    }

    @ConstructorProperties({"message", "durationMs", "iconId"})
    public PacketCooldown(String message, long durationMs, int iconId) {
        this.message = message;
        this.durationMs = durationMs;
        this.iconId = iconId;
    }

    @Override
    public void write(ByteBufWrapper byteBufWrapper) {
        byteBufWrapper.writeString(this.message);
        byteBufWrapper.buf().writeLong(this.durationMs);
        byteBufWrapper.buf().writeInt(this.iconId);
    }

    @Override
    public void read(ByteBufWrapper byteBufWrapper) {
        this.message = byteBufWrapper.readString();
        this.durationMs = byteBufWrapper.buf().readLong();
        this.iconId = byteBufWrapper.buf().readInt();
    }

    @Override
    public void process(ICBNetHandler iCBNetHandler) {
        ((ICBNetHandlerClient)iCBNetHandler).handleCooldown(this);
    }

    public String getMessage() {
        return this.message;
    }

    public long getDurationMs() {
        return this.durationMs;
    }

    public int getIconId() {
        return this.iconId;
    }
}
