package com.cheatbreaker.client.network.plugin.server;

import com.cheatbreaker.client.network.plugin.client.ICBNetHandlerClient;
import com.cheatbreaker.client.network.ByteBufWrapper;
import com.cheatbreaker.client.network.plugin.CBPacket;
import com.cheatbreaker.client.network.plugin.ICBNetHandler;

public class PacketNotification extends CBPacket {
    private String message;
    private long durationMs;
    private String level;

    public PacketNotification() {
    }

    public PacketNotification(String message, long durationMs, String level) {
        this.message = message;
        this.durationMs = durationMs;
        this.level = level;
    }

    @Override
    public void write(ByteBufWrapper byteBufWrapper) {
        byteBufWrapper.writeString(this.message);
        byteBufWrapper.buf().writeLong(this.durationMs);
        byteBufWrapper.writeString(this.level);
    }

    @Override
    public void read(ByteBufWrapper byteBufWrapper) {
        this.message = byteBufWrapper.readString();
        this.durationMs = byteBufWrapper.buf().readLong();
        this.level = byteBufWrapper.readString();
    }

    @Override
    public void process(ICBNetHandler iCBNetHandler) {
        ((ICBNetHandlerClient)iCBNetHandler).handleNotification(this);
    }

    public String getMessage() {
        return this.message;
    }

    public long getDurationMs() {
        return this.durationMs;
    }

    public String getLevel() {
        return this.level;
    }
}
